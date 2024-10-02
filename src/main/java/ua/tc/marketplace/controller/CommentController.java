package ua.tc.marketplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tc.marketplace.model.dto.comment.CommentDto;
import ua.tc.marketplace.model.dto.comment.CreateCommentDto;
import ua.tc.marketplace.model.dto.comment.UpdateCommentDto;
import ua.tc.marketplace.service.CommentService;
import ua.tc.marketplace.util.openapi.CommentOpenApi;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController implements CommentOpenApi {

  private final CommentService commentService;

  /**
   * Retrieves all comments with pagination.
   *
   * @param pageable The pageable object containing page number, size, and sorting.
   * @return A ResponseEntity containing a paginated list of CommentDto.
   */
  @Override
  @GetMapping("/all")
  public ResponseEntity<Page<CommentDto>> getAllComments(@PageableDefault Pageable pageable) {
    return ResponseEntity.status(HttpStatus.OK).body(commentService.findAll(pageable));
  }

  /**
   * Retrieves a comment by its ID.
   *
   * @param id The ID of the comment to retrieve.
   * @return A ResponseEntity containing the CommentDto.
   */
  @Override
  @GetMapping("/{id}")
  public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK).body(commentService.findCommentById(id));
  }

  /**
   * Creates a new comment.
   *
   * @param dto The CreateCommentDto containing the new comment data.
   * @return A ResponseEntity containing the created CommentDto.
   */
  @Override
  @PostMapping
  public ResponseEntity<CommentDto> createComment(@RequestBody @Valid CreateCommentDto dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(dto));
  }

  /**
   * Updates an existing comment.
   *
   * @param id The ID of the comment to update.
   * @param commentDto The UpdateCommentDto containing updated comment information.
   * @return A ResponseEntity containing the updated CommentDto.
   */
  @Override
  @PutMapping("/{id}")
  public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody @Valid UpdateCommentDto commentDto) {
    return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(id, commentDto));
  }

  /**
   * Deletes a comment by its ID.
   *
   * @param id The ID of the comment to delete.
   * @return A ResponseEntity with no content status.
   */
  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
    commentService.deleteCommentById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
