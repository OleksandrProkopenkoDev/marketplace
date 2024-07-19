package ua.tc.marketplace.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import ua.tc.marketplace.config.MapperConfig;
import ua.tc.marketplace.model.dto.CategoryDto;
import ua.tc.marketplace.model.dto.ClassificationAttributeDto;
import ua.tc.marketplace.model.entity.Category;
import ua.tc.marketplace.model.entity.ClassificationAttribute;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {

    CategoryDto toDto(Category entity);

    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(@MappingTarget Category category, CategoryDto categoryDto);

    default ClassificationAttributeDto toDto(ClassificationAttribute entity) {
        if (entity == null) {
            return null;
        }

        return new ClassificationAttributeDto(
                entity.getId(),
                entity.getName(),
                entity.getValueType()
        );
    }

    default ClassificationAttribute toEntity(ClassificationAttributeDto dto) {
        if (dto == null) {
            return null;
        }

        ClassificationAttribute classificationAttribute = new ClassificationAttribute();
        classificationAttribute.setId(dto.getId());
        classificationAttribute.setName(dto.getName());
        classificationAttribute.setValueType(dto.getValueType());

        return classificationAttribute;
    }
}
