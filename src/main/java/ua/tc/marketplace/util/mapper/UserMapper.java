package ua.tc.marketplace.util.mapper;

import java.util.Locale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import ua.tc.marketplace.config.MapperConfig;
import ua.tc.marketplace.model.dto.DemoRequest;
import ua.tc.marketplace.model.dto.UserDto;
import ua.tc.marketplace.model.entity.Demo;
import ua.tc.marketplace.model.entity.User;
import ua.tc.marketplace.model.enums.UserRole;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

  UserDto toDto(User entity);

  @Mapping(target = "id", ignore = true)
  User toEntity(UserDto dto);

  @Mapping(
      target = "userRole",
      source = "userRole",
      qualifiedByName = "mapUserRoleFromStringToEnum")
  @Mapping(target = "password", ignore = true)
  void updateEntityFromDto(@MappingTarget User user, UserDto userDto);

  @Named("mapUserRoleFromStringToEnum")
  default UserRole mapUserRoleFromStringToEnum(String userRole) {
    return UserRole.valueOf(userRole.toUpperCase(Locale.ROOT));
  }
}
