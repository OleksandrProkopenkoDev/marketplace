package ua.tc.marketplace.exception.photo;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

public class FailedStoreFileException extends CustomRuntimeException {

  private static final String ERROR_MESSAGE = "Failed to store file %s";
  private static final HttpStatus STATUS = HttpStatus.CONFLICT;

  public FailedStoreFileException(String filename, Throwable cause) {
    super(ERROR_MESSAGE.formatted(filename), cause, STATUS);
  }
}
