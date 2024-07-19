package ua.tc.marketplace.exception.photo;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

/**
 * Exception thrown when a photo is not found in the system.
 *
 * <p>This exception is a subclass of {@link CustomRuntimeException} and is used to signal
 * that a photo with a specific ID does not exist. It carries a predefined error message
 * and an HTTP status code of NOT_FOUND (404).
 *
 * <p>The error message includes the photo ID that was not found.
 */

public class PhotoNotFoundException extends CustomRuntimeException {

  private static final String ERROR_MESSAGE = "Photo with id %s is not found.";
  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public PhotoNotFoundException(Long photoId) {
    super(ERROR_MESSAGE.formatted(photoId), STATUS);
  }

  public PhotoNotFoundException(Long photoId, Throwable cause) {
    super(ERROR_MESSAGE.formatted(photoId), cause, STATUS);
  }
}
