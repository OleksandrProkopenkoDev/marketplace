package ua.tc.marketplace.exception.category;

import org.springframework.http.HttpStatus;
import ua.tc.marketplace.exception.model.CustomRuntimeException;

/**
 * Custom exception class for handling cases where a category with a specific ID is not found.
 * Extends CustomRuntimeException and sets an appropriate HTTP status code (404 - Not Found).
 */
public class CategoryNotFoundException extends CustomRuntimeException {

  private static final String ERROR_MESSAGE = "Category with id %s is not found.";
  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public CategoryNotFoundException(Long categoryId) {
    super(ERROR_MESSAGE.formatted(categoryId), STATUS);
  }
}
