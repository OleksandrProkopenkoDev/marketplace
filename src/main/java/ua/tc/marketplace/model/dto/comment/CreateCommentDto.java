package ua.tc.marketplace.model.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object for creating a new comment.
 * This DTO is used when a new comment is being created.
 *
 * @param authorId the ID of the author who is creating the comment
 * @param shelterId the ID of the shelter the comment is associated with
 * @param text the content of the comment
 */
public record CreateCommentDto(
        Long authorId,
        Long shelterId,
        @Schema(example = "This is a sample comment.") String text) {
}
