package ua.tc.marketplace.exception.attribute;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

/** Exception thrown when a specified attribute is not found. */
public class AttributeNotFoundException extends CustomRuntimeException {

  private static final String ERROR_MESSAGE = "Attribute with id %s is not found";
  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public AttributeNotFoundException(Long attributeId) {
    super(ERROR_MESSAGE.formatted(attributeId), STATUS);
  }
}
