package ua.tc.marketplace.exception.photo;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

public class WrongFilePathException extends CustomRuntimeException {

  private static final String ERROR_MESSAGE = "Failed to find or create directory %s";
  private static final HttpStatus STATUS = HttpStatus.CONFLICT;

  public WrongFilePathException(String path, Throwable cause) {
    super(ERROR_MESSAGE.formatted(path), cause, STATUS);
  }
}
