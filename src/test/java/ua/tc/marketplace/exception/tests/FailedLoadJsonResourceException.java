package ua.tc.marketplace.exception.tests;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

public class FailedLoadJsonResourceException extends CustomRuntimeException {

  private static final String ERROR_MESSAGE = "Failed to load json Resource with filename: %s ";
  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public FailedLoadJsonResourceException(String filename) {
    super(ERROR_MESSAGE.formatted(filename), STATUS);
  }
}
