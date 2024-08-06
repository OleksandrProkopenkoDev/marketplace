package ua.tc.marketplace.exception.tests;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

/**
 * FailedLoadJsonResourceException is thrown when there is an error loading a JSON resource file.
 *
 * <p>This exception extends CustomRuntimeException and provides a specific error message indicating
 * the filename of the resource that failed to load. The HTTP status code associated with this
 * exception is NOT_FOUND.
 */
public class FailedLoadJsonResourceException extends CustomRuntimeException {

  private static final String ERROR_MESSAGE = "Failed to load json Resource with filename: %s ";
  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public FailedLoadJsonResourceException(String filename) {
    super(ERROR_MESSAGE.formatted(filename), STATUS);
  }
}
