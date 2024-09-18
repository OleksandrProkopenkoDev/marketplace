package ua.tc.marketplace.service.impl;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.tc.marketplace.model.entity.Location;
import ua.tc.marketplace.repository.LocationRepository;
import ua.tc.marketplace.service.LocationService;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

  private final LocationRepository locationRepository;

  @Override
  public Optional<Location> extractLocationFromParams(Map<String, String> filterCriteria) {
    String locationString;
    Optional<Entry<String, String>> locationEntry =
        filterCriteria.entrySet().stream()
            .filter(entry -> entry.getValue() != null)
            .filter(entry -> entry.getKey().equals("location"))
            .findFirst();
    if (locationEntry.isPresent()) {
      locationString = locationEntry.get().getValue();
      return Optional.of(new Location(null, locationString));
    }
    return Optional.empty();
  }

  @Override
  public Location save(Location location) {
    return locationRepository.save(location);
  }

  @Override
  public Optional<Location> findByParams(Location location) {
    return locationRepository.findByAddress(location.getAddress());
  }
}
