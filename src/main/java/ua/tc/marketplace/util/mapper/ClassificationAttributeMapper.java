package ua.tc.marketplace.util.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.Named;
import ua.tc.marketplace.config.MapperConfig;

import ua.tc.marketplace.model.dto.classificationAttribute.ClassificationAttributeDto;
import ua.tc.marketplace.model.entity.ClassificationAttribute;

@Mapper(config = MapperConfig.class)
public interface ClassificationAttributeMapper {

    ClassificationAttributeDto toDto(ClassificationAttribute entity);

    ClassificationAttribute toEntity(ClassificationAttributeDto dto);

    @Named("classificationAttributeToId")
    default Long classificationAttributeToId(ClassificationAttribute classificationAttribute) {
        return classificationAttribute != null ? classificationAttribute.getId() : null;
    }

    @Named("idToClassificationAttribute")
    default ClassificationAttribute idToClassificationAttribute(Long id) {
        if (id == null) {
            return null;
        }
        ClassificationAttribute classificationAttribute = new ClassificationAttribute();
        classificationAttribute.setId(id);
        return classificationAttribute;
    }
}