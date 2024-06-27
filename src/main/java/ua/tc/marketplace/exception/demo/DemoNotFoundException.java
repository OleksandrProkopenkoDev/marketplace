package ua.tc.marketplace.exception.demo;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;
/**
 * Exception which should be thrown when Demo entity not found in Database.
 *
 */
public class DemoNotFoundException extends CustomRuntimeException {
  private static final String ERROR_MESSAGE ="Demo entity with id %s is not found.";
  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public DemoNotFoundException(Integer id) {
    super(ERROR_MESSAGE.formatted(id), STATUS);
  }
}
