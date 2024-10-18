package ua.tc.marketplace.model.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object for updating an existing comment.
 * This DTO is used when updating the content of a comment.
 *
 * @param text the updated content of the comment
 */
public record UpdateCommentDto(
        @Schema(example = "This is a sample comment.") String text) {
}
