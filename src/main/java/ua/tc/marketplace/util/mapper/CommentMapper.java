package ua.tc.marketplace.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ua.tc.marketplace.config.MapperConfig;
import ua.tc.marketplace.model.dto.comment.CommentDto;
import ua.tc.marketplace.model.dto.comment.CreateCommentDto;
import ua.tc.marketplace.model.dto.comment.UpdateCommentDto;
import ua.tc.marketplace.model.entity.Comment;

/**
 * Mapper interface using MapStruct for converting between Comment entities and DTOs. Defines mappings
 * for converting Comment to CommentDto.
 */
@Mapper(config = MapperConfig.class)
public interface CommentMapper {

    /**
     * Converts a Comment entity to a CommentDto.
     *
     * @param entity The Comment entity.
     * @return The corresponding CommentDto.
     */
    CommentDto toDto(Comment entity);

    /**
     * Converts a CreateCommentDto to a Comment entity.
     *
     * @param dto The CreateCommentDto.
     * @return The corresponding Comment entity.
     */
    Comment toEntity(CreateCommentDto dto);

    /**
     * Updates an existing Comment entity from an UpdateCommentDto.
     *
     * @param comment The existing Comment entity to be updated.
     * @param commentDto The UpdateCommentDto containing updated values.
     */
    void updateEntityFromDto(@MappingTarget Comment comment, UpdateCommentDto commentDto);
}
