package ua.tc.marketplace.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tc.marketplace.model.dto.ad.AdDto;
import ua.tc.marketplace.model.entity.Distance;
import ua.tc.marketplace.model.entity.Location;
import ua.tc.marketplace.repository.DistanceRepository;
import ua.tc.marketplace.service.DistanceService;
import ua.tc.marketplace.service.LocationService;
import ua.tc.marketplace.util.googleapi.DistanceCalculator;

@Slf4j
@Service
@RequiredArgsConstructor
public class DistanceServiceImpl implements DistanceService {

  private final DistanceRepository distanceRepository;
  private final LocationService locationService;
  private final DistanceCalculator distanceCalculator;

  @Transactional
  @Override
  public Page<AdDto> calculateDistance(Location location1, Page<AdDto> adDtoPage) {
    // 1. Fetch existing distances
    Map<Long, Distance> existingDistances = fetchExistingDistances(location1, adDtoPage);

    // 2. Separate ads with and without existing distances
    List<AdDto> adsWithExistingDistances =
        adDtoPage.getContent().stream()
            .map(
                adDto ->
                    existingDistances.containsKey(adDto.location().getId())
                        ? setDistanceInAdDto(
                            adDto,
                            existingDistances.get(adDto.location().getId()).getDistanceInMeters())
                        : adDto)
            .toList();

    List<AdDto> adsToCalculate =
        adDtoPage.getContent().stream()
            .filter(adDto -> !existingDistances.containsKey(adDto.location().getId()))
            .toList();

    // 3. Calculate and persist new distances
    List<AdDto> updatedAds =
        adsToCalculate.isEmpty()
            ? List.of()
            : calculateAndPersistDistances(location1, adsToCalculate);

    // 4. Combine the results, ensuring no null distances
    Map<Long, AdDto> adDtoMap = new HashMap<>();
    adsWithExistingDistances.forEach(adDto -> adDtoMap.put(adDto.id(), adDto));
    updatedAds.forEach(adDto -> adDtoMap.put(adDto.id(), adDto));

    List<AdDto> allAds = new ArrayList<>(adDtoMap.values());

    return new PageImpl<>(allAds, adDtoPage.getPageable(), adDtoPage.getTotalElements());
  }

  private Map<Long, Distance> fetchExistingDistances(Location location1, Page<AdDto> adDtoPage) {
    List<Long> location2Ids =
        adDtoPage.getContent().stream().map(adDto -> adDto.location().getId()).toList();

    return distanceRepository
        .findByLocation1IdAndLocation2IdIn(location1.getId(), location2Ids)
        .stream()
        .collect(
            Collectors.toMap(distance -> distance.getLocation2().getId(), distance -> distance));
  }

  private List<AdDto> calculateAndPersistDistances(Location location1, List<AdDto> adsToCalculate) {
    Map<Long, String> adIdToAddressMap =
        adsToCalculate.stream()
            .collect(
                Collectors.toMap(
                    AdDto::id, adDto -> locationService.getFullAddress(adDto.location())));

    Map<Long, Double> calculatedDistances =
        distanceCalculator.calculate(locationService.getFullAddress(location1), adIdToAddressMap);

    List<AdDto> updatedAds =
        adsToCalculate.stream()
            .map(adDto -> setDistanceInAdDto(adDto, calculatedDistances.get(adDto.id())))
            .toList();

    List<Distance> distancesToSave =
        updatedAds.stream()
            .map(adDto -> new Distance(null, location1, adDto.location(), adDto.distance()))
            .toList();

    distanceRepository.saveAll(distancesToSave);
    return updatedAds;
  }

  private AdDto setDistanceInAdDto(AdDto adDto, Double distanceInMeters) {
    return new AdDto(
        adDto.id(),
        adDto.authorId(),
        adDto.title(),
        adDto.description(),
        adDto.price(),
        adDto.location(),
        distanceInMeters, // Set the calculated distance
        adDto.photos(),
        adDto.thumbnail(),
        adDto.categoryId(),
        adDto.adAttributes(),
        adDto.createdAt(),
        adDto.updatedAt());
  }
}
