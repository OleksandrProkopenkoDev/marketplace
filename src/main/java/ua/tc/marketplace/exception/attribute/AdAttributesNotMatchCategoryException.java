package ua.tc.marketplace.exception.attribute;

import java.util.Set;
import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

/**
 * Exception thrown when the actual attribute IDs do not match the required category attribute IDs.
 */
public class AdAttributesNotMatchCategoryException extends CustomRuntimeException {

  private static final String ERROR_MESSAGE =
      "Attribute's ids list %s not matches Category's attributes ids %s";
  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public AdAttributesNotMatchCategoryException(
      Set<Long> actualIds, Set<Long> requiredAttributeIds) {
    super(ERROR_MESSAGE.formatted(actualIds, requiredAttributeIds), STATUS);
  }
}
