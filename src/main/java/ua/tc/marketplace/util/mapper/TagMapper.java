package ua.tc.marketplace.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import ua.tc.marketplace.config.MapperConfig;
import ua.tc.marketplace.model.dto.CreateTagDto;
import ua.tc.marketplace.model.dto.TagDto;
import ua.tc.marketplace.model.dto.user.CreateUserDto;
import ua.tc.marketplace.model.dto.user.UpdateUserDto;
import ua.tc.marketplace.model.dto.user.UserDto;
import ua.tc.marketplace.model.entity.Ad;
import ua.tc.marketplace.model.entity.Tag;
import ua.tc.marketplace.model.entity.User;
import ua.tc.marketplace.model.enums.UserRole;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Mapper interface using MapStruct for converting between Tag entities and DTOs. Defines mappings
 * for converting Tag to TagDto.
 */
@Mapper(config = MapperConfig.class)
public interface TagMapper {

    TagDto toDto(Tag entity);

//    @Mapping(target = "id", ignore = true)
    Tag toEntity(CreateTagDto dto);

    void updateEntityFromDto(@MappingTarget Tag tag, TagDto tagDto);
}
