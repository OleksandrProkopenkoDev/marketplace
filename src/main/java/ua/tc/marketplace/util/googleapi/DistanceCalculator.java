package ua.tc.marketplace.util.googleapi;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import ua.tc.marketplace.exception.distance.DistanceCalculationException;

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
    Map<Long, Double> adIdToDistanceMap = new HashMap<>();

      // Convert the map values to a String array for destination addresses
      String[] destinationAddresses = adIdToAddressMap.values().toArray(new String[0]);
    try {

      // Make the API call to get the distance matrix
      DistanceMatrix distanceMatrix =
          DistanceMatrixApi.getDistanceMatrix(
                  geoApiContext,
                  new String[] {fullAddress}, // Origin address
                  destinationAddresses // Destination addresses
                  )
              .await();

      // Process the result from the distance matrix
      if (distanceMatrix.rows.length > 0) {
        DistanceMatrixElement[] elements = distanceMatrix.rows[0].elements;

        IntStream.range(0, elements.length)
            .forEach(
                i -> {
                  double distanceInMeters = elements[i].distance.inMeters;
                  adIdToAddressMap.entrySet().stream()
                      .filter(entry -> entry.getValue().equals(destinationAddresses[i]))
                      .map(Map.Entry::getKey)
                      .findFirst()
                      .ifPresent(adId -> adIdToDistanceMap.put(adId, distanceInMeters));
                });
      }
    } catch (Exception e) {
      throw new DistanceCalculationException(e.getMessage(), fullAddress, destinationAddresses);
    }

    return adIdToDistanceMap;
  }
}
