package ua.tc.marketplace.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.IterableMapping;
import ua.tc.marketplace.config.MapperConfig;
import ua.tc.marketplace.model.dto.category.CategoryDto;
import ua.tc.marketplace.model.dto.category.CreateCategoryDTO;
import ua.tc.marketplace.model.entity.Category;
import ua.tc.marketplace.model.entity.ClassificationAttribute;
import java.util.List;

@Mapper(config = MapperConfig.class, uses = ClassificationAttributeMapper.class)
public interface CategoryMapper {

    CategoryDto toDto(Category entity);

    @Mapping(source = "classificationAttributes", target = "classificationAttributes", qualifiedByName = "classificationAttributeToIdList")
    CreateCategoryDTO toCreateCategoryDto(Category entity);

    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "classificationAttributes", target = "classificationAttributes", qualifiedByName = "idToClassificationAttributeList")
    Category toEntity(CreateCategoryDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(@MappingTarget Category category, CategoryDto categoryDto);

    @Named("classificationAttributeToIdList")
    @IterableMapping(qualifiedByName = "classificationAttributeToId")
    List<Long> classificationAttributeToIdList(List<ClassificationAttribute> classificationAttributes);

    @Named("idToClassificationAttributeList")
    @IterableMapping(qualifiedByName = "idToClassificationAttribute")
    List<ClassificationAttribute> idToClassificationAttributeList(List<Long> ids);
}
