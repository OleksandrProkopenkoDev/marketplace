package ua.tc.marketplace.exception.attribute;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

/** Exception thrown when the JSON for ad attributes fails to parse. */
public class FailedToParseAdAttributesJsonException extends CustomRuntimeException {

  private static final String ERROR_MESSAGE = "Failed to parse ad attributes json %s";
  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public FailedToParseAdAttributesJsonException(String adAttributesJson) {
    super(ERROR_MESSAGE.formatted(adAttributesJson), STATUS);
  }
}
