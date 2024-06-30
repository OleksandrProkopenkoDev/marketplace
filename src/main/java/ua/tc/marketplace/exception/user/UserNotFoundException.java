package ua.tc.marketplace.exception.user;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

/**
 * Exception which should be thrown when User entity not found in Database.
 *
 */
public class UserNotFoundException extends CustomRuntimeException {
  private static final String ERROR_MESSAGE ="User entity with id %s is not found.";
  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public UserNotFoundException(Long id) {
    super(ERROR_MESSAGE.formatted(id), STATUS);
  }
}
