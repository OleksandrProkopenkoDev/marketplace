package ua.tc.marketplace.exception.attribute;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

import java.util.Set;

/**
 * Custom exception class for handling cases where provided attribute IDs are invalid.
 * Extends CustomRuntimeException and sets an appropriate HTTP status code (400 - Bad Request).
 */
public class InvalidAttributeIdsException extends CustomRuntimeException {

    private static final String ERROR_MESSAGE = "Invalid attribute IDs: %s.";
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public InvalidAttributeIdsException(Set<Long> invalidAttributeIds) {
        super(ERROR_MESSAGE.formatted(invalidAttributeIds), STATUS);
    }
}
