package ua.tc.marketplace.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.IterableMapping;
import ua.tc.marketplace.config.MapperConfig;
import ua.tc.marketplace.model.dto.category.CategoryDTO;
import ua.tc.marketplace.model.dto.category.CreateCategoryDTO;
import ua.tc.marketplace.model.dto.category.UpdateCategoryDTO;
import ua.tc.marketplace.model.dto.classificationAttribute.ClassificationAttributeDto;
import ua.tc.marketplace.model.entity.Category;
import ua.tc.marketplace.model.entity.ClassificationAttribute;
import java.util.List;

@Mapper(config = MapperConfig.class, uses = ClassificationAttributeMapper.class)
public interface CategoryMapper {

    @Mapping(source = "classificationAttributes", target = "classificationAttribute")
    CategoryDTO toCategoryDto(Category entity);

    @Mapping(source = "classificationAttribute", target = "classificationAttributes")
    Category toEntity(CategoryDTO dto);

    @Mapping(source = "attributeIds", target = "classificationAttributes", qualifiedByName = "idToClassificationAttributeList")
    Category toEntity(CreateCategoryDTO dto);

    @Mapping(source = "classificationAttributes", target = "attributeIds", qualifiedByName = "classificationAttributeToIdList")
    CreateCategoryDTO toCreateCategoryDto(Category entity);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "attributeIds", target = "classificationAttributes", qualifiedByName = "idToClassificationAttributeList")
    void updateEntityFromDto(@MappingTarget Category category, UpdateCategoryDTO dto);

    @Named("classificationAttributeToIdList")
    @IterableMapping(qualifiedByName = "classificationAttributeToId")
    List<Long> classificationAttributeToIdList(List<ClassificationAttribute> classificationAttributes);

    @Named("idToClassificationAttributeList")
    @IterableMapping(qualifiedByName = "idToClassificationAttribute")
    List<ClassificationAttribute> idToClassificationAttributeList(List<Long> ids);


}