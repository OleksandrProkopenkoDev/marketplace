package ua.tc.marketplace.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.tc.marketplace.config.MapperConfig;
import ua.tc.marketplace.model.dto.user.CreateUserDto;
import ua.tc.marketplace.model.dto.user.UpdateUserDto;
import ua.tc.marketplace.model.dto.user.UserDto;
import ua.tc.marketplace.model.entity.Ad;
import ua.tc.marketplace.model.entity.User;
import ua.tc.marketplace.model.enums.UserRole;
import ua.tc.marketplace.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Mapper interface using MapStruct for converting between User entities and DTOs. Defines mappings
 * for converting User to UserDto, creating User from UserDto or CreateUserDto, and updating an
 * existing User entity with fields from UpdateUserDto.
 */
@Component
public class UserMapperResolver {
    @Autowired
    private UserService userService;

    @Named("mapIdToUser")
        public User mapIdToUser(Long userId) {
        if (userId == null) {
            return null;
        }
        return userService.findUserById(userId);
    }
}
