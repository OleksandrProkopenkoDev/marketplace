package ua.tc.marketplace.exception.category;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

public class CategoryNotFoundException extends CustomRuntimeException {
    private static final String ERROR_MESSAGE ="Category with id %s is not found.";
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public CategoryNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id), STATUS);
    }
}
