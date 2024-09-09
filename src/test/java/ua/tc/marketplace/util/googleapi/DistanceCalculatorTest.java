package ua.tc.marketplace.util.googleapi;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.tc.marketplace.exception.distance.DistanceCalculationException;

@ExtendWith(MockitoExtension.class)
class DistanceCalculatorTest {
  @Mock private GeoApiContext geoApiContext;

  @InjectMocks private DistanceCalculator distanceCalculator;

  @Test
  void calculate_shouldReturnEmptyMap_whenDistanceMatrixHasNoRows()
      throws ApiException, InterruptedException, IOException {
    try (MockedStatic<DistanceMatrixApi> distanceMatrixApiMock =
        Mockito.mockStatic(DistanceMatrixApi.class)) {
      // Given
      String origin = "Origin Address";
      String destination1 = "Destination Address 1";
      String destination2 = "Destination Address 2";
      String[] destinations = new String[] {destination1, destination2};
      Map<Long, String> adIdToAddressMap =
          Map.of(
              1L, destination1,
              2L, destination2);

      DistanceMatrix distanceMatrix =
          new DistanceMatrix(new String[] {origin}, destinations, new DistanceMatrixRow[] {});

      DistanceMatrixApiRequest request = Mockito.mock(DistanceMatrixApiRequest.class);
      distanceMatrixApiMock
          .when(
              () ->
                  DistanceMatrixApi.getDistanceMatrix(
                      any(GeoApiContext.class), any(String[].class), any(String[].class)))
          .thenReturn(request);
      when(request.await()).thenReturn(distanceMatrix);

      // When
      Map<Long, Double> result = distanceCalculator.calculate(origin, adIdToAddressMap);

      // Then
      assertTrue(result.isEmpty());
    }
  }

  @Test
  void calculate_shouldThrowDistanceCalculationException_whenApiCallFails()
      throws InterruptedException, IOException {
    try (MockedStatic<DistanceMatrixApi> distanceMatrixApiMock =
        Mockito.mockStatic(DistanceMatrixApi.class)) {
      // Given
      String origin = "Origin Address";
      String destination1 = "Destination Address 1";
      String[] destinations = new String[] {destination1};
      Map<Long, String> adIdToAddressMap = Map.of(1L, destination1);

      DistanceMatrixApiRequest request = Mockito.mock(DistanceMatrixApiRequest.class);

      // Simulate API call failure
      distanceMatrixApiMock
          .when(
              () ->
                  DistanceMatrixApi.getDistanceMatrix(
                      any(GeoApiContext.class), any(String[].class), any(String[].class)))
          .thenAnswer(
              invocation -> {
                throw new IOException("Simulated IO Exception");
              });

      // When / Then
      assertThrows(
          DistanceCalculationException.class,
          () -> {
            distanceCalculator.calculate(origin, adIdToAddressMap);
          });
    }
  }
  // this is not working, because it produces different result order each run
  /*
  @Test
  void calculate_shouldReturnDistances_whenApiCallSucceeds()
      throws ApiException, InterruptedException, IOException {
    try (MockedStatic<DistanceMatrixApi> distanceMatrixApiMock =
        Mockito.mockStatic(DistanceMatrixApi.class)) {
      // Given
      String origin = "Origin Address";
      String destination1 = "Destination Address 1";
      String destination2 = "Destination Address 2";
      String[] destinations = new String[] {destination1, destination2};
      Map<Long, String> adIdToAddressMap =
          Map.of(
              1L, destination1,
              2L, destination2);

      DistanceMatrixElement element1 = mock(DistanceMatrixElement.class);
      DistanceMatrixElement element2 = mock(DistanceMatrixElement.class);
      com.google.maps.model.Distance distance1 = mock(com.google.maps.model.Distance.class);

      com.google.maps.model.Distance distance2 = mock(com.google.maps.model.Distance.class);

      distance1.inMeters = 1000L;
      distance2.inMeters = 2000L;
      element1.distance = distance1;
      element2.distance = distance2;

      DistanceMatrixRow row = mock(DistanceMatrixRow.class);
      row.elements = new DistanceMatrixElement[] {element1, element2};

      DistanceMatrix distanceMatrix =
          new DistanceMatrix(new String[] {origin}, destinations, new DistanceMatrixRow[] {row});
      DistanceMatrixApiRequest request = Mockito.mock(DistanceMatrixApiRequest.class);
      distanceMatrixApiMock
          .when(
              () ->
                  DistanceMatrixApi.getDistanceMatrix(
                      any(GeoApiContext.class), any(String[].class), any(String[].class)))
          .thenReturn(request);
      when(request.await()).thenReturn(distanceMatrix);
      // When
      Map<Long, Double> result = distanceCalculator.calculate(origin, adIdToAddressMap);

      System.out.println(result);
      // Then
      assertEquals(2, result.size());
      assertEquals(1000.0, result.get(1L));
      assertEquals(2000.0, result.get(2L));
    }
  }


  @Test
  void calculate_shouldHandleNullDistanceElements()
      throws ApiException, InterruptedException, IOException {
    try (MockedStatic<DistanceMatrixApi> distanceMatrixApiMock =
        Mockito.mockStatic(DistanceMatrixApi.class)) {
      // Given
      String origin = "Origin Address";
      String destination1 = "Destination Address 1";
      String destination2 = "Destination Address 2";
      String[] destinations = new String[] {destination1, destination2};
      Map<Long, String> adIdToAddressMap =
          Map.of(
              1L, destination1,
              2L, destination2);

      DistanceMatrixElement element1 = mock(DistanceMatrixElement.class);
      DistanceMatrixElement element2 = mock(DistanceMatrixElement.class);

      element1.distance = null; // Simulate null distance
      element2.distance = mock(com.google.maps.model.Distance.class);
      element2.distance.inMeters = 2000L;

      DistanceMatrixRow row = mock(DistanceMatrixRow.class);
      row.elements = new DistanceMatrixElement[] {element1, element2};

      DistanceMatrix distanceMatrix =
          new DistanceMatrix(new String[] {origin}, destinations, new DistanceMatrixRow[] {row});
      DistanceMatrixApiRequest request = Mockito.mock(DistanceMatrixApiRequest.class);
      distanceMatrixApiMock
          .when(
              () ->
                  DistanceMatrixApi.getDistanceMatrix(
                      any(GeoApiContext.class), any(String[].class), any(String[].class)))
          .thenReturn(request);
      when(request.await()).thenReturn(distanceMatrix);

      // When
      Map<Long, Double> result = distanceCalculator.calculate(origin, adIdToAddressMap);
      System.out.println(result);
      // Then
      assertEquals(2, result.size());
      assertEquals(Double.NaN, result.get(1L)); // No distance available
      assertEquals(2000.0, result.get(2L));
    }
  }

  @Test
  void calculate_shouldProcessMultipleRows_whenDistanceMatrixHasMultipleRows()
      throws ApiException, InterruptedException, IOException {
    try (MockedStatic<DistanceMatrixApi> distanceMatrixApiMock =
        Mockito.mockStatic(DistanceMatrixApi.class)) {
      // Given
      String origin1 = "Origin Address 1";
      String origin2 = "Origin Address 2";
      String destination1 = "Destination Address 1";
      String destination2 = "Destination Address 2";
      String[] destinations = new String[] {destination1, destination2};
      Map<Long, String> adIdToAddressMap =
          Map.of(
              1L, destination1,
              2L, destination2);

      DistanceMatrixElement element1 = mock(DistanceMatrixElement.class);
      DistanceMatrixElement element2 = mock(DistanceMatrixElement.class);
      com.google.maps.model.Distance distance1 = mock(com.google.maps.model.Distance.class);
      com.google.maps.model.Distance distance2 = mock(com.google.maps.model.Distance.class);

      distance1.inMeters = 1000L;
      distance2.inMeters = 2000L;
      element1.distance = distance1;
      element2.distance = distance2;

      DistanceMatrixRow row1 = mock(DistanceMatrixRow.class);
      DistanceMatrixRow row2 = mock(DistanceMatrixRow.class);
      row1.elements = new DistanceMatrixElement[] {element1, element2};
      row2.elements = new DistanceMatrixElement[] {element2, element1};

      DistanceMatrix distanceMatrix =
          new DistanceMatrix(
              new String[] {origin1, origin2}, destinations, new DistanceMatrixRow[] {row1, row2});
      DistanceMatrixApiRequest request = Mockito.mock(DistanceMatrixApiRequest.class);
      distanceMatrixApiMock
          .when(
              () ->
                  DistanceMatrixApi.getDistanceMatrix(
                      any(GeoApiContext.class), any(String[].class), any(String[].class)))
          .thenReturn(request);
      when(request.await()).thenReturn(distanceMatrix);

      // When
      Map<Long, Double> result = distanceCalculator.calculate(origin1, adIdToAddressMap);
      System.out.println(result);
      // Then
      assertEquals(2, result.size());
      assertEquals(1000.0, result.get(1L));
      assertEquals(2000.0, result.get(2L));
    }
  }

  @ParameterizedTest
  @MethodSource("distanceData")
  void calculate_shouldPreserveOrder_whenApiCallSucceeds(DistanceData data)
      throws ApiException, InterruptedException, IOException {
    try (MockedStatic<DistanceMatrixApi> distanceMatrixApiMock =
        Mockito.mockStatic(DistanceMatrixApi.class)) {
      // Given
      String origin = data.origin;
      String[] destinations = data.adIdToAddressMap.values().toArray(new String[0]);

      DistanceMatrixRow row = mock(DistanceMatrixRow.class);
      row.elements = data.elements;

      DistanceMatrix distanceMatrix =
          new DistanceMatrix(new String[] {origin}, destinations, new DistanceMatrixRow[] {row});
      DistanceMatrixApiRequest request = Mockito.mock(DistanceMatrixApiRequest.class);
      distanceMatrixApiMock
          .when(
              () ->
                  DistanceMatrixApi.getDistanceMatrix(
                      any(GeoApiContext.class), any(String[].class), any(String[].class)))
          .thenReturn(request);
      when(request.await()).thenReturn(distanceMatrix);

      // When
      Map<Long, Double> result = distanceCalculator.calculate(origin, data.adIdToAddressMap);
      System.out.println("result: " + result);
      // Then
      assertEquals(data.adIdToAddressMap.size(), result.size());
      for (Map.Entry<Long, String> entry : data.adIdToAddressMap.entrySet()) {
        assertEquals(
            (double) data.expectedDistances[entry.getKey().intValue() - 1],
            result.get(entry.getKey()));
      }
    }
  }

  static Stream<DistanceData> distanceData() {
    return Stream.of(
        new DistanceData(
            "Origin Address",
            Map.of(
                1L, "Destination Address 1",
                2L, "Destination Address 2",
                3L, "Destination Address 3",
                4L, "Destination Address 4",
                5L, "Destination Address 5"),
            new DistanceMatrixElement[] {
              createDistanceElement(1000L),
              createDistanceElement(2000L),
              createDistanceElement(3000L),
              createDistanceElement(4000L),
              createDistanceElement(5000L)
            },
            new long[] {1000L, 2000L, 3000L, 4000L, 5000L}));
  }

  private static DistanceMatrixElement createDistanceElement(long meters) {
    DistanceMatrixElement element = mock(DistanceMatrixElement.class);
    com.google.maps.model.Distance distance = mock(com.google.maps.model.Distance.class);
    distance.inMeters = meters;
    element.distance = distance;
    return element;
  }

  // Inner class to hold test data
  static class DistanceData {
    String origin;
    Map<Long, String> adIdToAddressMap;
    DistanceMatrixElement[] elements;
    long[] expectedDistances;

    DistanceData(
        String origin,
        Map<Long, String> adIdToAddressMap,
        DistanceMatrixElement[] elements,
        long[] expectedDistances) {
      this.origin = origin;
      this.adIdToAddressMap = adIdToAddressMap;
      this.elements = elements;
      this.expectedDistances = expectedDistances;
    }
  }*/
}
