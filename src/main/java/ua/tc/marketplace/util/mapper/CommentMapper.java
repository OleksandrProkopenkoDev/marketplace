package ua.tc.marketplace.util.mapper;

import org.mapstruct.*;
import ua.tc.marketplace.config.MapperConfig;
import ua.tc.marketplace.exception.attribute.InvalidAttributeIdsException;
import ua.tc.marketplace.model.dto.comment.CommentDto;
import ua.tc.marketplace.model.dto.comment.CreateCommentDto;
import ua.tc.marketplace.model.dto.comment.UpdateCommentDto;
import ua.tc.marketplace.model.entity.Ad;
import ua.tc.marketplace.model.entity.Comment;
import ua.tc.marketplace.model.entity.User;
import ua.tc.marketplace.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper interface using MapStruct for converting between Comment entities and DTOs. Defines mappings
 * for converting Comment to CommentDto.
 */
@Mapper(config = MapperConfig.class, uses = UserMapperResolver.class)
public interface CommentMapper {

    /**
     * Converts a Comment entity to a CommentDto.
     *
     * @param entity The Comment entity.
     * @return The corresponding CommentDto.
     */
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "shelterId", source = "shelter.id")
//    @Mapping(target = "favoriteAdIds", source = "favorites", qualifiedByName = "mapAdsToIds")
    CommentDto toDto(Comment entity);

    /**
     * Converts a CreateCommentDto to a Comment entity.
     *
     * @param dto The CreateCommentDto.
     * @return The corresponding Comment entity.
     */
    @Mapping(target = "shelter", source = "shelterId", qualifiedByName = "mapIdToUser")
    @Mapping(target = "author", source = "authorId", qualifiedByName = "mapIdToUser")
    Comment toEntity(CreateCommentDto dto);

    /**
     * Updates an existing Comment entity from an UpdateCommentDto.
     *
     * @param comment The existing Comment entity to be updated.
     * @param commentDto The UpdateCommentDto containing updated values.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "shelter", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(@MappingTarget Comment comment, UpdateCommentDto commentDto);

//    @Named("mapIdToUser")
//    default User mapIdToUser(Long id, @Context UserService userService) {
//        if (id == null) {
//            return null;
//        }
//        return userService.findUserById(id);
//    }
}
