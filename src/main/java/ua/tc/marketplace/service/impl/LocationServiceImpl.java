package ua.tc.marketplace.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.tc.marketplace.model.entity.Location;
import ua.tc.marketplace.service.LocationService;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

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
      return Optional.of(newLocationFromString(locationString));
    }
    return Optional.empty();
  }

  @Override
  public String getFullAddress(Location location) {
    // Create a list of non-null and non-empty fields
    String[] fields = {
      location.getStreet(), location.getCity(), location.getCountry(), location.getZipcode()
    };

    // Join the non-null and non-empty fields with ", " separator
    return Arrays.stream(fields)
        .filter(field -> field != null && !field.trim().isEmpty())
        .collect(Collectors.joining(", "));
  }

  private Location newLocationFromString(String locationString) {
    // Split the input string by comma and trim whitespace
    List<String> parts =
        Arrays.stream(locationString.split(","))
            .map(String::trim)
            .filter(part -> !part.isEmpty())
            .toList();

    // Initialize variables with default values
    String street = null;
    String houseNumber = null;
    String city = null;
    String country = null;
    String zipcode = null;

    // Assign values based on the number of parts
    if (!parts.isEmpty()) {
      // Always set the last part as zipcode if it looks like a postal code
      if (parts.size() == 1) {
        city = parts.getFirst();
      } else {
        // Last part is zipcode if it is numeric or resembles a postal code
        String lastPart = parts.getLast();
        if (lastPart.matches("\\d+")) { // Basic check for zipcode
          zipcode = lastPart;
          parts = parts.subList(0, parts.size() - 1); // Remove zipcode from parts
        }

        // If now we have remaining parts
        if (!parts.isEmpty()) {
          // Last remaining part is assumed to be city
          city = parts.getLast();
          // All other parts are treated as street and house number
          if (parts.size() > 1) {
            street = String.join(", ", parts.subList(0, parts.size() - 1));
          }
        }
      }
    }

    // Construct Location object with corrected fields
    return new Location(
        null, // id is not parsed from the string
        Optional.ofNullable(country).orElse(null),
        Optional.ofNullable(city).orElse(null),
        Optional.ofNullable(street).orElse(null)
            + " "
            + Optional.ofNullable(houseNumber).orElse(""),
        Optional.ofNullable(zipcode).orElse(null));
  }
}
