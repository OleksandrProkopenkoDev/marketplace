package ua.tc.marketplace.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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
    // 1. Partition ads into those with existing distances in DB and those without
    Map<Boolean, List<AdDto>> distanceExistsMap =
        adDtoPage.getContent().stream()
            .collect(
                Collectors.partitioningBy(
                    adDto ->
                        distanceRepository
                            .findByLocation1IdAndLocation2Id(
                                location1.getId(), adDto.location().getId())
                            .isPresent()));

    // 2. Get the lists of ads
    List<AdDto> adDtoListExistingLocation = distanceExistsMap.get(true);
    List<AdDto> adDtoListToCalculateLocation = distanceExistsMap.get(false);

    // 3. Fetch existing distances and set them in AdDto using streams
    adDtoListExistingLocation =
        adDtoListExistingLocation.stream()
            .map(
                adDto ->
                    distanceRepository
                        .findByLocation1IdAndLocation2Id(
                            location1.getId(), adDto.location().getId())
                        .map(distance -> getAdDto(adDto, distance.getDistanceInMeters()))
                        .orElse(adDto))
            .toList();
    log.info("Fetching distances from db: {}", adDtoListExistingLocation.stream().map(AdDto::location).toList());

    // 4. Check if there are ads to calculate distances for
    List<AdDto> updatedAds = adDtoListToCalculateLocation;
    if (!adDtoListToCalculateLocation.isEmpty()) {
      // Prepare addresses for Google API using streams
      Map<Long, String> adIdToAddressMap =
          adDtoListToCalculateLocation.stream()
              .collect(
                  Collectors.toMap(
                      AdDto::id,
                      adDto -> locationService.getFullAddress(adDto.location())));

      log.info("idToAddressMap: {}", adIdToAddressMap);

      // Make the Google API call to calculate distances
      Map<Long, Double> calculatedDistances =
          distanceCalculator.calculate(locationService.getFullAddress(location1), adIdToAddressMap);
      log.info("Make Google API call to get distance from {} to {}", locationService.getFullAddress(location1), adIdToAddressMap);

      // Set calculated distances and create Distance entities to save in DB using streams
      updatedAds =
          adDtoListToCalculateLocation.stream()
              .map(adDto -> {
                Double distanceInMeters = calculatedDistances.get(adDto.id());
                return getAdDto(adDto, distanceInMeters);
              })
              .toList();

      // Save distances to DB using streams
      List<Distance> distancesToSave =
          updatedAds.stream()
              .map(adDto -> new Distance(null, location1, adDto.location(), adDto.distance()))
              .toList();
      distanceRepository.saveAll(distancesToSave);
    } else {
      log.info("No ads to calculate distances for.");
    }

    // 5. Combine the lists and return the result as a Page object
    List<AdDto> allAds =
        Stream.concat(adDtoListExistingLocation.stream(), updatedAds.stream()).toList();

    return new PageImpl<>(allAds, adDtoPage.getPageable(), adDtoPage.getTotalElements());
  }

  private @NotNull AdDto getAdDto(AdDto adDto, Double distanceInMeters) {
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
