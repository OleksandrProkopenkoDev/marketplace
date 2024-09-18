package ua.tc.marketplace.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ua.tc.marketplace.model.dto.ad.AdDto;
import ua.tc.marketplace.model.entity.Distance;
import ua.tc.marketplace.model.entity.Location;
import ua.tc.marketplace.repository.DistanceRepository;
import ua.tc.marketplace.service.LocationService;
import ua.tc.marketplace.util.googleapi.DistanceCalculator;

@ExtendWith(MockitoExtension.class)
class DistanceServiceImplTest {
  @Mock private DistanceRepository distanceRepository;
  @Mock private LocationService locationService;
  @Mock private DistanceCalculator distanceCalculator;

  @InjectMocks DistanceServiceImpl underTest;

  @Test
  void calculateDistance_shouldReturnAdsWithExistingDistances() {
    // Given
    Location location1 = new Location(1L, "USA, City1, Street1, 12345");
    Location location2 = new Location(2L, "USA, City2, Street2, 67890");

    AdDto adDto =
        new AdDto(
            1L,
            1L,
            "Title",
            "Description",
            new BigDecimal("100.0"),
            location2,
            null,
            List.of(),
            null,
            1L,
            List.of(),
            null,
            null);
    Page<AdDto> adDtoPage = new PageImpl<>(List.of(adDto));

    Distance existingDistance = new Distance(1L, location1, location2, 5000.0);
    when(distanceRepository.findByLocation1IdAndLocation2IdIn(eq(location1.getId()), anyList()))
        .thenReturn(List.of(existingDistance));

    // When
    Page<AdDto> result = underTest.calculateDistance(location1, adDtoPage);

    // Then
    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    assertEquals(5000.0, result.getContent().getFirst().distance());
    verify(distanceRepository).findByLocation1IdAndLocation2IdIn(eq(location1.getId()), anyList());
    verifyNoInteractions(distanceCalculator);
  }

  @Test
  void calculateDistance_shouldCalculateAndPersistDistances() {
    // Given
    Location location1 = new Location(1L, "USA, City1, Street1, 12345");
    Location location2 = new Location(2L, "USA, City2, Street2, 67890");

    AdDto adDto =
        new AdDto(
            1L,
            1L,
            "Title",
            "Description",
            new BigDecimal("100.0"),
            location2,
            null,
            List.of(),
            null,
            1L,
            List.of(),
            null,
            null);
    Distance savedDistance = new Distance(1L, location1, location2, 10000.0);
    Page<AdDto> adDtoPage = new PageImpl<>(List.of(adDto));

    when(distanceRepository.findByLocation1IdAndLocation2IdIn(eq(location1.getId()), anyList()))
        .thenReturn(List.of()); // No existing distances


    when(distanceCalculator.calculate(anyString(), anyMap()))
        .thenReturn(Map.of(1L, 10000.0)); // Calculated distance
    when(distanceRepository.saveAll(anyList())).thenReturn(List.of(savedDistance));
    // When
    Page<AdDto> result = underTest.calculateDistance(location1, adDtoPage);

    // Additional debug output
    AdDto resultAdDto = result.getContent().getFirst();

    // Then
    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    assertEquals(10000.0, resultAdDto.distance(), "The calculated distance should be correctly set in the AdDto.");

    // Verify interactions
    verify(distanceRepository).saveAll(anyList());
    verify(distanceRepository).findByLocation1IdAndLocation2IdIn(eq(location1.getId()), anyList());
    verify(distanceCalculator).calculate(anyString(), anyMap());
  }

}
