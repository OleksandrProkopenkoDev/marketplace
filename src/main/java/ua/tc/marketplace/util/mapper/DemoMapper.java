package ua.tc.marketplace.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.tc.marketplace.config.MapperConfig;
import ua.tc.marketplace.model.dto.DemoRequest;
import ua.tc.marketplace.model.entity.Demo;

/**
 * Mapper interface for converting between {@link Demo} entity and {@link DemoRequest} DTO.
 *
 * <p>This interface uses MapStruct for automatic mapping configuration defined in {@link MapperConfig}.
 * It provides methods for converting {@link Demo} entities to {@link DemoRequest} DTOs
 * and vice versa.
 */

@Mapper(config = MapperConfig.class)
public interface DemoMapper {

    DemoRequest toDto (Demo entity);

    @Mapping(target = "id", ignore = true)
    Demo toEntity(DemoRequest dto);
}