package ua.tc.marketplace.exception.ad;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

public class AdNotFoundException extends CustomRuntimeException {

  private static final String ERROR_MESSAGE = "Ad with id %s is not found";
  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public AdNotFoundException(Long adId) {
    super(ERROR_MESSAGE.formatted(adId), STATUS);
  }
}
