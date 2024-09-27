package ua.tc.marketplace.exception.tag;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

/**
 * Exception thrown when a tag to be deleted is in use.
 *
 * <p>This exception is a subclass of {@link CustomRuntimeException} and is used to signal that a
 * tag, that is supposed to be deleted, is in use in one or more article. It carries a predefined
 * error message and an HTTP status code of CONFLICT (409).
 *
 * <p>The error message includes the tag ID that was not found.
 */
public class TagInUseException extends CustomRuntimeException {

  private static final String ERROR_MESSAGE = "Tag with id %s is in use and cannot be deleted.";
  private static final HttpStatus STATUS = HttpStatus.CONFLICT;

  public TagInUseException(Long tagId) {
    super(ERROR_MESSAGE.formatted(tagId), STATUS);
  }
}
