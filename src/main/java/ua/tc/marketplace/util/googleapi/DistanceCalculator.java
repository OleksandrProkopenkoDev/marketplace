package ua.tc.marketplace.util.googleapi;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;

public class DistanceCalculator {
  public static void main(String[] args) {
    String apiKey = "AIzaSyB_PrwoGyWWCRmC8kpcJRtBSUn6iGfd4Vo";
    String originAddress = "1600 Amphitheatre Parkway, Mountain View, CA";
    String destinationAddress = "Times Square, New York, NY";

    try {
      GeoApiContext geoApiContext = new GeoApiContext.Builder().apiKey(apiKey).build();

      // Construct the Distance Matrix request directly within DistanceMatrixApi.getDistanceMatrix()
      DistanceMatrix distanceMatrix =
          DistanceMatrixApi.getDistanceMatrix(
                  geoApiContext,
                  // Origins (can be a String address or a String array)
                  new String[] {originAddress},
                  // Destinations (can be a String address or a String array)
                  new String[] {destinationAddress})
              .await();

      if (distanceMatrix.rows.length > 0 && distanceMatrix.rows[0].elements.length > 0) {
        double distance = distanceMatrix.rows[0].elements[0].distance.inMeters;
        System.out.println(
            "Distance between "
                + originAddress
                + " and "
                + destinationAddress
                + " is: "
                + distance
                + " meters");
      } else {
        System.out.println("Error: Could not calculate distance.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
