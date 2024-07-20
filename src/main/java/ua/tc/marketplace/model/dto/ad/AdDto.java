package ua.tc.marketplace.model.dto.ad;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import ua.tc.marketplace.model.entity.AdAttribute;
import ua.tc.marketplace.model.entity.Photo;

/**
 * Data Transfer Object representing an advertisement.
 *
 * <p>This class defines the structure of an advertisement to be transferred between different
 * layers of the application. It includes properties such as ID, author ID, title, description,
 * price, photos, thumbnail, category ID, creation timestamp, and update timestamp.
 *
 * <p>Validation constraints are applied to ensure data integrity and consistency.
 */
public record AdDto(
    Long id,

    @NotNull(message = "Author ID cannot be null")
    Long authorId,

    @NotEmpty(message = "Title cannot be empty")
    @Size(max = 100, message = "Title cannot be longer than 100 characters")
    String title,

    @NotEmpty(message = "Description cannot be empty")
    String description,

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", message = "Price must be zero or positive")
    BigDecimal price,

    List<Photo> photos,

    Photo thumbnail,

    @NotNull(message = "Category ID cannot be null")
    Long categoryId,

    List<AdAttributeDto> adAttributes,

    LocalDateTime createdAt,

    LocalDateTime updatedAt
) {}
