package ua.tc.marketplace.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import ua.tc.marketplace.model.entity.ContactInfo;
import ua.tc.marketplace.model.entity.Photo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing a tag.
 * Used for transferring tag data between layers of the application.
 * This DTO includes all tag information.
 *
 * <p>Validation constraints are applied to ensure data integrity and consistency.
 */
public record TagDto(
    Long id,
    @Schema(example = "cat") @NotBlank String name)
{
}
