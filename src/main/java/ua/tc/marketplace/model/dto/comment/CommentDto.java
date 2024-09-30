package ua.tc.marketplace.model.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for the Comment entity.
 * This DTO is used to transfer the full details of a comment.
 *
 * @param id the unique identifier of the comment
 * @param authorId the ID of the author who made the comment
 * @param shelterId the ID of the shelter the comment is associated with
 * @param text the content of the comment
 * @param createdAt the timestamp when the comment was created
 */
public record CommentDto(
        Long id,
        Long authorId,
        Long shelterId,
        @Schema(example = "This is a sample comment.") String text,
        LocalDateTime createdAt
)
{
}
