package ua.tc.marketplace.util.mapper;

import org.springframework.stereotype.Component;
import ua.tc.marketplace.exception.model.MappingException;
import ua.tc.marketplace.model.dto.ClassificationAttributeDto;
import ua.tc.marketplace.model.entity.ClassificationAttribute;

@Component
public class ClassificationAttributeMapper {

    public ClassificationAttributeDto convertToDto(ClassificationAttribute entity) {
        try {
            ClassificationAttributeDto dto = new ClassificationAttributeDto();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setValueType(entity.getValueType());
            return dto;
        } catch (Exception e) {
            throw new MappingException("Error converting ClassificationAttribute to ClassificationAttributeDto", e);
        }
    }

    public ClassificationAttribute convertToEntity(ClassificationAttributeDto dto) {
        try {
            ClassificationAttribute entity = new ClassificationAttribute();
            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setValueType(dto.getValueType());
            return entity;
        } catch (Exception e) {
            throw new MappingException("Error converting ClassificationAttributeDto to ClassificationAttribute", e);
        }
    }
}
