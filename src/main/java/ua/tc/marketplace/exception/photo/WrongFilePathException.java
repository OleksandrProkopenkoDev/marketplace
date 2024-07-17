package ua.tc.marketplace.exception.photo;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

/**
 * Exception thrown when there is an issue with a file path.
 *
 * <p>This exception is used to signal that an attempt to find or create a directory at the
 * specified path has failed. It extends the {@link CustomRuntimeException} class and provides a
 * detailed error message including the problematic path. The associated HTTP status for this
 * exception is {@code HttpStatus.CONFLICT}.
 */
public class WrongFilePathException extends CustomRuntimeException {

  private static final String ERROR_MESSAGE = "Failed to find or create directory %s";
  private static final HttpStatus STATUS = HttpStatus.CONFLICT;

  public WrongFilePathException(String path, Throwable cause) {
    super(ERROR_MESSAGE.formatted(path), cause, STATUS);
  }
}
