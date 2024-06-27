package ua.tc.marketplace.exception.photo;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

public class PhotoFileNotFoundException extends CustomRuntimeException {

  private static final String ERROR_MESSAGE ="File of photo with name %s is not found.";
  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public PhotoFileNotFoundException(String filename) {
    super(ERROR_MESSAGE.formatted(filename), STATUS);
  }
}
