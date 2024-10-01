package ua.tc.marketplace.exception.comment;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

/**
 * Exception thrown when a userId have no ShelterRole in Comment's is not found in the system.
 *
 * <p>This exception is a subclass of {@link CustomRuntimeException} and is used to signal that a
 * User, provided in CommentCreateDto in 'Shelter' field, does not have appropriate Shelter role.
 * It carries a predefined error message and an HTTP status code of NOT_FOUND (404).
 *
 * <p>The error message includes the user ID of the user without Shelter role.
 */
public class CommentCreationErrorWrongShelterRole extends CustomRuntimeException {

  private static final String ERROR_MESSAGE = "Comment cannot be created, userId %s has no Shelter role";
  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public CommentCreationErrorWrongShelterRole(Long commentId) {
    super(ERROR_MESSAGE.formatted(commentId), STATUS);
  }
}
