package ua.tc.marketplace.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.tc.marketplace.model.dto.comment.CommentDto;
import ua.tc.marketplace.model.dto.comment.CreateCommentDto;
import ua.tc.marketplace.model.dto.comment.UpdateCommentDto;

public interface CommentService {

    Page<CommentDto> findAll(Pageable pageable);

    CommentDto findCommentById(Long id);

    CommentDto createComment(CreateCommentDto createCommentDto);

    CommentDto updateComment(Long id, UpdateCommentDto updateCommentDto);

    void deleteCommentById(Long id);
}
