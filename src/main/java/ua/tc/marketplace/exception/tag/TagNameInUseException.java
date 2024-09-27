package ua.tc.marketplace.exception.tag;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

/**
 * Exception thrown when a tag name in UpdateTagDto is already in use.
 *
 * <p>This exception is a subclass of {@link CustomRuntimeException} and is used to signal that a
 * tag name, specified in UpdateTagDto, is already in usy by another tag. It carries a predefined
 * error message and an HTTP status code of CONFLICT (409).
 *
 * <p>The error message includes the tag ID that was not found.
 */
public class TagNameInUseException extends CustomRuntimeException {

  private static final String ERROR_MESSAGE = "Tagname '%s' is in use and cannot be used for renaming.";
  private static final HttpStatus STATUS = HttpStatus.CONFLICT;

  public TagNameInUseException(String tagName) {
    super(ERROR_MESSAGE.formatted(tagName), STATUS);
  }
}
