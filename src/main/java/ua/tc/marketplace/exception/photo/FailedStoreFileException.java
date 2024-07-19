package ua.tc.marketplace.exception.photo;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

/**
 * Exception thrown when a file storage operation fails.
 *
 * <p>This exception is used to signal that an attempt to store a file associated with a photo has
 * failed. It extends the {@link CustomRuntimeException} class and provides a detailed error message
 * including the filename and the root cause of the failure. The associated HTTP status for this
 * exception is {@code HttpStatus.CONFLICT}.
 */
public class FailedStoreFileException extends CustomRuntimeException {

  private static final String ERROR_MESSAGE = "Failed to store file %s";
  private static final HttpStatus STATUS = HttpStatus.CONFLICT;

  public FailedStoreFileException(String filename, Throwable cause) {
    super(ERROR_MESSAGE.formatted(filename), cause, STATUS);
  }
}
