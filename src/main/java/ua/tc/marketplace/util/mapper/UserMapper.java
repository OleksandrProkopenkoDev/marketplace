package ua.tc.marketplace.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.tc.marketplace.config.MapperConfig;
import ua.tc.marketplace.model.dto.DemoRequest;
import ua.tc.marketplace.model.dto.UserDto;
import ua.tc.marketplace.model.entity.Demo;
import ua.tc.marketplace.model.entity.User;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

  UserDto toDto (User entity);

  @Mapping(target = "id", ignore = true)
  User toEntity(UserDto dto);
}
