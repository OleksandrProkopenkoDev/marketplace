package ua.tc.marketplace.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.tc.marketplace.exception.comment.CommentCreationErrorWrongShelterRole;
import ua.tc.marketplace.exception.comment.CommentNotFoundException;
import ua.tc.marketplace.model.dto.comment.CommentDto;
import ua.tc.marketplace.model.dto.comment.CreateCommentDto;
import ua.tc.marketplace.model.dto.comment.UpdateCommentDto;
import ua.tc.marketplace.model.entity.Comment;
import ua.tc.marketplace.model.entity.User;
import ua.tc.marketplace.model.enums.UserRole;
import ua.tc.marketplace.repository.CommentRepository;
import ua.tc.marketplace.service.UserService;
import ua.tc.marketplace.util.mapper.CommentMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Long commentId = 1L;
    private Long authorId = 3L;
    private Long shelterId = 2L;
    private String commentText = "Test text";
    private String updatedCommentText = "Test text after update";
    private Comment comment;
    private CommentDto commentDto;
    private CreateCommentDto createCommentDto;
    private UpdateCommentDto updateCommentDto;
    private User author;
    private User shelter;
    private User nonShelterUser;
    private Comment updatedComment;
    private CommentDto updatedCommentDto;

    @BeforeEach
    void setUp() {

        // Initialize the common objects
        comment = new Comment(commentId, author, shelter, commentText, LocalDateTime.now());
        commentDto = new CommentDto(commentId, authorId, shelterId, commentText, LocalDateTime.now());
        createCommentDto = new CreateCommentDto(authorId, shelterId, commentText);
        updateCommentDto = new UpdateCommentDto(updatedCommentText);
        author = new User();
        shelter = new User();
        shelter.setUserRole(UserRole.SHELTER);
        nonShelterUser = new User();
        nonShelterUser.setUserRole(UserRole.INDIVIDUAL);
        updatedComment = new Comment(commentId, author, shelter, commentText, LocalDateTime.now());
        updatedCommentDto = new CommentDto(commentId, authorId, shelterId, updatedCommentText, LocalDateTime.now());
    }

    @Test
    void findCommentById_shouldReturnCommentDto_whenExists() {
        // Mock repository to return a comment when findById is called
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // Mock mapper to convert entity to DTO
        when(commentMapper.toDto(comment)).thenReturn(commentDto);

        // Act
        CommentDto result = commentService.findCommentById(commentId);

        // Assert
        assertEquals(commentDto, result);

        // Verify repository call
        verify(commentRepository, times(1)).findById(commentId);
    }

    @Test
    void findCommentById_shouldThrow_whenNotExists() {
        // Mock repository to return an empty Optional when comment is not found
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // Assert that the exception is thrown
        assertThrows(CommentNotFoundException.class, () -> commentService.findCommentById(commentId));

        // Verify repository call
        verify(commentRepository, times(1)).findById(commentId);
    }

    @Test
    void createComment_shouldCreate_whenValidInput() {
        // Mock user service to return author and shelter
        when(userService.findUserById(authorId)).thenReturn(author);
        when(userService.findUserById(shelterId)).thenReturn(shelter);

        // Mock mapper to convert DTO to entity and entity to DTO
        when(commentMapper.toEntity(createCommentDto)).thenReturn(comment);
        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentMapper.toDto(comment)).thenReturn(commentDto);

        // Act
        CommentDto result = commentService.createComment(createCommentDto);

        // Assert
        assertEquals(commentDto, result);

        // Verify calls
        verify(userService, times(1)).findUserById(authorId);
        verify(userService, times(1)).findUserById(shelterId);
        verify(commentMapper, times(1)).toEntity(createCommentDto);
        verify(commentRepository, times(1)).save(comment);
        verify(commentMapper, times(1)).toDto(comment);
    }

    @Test
    void createComment_shouldReturnException_whenShelterIdIsNotShelter() {
        // Mock user service to return author and non-shelter user for shelterId
        when(userService.findUserById(authorId)).thenReturn(author);
        when(userService.findUserById(shelterId)).thenReturn(nonShelterUser);

        // Assert exception thrown
        assertThrows(CommentCreationErrorWrongShelterRole.class, () -> commentService.createComment(createCommentDto));

        // Verify calls
        verify(userService, times(1)).findUserById(authorId);
        verify(userService, times(1)).findUserById(shelterId);
        verify(commentMapper, times(0)).toEntity(createCommentDto);
        verify(commentRepository, times(0)).save(comment);
        verify(commentMapper, times(0)).toDto(comment);
    }

    @Test
    void updateComment_shouldUpdate_whenValidInput() {
        // Mock repository to return existing comment
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // Mock mapper to update the entity
        doNothing().when(commentMapper).updateEntityFromDto(comment, updateCommentDto);

        // Mock repository to save updated comment
        when(commentRepository.save(comment)).thenReturn(updatedComment);

        // Mock mapper to convert entity to DTO
        when(commentMapper.toDto(updatedComment)).thenReturn(updatedCommentDto);

        // Act
        CommentDto result = commentService.updateComment(commentId, updateCommentDto);

        // Assert
        assertEquals(updatedCommentDto, result);

        // Verify calls
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentMapper, times(1)).updateEntityFromDto(comment, updateCommentDto);
        verify(commentRepository, times(1)).save(comment);
        verify(commentMapper, times(1)).toDto(updatedComment);
    }

    @Test
    void updateComment_shouldThrow_whenCommentIdNotExists() {
        // Mock repository to return an empty Optional
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // Assert exception thrown
        assertThrows(CommentNotFoundException.class, () -> commentService.updateComment(commentId, updateCommentDto));

        // Verify call
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentMapper, times(0)).updateEntityFromDto(comment, updateCommentDto);
        verify(commentRepository, times(0)).save(comment);
        verify(commentMapper, times(0)).toDto(updatedComment);
    }

    @Test
    void deleteComment_shouldDelete() {
        // Mock repository to return existing comment
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // Act
        commentService.deleteCommentById(commentId);

        // Verify calls
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    public void deleteComment_shouldThrowException_whenCommentNotExists() {
        // Mock repository to return an empty Optional
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // Assert exception thrown
        assertThrows(CommentNotFoundException.class, () -> commentService.deleteCommentById(commentId));

        // Verify call
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentRepository, never()).deleteById(commentId);
    }
}
