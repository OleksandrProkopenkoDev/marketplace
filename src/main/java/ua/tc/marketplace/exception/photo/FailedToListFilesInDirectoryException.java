package ua.tc.marketplace.exception.photo;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

/**
 * Exception thrown when listing files in a directory fails.
 *
 * <p>This exception is used to signal that an attempt to list files in a specified directory has
 * failed. It extends the {@link CustomRuntimeException} class and provides a detailed error message
 * including the directory name and the root cause of the failure. The associated HTTP status for
 * this exception is {@code HttpStatus.CONFLICT}.
 */
public class FailedToListFilesInDirectoryException extends CustomRuntimeException {

  private static final String ERROR_MESSAGE = "Failed to list files in directory: %s";
  private static final HttpStatus STATUS = HttpStatus.CONFLICT;

  public FailedToListFilesInDirectoryException(String filename, Throwable e) {
    super(ERROR_MESSAGE.formatted(filename), e, STATUS);
  }
}
