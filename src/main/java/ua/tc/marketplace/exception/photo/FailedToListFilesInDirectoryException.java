package ua.tc.marketplace.exception.photo;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

public class FailedToListFilesInDirectoryException extends CustomRuntimeException {

  private static final String ERROR_MESSAGE = "Failed to list files in directory: %s";
  private static final HttpStatus STATUS = HttpStatus.CONFLICT;

  public FailedToListFilesInDirectoryException(String filename, Throwable e) {
    super(ERROR_MESSAGE.formatted(filename), e, STATUS);
  }
}
