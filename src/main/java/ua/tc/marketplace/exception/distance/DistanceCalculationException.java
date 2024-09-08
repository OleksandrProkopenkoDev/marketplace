package ua.tc.marketplace.exception.distance;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

public class DistanceCalculationException extends CustomRuntimeException {

  private static final String MESSAGE =
      "Distance calculation failed. Error message: %s. | Address 1: %s . Addresses: %s";
  private static final HttpStatus STATUS = HttpStatus.CONFLICT;

  public DistanceCalculationException(
      String message, String fullAddress, String[] destinationAddresses) {
    super(MESSAGE.formatted(message, fullAddress, destinationAddresses), STATUS);
  }
}
