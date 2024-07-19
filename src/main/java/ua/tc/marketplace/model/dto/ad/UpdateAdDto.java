package ua.tc.marketplace.model.dto.ad;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for updating an advertisement.
 *
 * <p>This class defines the structure of the data required to update an advertisement. It includes
 * properties such as author ID, title, description, price, and category ID.
 *
 * <p>Validation constraints are applied to ensure data integrity and consistency.
 */
public record UpdateAdDto(
    @NotNull(message = "Author ID cannot be null")
    Long authorId,

    @NotEmpty(message = "Title cannot be empty")
    @Size(max = 100, message = "Title cannot be longer than 100 characters")
    String title,

    @NotEmpty(message = "Description cannot be empty")
    String description,

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    BigDecimal price,

    @NotNull(message = "Category ID cannot be null")
    Long categoryId
) {}
