package ua.tc.marketplace.util.mapper;

import org.mapstruct.Mapper;
import ua.tc.marketplace.config.MapperConfig;
import ua.tc.marketplace.model.dto.category.ClassificationAttributeDto;
import ua.tc.marketplace.model.entity.ClassificationAttribute;

@Mapper(config = MapperConfig.class)
public interface ClassificationAttributeMapper {
    ClassificationAttributeDto toDto(ClassificationAttribute entity);
    ClassificationAttribute toEntity(ClassificationAttributeDto dto);
}
