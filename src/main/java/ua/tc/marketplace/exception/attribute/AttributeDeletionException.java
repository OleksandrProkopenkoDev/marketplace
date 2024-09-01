package ua.tc.marketplace.exception.attribute;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

public class AttributeDeletionException extends CustomRuntimeException {

    private static final String ERROR_MESSAGE = "Cannot delete an attribute that is associated with an existing category";
    private static final HttpStatus STATUS = HttpStatus.CONFLICT;

    public AttributeDeletionException() {
        super(ERROR_MESSAGE, STATUS);
    }
}
