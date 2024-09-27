package ua.tc.marketplace.model.dto.tag;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object (DTO) representing inf for creating a tag.
 * Used for transferring tag data between layers of the application.
 * This DTO includes all tag information.
 *
 * <p>Validation constraints are applied to ensure data integrity and consistency.
 */
public record CreateTagDto(
        @Schema(example = "cat") String name) {
}
