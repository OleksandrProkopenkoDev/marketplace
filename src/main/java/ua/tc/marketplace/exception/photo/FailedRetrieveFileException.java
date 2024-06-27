package ua.tc.marketplace.exception.photo;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

public class FailedRetrieveFileException extends CustomRuntimeException {
  private static final String ERROR_MESSAGE = "Failed to retrieve file of photo with name %s";
  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public FailedRetrieveFileException(String filename, Throwable e) {
    super(ERROR_MESSAGE.formatted(filename), e, STATUS);
  }
}
