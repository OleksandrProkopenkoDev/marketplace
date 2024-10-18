package ua.tc.marketplace.util.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tc.marketplace.model.dto.comment.CommentDto;
import ua.tc.marketplace.model.dto.comment.CreateCommentDto;
import ua.tc.marketplace.model.dto.comment.UpdateCommentDto;

/**
 * This interface defines the OpenAPI annotations for the {@link ua.tc.marketplace.controller.CommentController} class.
 * It provides endpoints for managing comments.
 */
@Tag(name = "Comment API", description = "API for managing comments")
public interface CommentOpenApi {

  @Operation(
          summary = "Get all comments",
          description = "Retrieves a pageable list of all comments."
  )
  @GetMapping("/all")
  ResponseEntity<Page<CommentDto>> getAllComments(@PageableDefault Pageable pageable);

  @Operation(
          summary = "Get comment by ID",
          description = "Retrieves a comment by its unique identifier."
  )
  @GetMapping("/{id}")
  ResponseEntity<CommentDto> getCommentById(@PathVariable Long id);

  @Operation(
          summary = "Create a new comment",
          description = "Creates a new comment based on the provided data."
  )
  @PostMapping
  ResponseEntity<CommentDto> createComment(@RequestBody @Valid CreateCommentDto dto);

  @Operation(
          summary = "Updates an existing comment",
          description = "Updates an existing comment with the provided data."
  )
  @PutMapping("/{id}")
  ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody @Valid UpdateCommentDto commentDto);

  @Operation(
          summary = "Delete a comment",
          description = "Deletes a comment by its unique identifier."
  )
  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteComment(@PathVariable Long id);
}
