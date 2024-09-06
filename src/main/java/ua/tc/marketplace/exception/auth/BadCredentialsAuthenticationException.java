package ua.tc.marketplace.exception.auth;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

public class BadCredentialsAuthenticationException extends CustomRuntimeException {

  private static final String MESSAGE ="Invalid username or password";
  private static final HttpStatus STATUS = HttpStatus.UNAUTHORIZED;

  public BadCredentialsAuthenticationException() {
    super(MESSAGE, STATUS);
  }
}
