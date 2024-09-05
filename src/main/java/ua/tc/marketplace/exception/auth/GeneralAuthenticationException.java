package ua.tc.marketplace.exception.auth;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

public class GeneralAuthenticationException extends CustomRuntimeException {

  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public GeneralAuthenticationException(String message) {
    super(message, STATUS);
  }
}
