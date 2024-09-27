package ua.tc.marketplace.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.tc.marketplace.model.entity.Location;
import ua.tc.marketplace.repository.LocationRepository;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {

  @Mock private LocationRepository locationRepository;

  @InjectMocks private LocationServiceImpl underTest;

  @ParameterizedTest
  @MethodSource("locationData")
  void extractLocationFromParams_shouldReturnCorrectLocation_whenLocationIsPresent(
      Map<String, String> filterCriteria, Location expectedLocation) {

    // When
    Optional<Location> result = underTest.extractLocationFromParams(filterCriteria);

    // Then
    assertTrue(result.isPresent());
    assertEquals(expectedLocation.getAddress(), result.get().getAddress());
  }

  static Stream<Arguments> locationData() {
    return Stream.of(
        Arguments.of(
            Map.of("location", "USA, Springfield, Main St, 123, 12345"),
            new Location(null, "USA, Springfield, Main St, 123, 12345")),
        Arguments.of(
            Map.of("location", "USA, Springfield, Main St"),
            new Location(null, "USA, Springfield, Main St")),
        Arguments.of(
            Map.of("location", "USA, Springfield"), new Location(null, "USA, Springfield")),
        Arguments.of(Map.of("location", "Springfield"), new Location(null, "Springfield")));
  }

  @Test
  void extractLocationFromParams_shouldReturnEmptyOptional_whenLocationIsNotPresent() {
    // Given
    Map<String, String> filterCriteria = new HashMap<>();
    filterCriteria.put("otherKey", "Some value");

    // When
    Optional<Location> result = underTest.extractLocationFromParams(filterCriteria);

    // Then
    assertFalse(result.isPresent());
  }

  @Test
  void extractLocationFromParams_shouldReturnEmptyOptional_whenLocationValueIsNull() {
    // Given
    Map<String, String> filterCriteria = new HashMap<>();
    filterCriteria.put("location", null);

    // When
    Optional<Location> result = underTest.extractLocationFromParams(filterCriteria);

    // Then
    assertFalse(result.isPresent());
  }
}
