package ua.tc.marketplace.exception.category;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

import java.util.Set;


/**
 * Custom exception class for handling cases where attribute with a specific ID is not found.
 * Extends CustomRuntimeException and sets an appropriate HTTP status code (404 - Not Found).
 */
public class AttributeNotFoundException extends CustomRuntimeException {

    private static final String ERROR_MESSAGE = "Attribute with id %s is not found.";
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public AttributeNotFoundException(Set<Long> categoryId) {
        super(ERROR_MESSAGE.formatted(categoryId), STATUS);
    }
}
