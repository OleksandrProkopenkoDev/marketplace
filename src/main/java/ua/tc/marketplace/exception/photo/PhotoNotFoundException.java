package ua.tc.marketplace.exception.photo;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

public class PhotoNotFoundException extends CustomRuntimeException {

  private static final String ERROR_MESSAGE = "Photo with id %s is not found.";
  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public PhotoNotFoundException(Long photoId) {
    super(ERROR_MESSAGE.formatted(photoId), STATUS);
  }

  public PhotoNotFoundException(Long photoId, Throwable cause) {
    super(ERROR_MESSAGE.formatted(photoId), cause, STATUS);
  }
}
