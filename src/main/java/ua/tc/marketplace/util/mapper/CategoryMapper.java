package ua.tc.marketplace.util.mapper;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ua.tc.marketplace.exception.model.MappingException;
import ua.tc.marketplace.model.entity.Category;
import ua.tc.marketplace.model.dto.CategoryDto;
import ua.tc.marketplace.model.entity.ClassificationAttribute;
import ua.tc.marketplace.model.dto.ClassificationAttributeDto;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CategoryMapper {

    @Autowired
    private final ClassificationAttributeMapper classificationAttributeMapper;

    public CategoryDto convertToDto(Category category) {
        try {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());
            if (category.getClassificationAttributes() != null) {
                categoryDto.setClassificationAttributes(
                        category.getClassificationAttributes().stream()
                                .map(this::mapToClassificationAttributeDto)
                                .collect(Collectors.toList())
                );
            }
            return categoryDto;
        } catch (Exception e) {
            throw new MappingException("Error converting Category to CategoryDto", e);
        }
    }

    private ClassificationAttributeDto mapToClassificationAttributeDto(ClassificationAttribute classificationAttribute) {
        try {
            ClassificationAttributeDto dto = new ClassificationAttributeDto();
            dto.setId(classificationAttribute.getId());
            dto.setName(classificationAttribute.getName());
            dto.setValueType(classificationAttribute.getValueType());
            return dto;
        } catch (Exception e) {
            throw new MappingException("Error converting ClassificationAttribute to ClassificationAttributeDto", e);
        }
    }

    public Category toEntity(CategoryDto categoryDto) {
        try {
            Category category = new Category();
            category.setId(categoryDto.getId());
            category.setName(categoryDto.getName());
            if (categoryDto.getClassificationAttributes() != null) {
                category.setClassificationAttributes(
                        categoryDto.getClassificationAttributes().stream()
                                .map(classificationAttributeMapper::convertToEntity)
                                .collect(Collectors.toList())
                );
            }
            return category;
        }catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid value for ValueType: " + categoryDto.getClassificationAttributes());
        }
        catch (Exception e) {
            throw new MappingException("Error converting CategoryDto to Category", e);
        }
    }
}
