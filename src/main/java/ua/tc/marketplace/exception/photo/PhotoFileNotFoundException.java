package ua.tc.marketplace.exception.photo;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

/**
 * Exception thrown when a photo file is not found.
 *
 * <p>This exception is used to signal that a file associated with a photo, identified by its
 * filename, is not found. It extends the {@link CustomRuntimeException} class and provides a
 * detailed error message including the filename. The associated HTTP status for this exception is
 * {@code HttpStatus.NOT_FOUND}.
 */
public class PhotoFileNotFoundException extends CustomRuntimeException {

  private static final String ERROR_MESSAGE = "File of photo with name %s is not found.";
  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public PhotoFileNotFoundException(String filename) {
    super(ERROR_MESSAGE.formatted(filename), STATUS);
  }

  public PhotoFileNotFoundException(String filename, Throwable cause) {
    super(ERROR_MESSAGE.formatted(filename), cause, STATUS);
  }
}
