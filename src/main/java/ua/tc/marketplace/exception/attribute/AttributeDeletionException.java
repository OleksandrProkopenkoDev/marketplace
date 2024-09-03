package ua.tc.marketplace.exception.attribute;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

/**
 * Exception thrown when an attempt is made to delete an attribute that is associated with an
 * existing category.
 *
 * <p>This exception is used to prevent the deletion of an attribute that is still linked to a
 * category, indicating a conflict situation. The attribute ID is included in the error message.
 */
public class AttributeDeletionException extends CustomRuntimeException {

  private static final String ERROR_MESSAGE =
      "Cannot delete an attribute %s that is associated with an existing category";
  private static final HttpStatus STATUS = HttpStatus.CONFLICT;

  public AttributeDeletionException(Long attributeId) {
    super(ERROR_MESSAGE.formatted(attributeId), STATUS);
  }
}
