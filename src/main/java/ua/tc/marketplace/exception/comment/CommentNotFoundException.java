package ua.tc.marketplace.exception.comment;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

/**
 * Exception thrown when a Comment is not found in the system.
 *
 * <p>This exception is a subclass of {@link CustomRuntimeException} and is used to signal that a
 * Comment with a specific ID does not exist. It carries a predefined error message and an HTTP status
 * code of NOT_FOUND (404).
 *
 * <p>The error message includes the tag ID that was not found.
 */
public class CommentNotFoundException extends CustomRuntimeException {

  private static final String ERROR_MESSAGE = "Comment with id %s is not found.";
  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public CommentNotFoundException(Long commentId) {
    super(ERROR_MESSAGE.formatted(commentId), STATUS);
  }
}
