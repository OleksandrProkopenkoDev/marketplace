package ua.tc.marketplace.exception.ad;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

/**
 * Exception thrown when an advertisement is not found.
 *
 * <p>This exception is used to signal that an advertisement with the specified ID is not found in
 * the system. It extends the {@link CustomRuntimeException} class and provides a detailed error
 * message including the ad ID. The associated HTTP status for this exception is {@code
 * HttpStatus.NOT_FOUND}.
 */
public class AdNotFoundException extends CustomRuntimeException {

  private static final String ERROR_MESSAGE = "Ad with id %s is not found";
  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public AdNotFoundException(Long adId) {
    super(ERROR_MESSAGE.formatted(adId), STATUS);
  }
}
