package ua.tc.marketplace.util.googleapi;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.tc.marketplace.exception.distance.DistanceCalculationException;

@Slf4j
@Component
public class DistanceCalculator {

  private GeoApiContext geoApiContext;

  // Inject the API key from application.properties
  @Value("${google.maps.api.key}")
  private String apiKey;

  // Initialize GeoApiContext after the bean is constructed
  @PostConstruct
  private void init() {
    this.geoApiContext = new GeoApiContext.Builder().apiKey(apiKey).build();
  }

  public Map<Long, Double> calculate(String fullAddress, Map<Long, String> adIdToAddressMap) {
    try {
      DistanceMatrix distanceMatrix =
          fetchDistanceMatrix(fullAddress, adIdToAddressMap.values().toArray(new String[0]));

      return processDistanceMatrix(distanceMatrix, adIdToAddressMap);
    } catch (Exception e) {
      throw new DistanceCalculationException(
          e.getMessage(), fullAddress, adIdToAddressMap.values().toArray(new String[0]));
    }
  }

  private DistanceMatrix fetchDistanceMatrix(String origin, String[] destinations)
      throws InterruptedException, IOException, ApiException {
    return DistanceMatrixApi.getDistanceMatrix(geoApiContext, new String[] {origin}, destinations)
        .await();
  }

  private Map<Long, Double> processDistanceMatrix(
      DistanceMatrix distanceMatrix, Map<Long, String> adIdToAddressMap) {
    if (distanceMatrix.rows.length == 0) {
      return new HashMap<>();
    }

    DistanceMatrixElement[] elements = distanceMatrix.rows[0].elements;

    // Create a map to associate each address with its distance
    Map<String, Double> addressToDistanceMap =
        IntStream.range(0, elements.length)
            .boxed()
            .collect(
                Collectors.toMap(
                    i -> adIdToAddressMap.values().toArray(new String[0])[i], // Address
                    i -> (double) elements[i].distance.inMeters // Distance
                    ));

    // Create the final map of ad IDs to distances
    return adIdToAddressMap.entrySet().stream()
        .collect(
            Collectors.toMap(
                Map.Entry::getKey, // Ad ID
                entry -> addressToDistanceMap.get(entry.getValue()) // Distance
                ));
  }
}
