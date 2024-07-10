package ua.tc.marketplace.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.tc.marketplace.config.MapperConfig;
import ua.tc.marketplace.model.dto.DemoRequest;
import ua.tc.marketplace.model.entity.Demo;

@Mapper(config = MapperConfig.class)
public interface DemoMapper {

    DemoRequest toDto (Demo entity);

    @Mapping(target = "id", ignore = true)
    Demo toEntity(DemoRequest dto);
}