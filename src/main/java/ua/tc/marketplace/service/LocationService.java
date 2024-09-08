package ua.tc.marketplace.service;

import java.util.Map;
import java.util.Optional;
import ua.tc.marketplace.model.entity.Location;

public interface LocationService {

  Optional<Location> extractLocationFromParams(Map<String, String> filterCriteria);

  String getFullAddress(Location location);

  Location save(Location location);
}
