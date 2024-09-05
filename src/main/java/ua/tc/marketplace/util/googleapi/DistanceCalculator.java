package ua.tc.marketplace.util.googleapi;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;

public class DistanceCalculator {
  public static void main(String[] args) {
    String apiKey = "key";
    /*
        * Kyiv (Capital) Address:

    Ukrainian: "вул. Хрещатик, 22, Київ, Україна, 01001"
    Transliterated: "Khreshchatyk St, 22, Kyiv, Ukraine, 01001"
    Lviv Address:

    Ukrainian: "пл. Ринок, 1, Львів, Україна, 79000"
    Transliterated: "Rynok Square, 1, Lviv, Ukraine, 79000"
    Odessa Address:

    Ukrainian: "вул. Дерибасівська, 10, Одеса, Україна, 65000"
    Transliterated: "Deribasivska St, 10, Odessa, Ukraine, 65000"
    Kharkiv Address:

    Ukrainian: "просп. Науки, 14, Харків, Україна, 61000"
    Transliterated: "Nauky Ave, 14, Kharkiv, Ukraine, 61000"
    Dnipro Address:

    Ukrainian: "вул. Дмитра Яворницького, 1, Дніпро, Україна, 49000"
    Transliterated: "Dmytro Yavornytskoho St, 1, Dnipro, Ukraine, 49000"*/
    String originAddress = "Одеса";
    String destinationAddress = "Львів";

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
