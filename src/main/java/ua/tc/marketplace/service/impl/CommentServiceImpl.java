package ua.tc.marketplace.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tc.marketplace.exception.comment.CommentCreationErrorWrongShelterRole;
import ua.tc.marketplace.exception.comment.CommentNotFoundException;
import ua.tc.marketplace.model.dto.comment.CommentDto;
import ua.tc.marketplace.model.dto.comment.CreateCommentDto;
import ua.tc.marketplace.model.dto.comment.UpdateCommentDto;
import ua.tc.marketplace.model.entity.Comment;
import ua.tc.marketplace.model.entity.User;
import ua.tc.marketplace.model.enums.UserRole;
import ua.tc.marketplace.repository.CommentRepository;
import ua.tc.marketplace.service.CommentService;
import ua.tc.marketplace.service.UserService;
import ua.tc.marketplace.util.mapper.CommentMapper;

/**
 * Implementation of the {@link CommentService} interface. Provides methods for creating, retrieving,
 * updating, and deleting comments.
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    /**
     * Retrieves a paginated list of all comments.
     *
     * @param pageable Pagination information (page number, size, sorting).
     * @return A page of CommentDto objects.
     */
    @Override
    public Page<CommentDto> findAll(Pageable pageable) {
        Page<Comment> comments = commentRepository.findAll(pageable);
        return comments.map(commentMapper::toDto);
    }

    /**
     * Retrieves a comment by its ID.
     *
     * @param id The ID of the comment to retrieve.
     * @return The CommentDto representing the comment.
     * @throws CommentNotFoundException If the comment is not found.
     */
    @Override
    public CommentDto findCommentById(Long id) {
        Comment comment = getComment(id);
        return commentMapper.toDto(comment);
    }

    /**
     * Updates an existing comment.
     *
     * @param id              The ID of the comment to update.
     * @param updateCommentDto The DTO containing updated comment information.
     * @return The updated CommentDto.
     * @throws CommentNotFoundException If the comment to update is not found.
     */
    @Transactional
    @Override
    public CommentDto updateComment(Long id, @NonNull UpdateCommentDto updateCommentDto) {
        Comment existingComment = getComment(id);
        commentMapper.updateEntityFromDto(existingComment, updateCommentDto);
        return commentMapper.toDto(commentRepository.save(existingComment));
    }

    /**
     * Deletes a comment by its ID.
     *
     * @param id The ID of the comment to delete.
     * @throws CommentNotFoundException If the comment to delete is not found.
     */
    @Transactional
    @Override
    public void deleteCommentById(Long id) {
        log.info("Deleting comment with id={}", id);
        Comment existingComment = getComment(id);
        commentRepository.deleteById(existingComment.getId());
    }

    /**
     * Creates a new comment.
     *
     * @param createCommentDto The DTO containing comment information for creation.
     * @return The created CommentDto.
     */
    @Transactional
    @Override
    public CommentDto createComment(CreateCommentDto createCommentDto) {
        User author = userService.findUserById(createCommentDto.authorId());
        User shelter = userService.findUserById(createCommentDto.shelterId());
        if (shelter.getUserRole()!= UserRole.SHELTER) throw new CommentCreationErrorWrongShelterRole(createCommentDto.shelterId());
        Comment comment = commentMapper.toEntity(createCommentDto);
        return commentMapper.toDto(commentRepository.save(comment));
    }

    /**
     * Retrieves a comment by its ID, throwing a CommentNotFoundException if not found.
     *
     * @param id The ID of the comment to retrieve.
     * @return The found Comment entity.
     * @throws CommentNotFoundException If the comment is not found.
     */
    private Comment getComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));
    }
}
