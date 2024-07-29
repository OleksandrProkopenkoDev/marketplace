package ua.tc.marketplace.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.IterableMapping;
import ua.tc.marketplace.config.MapperConfig;
import ua.tc.marketplace.model.dto.category.CategoryDTO;
import ua.tc.marketplace.model.entity.Category;
import ua.tc.marketplace.model.entity.ClassificationAttribute;
import java.util.List;

/**
 * Mapper interface using MapStruct for converting between Category entities and DTOs. Defines mappings
 * for converting Category to CreateCategoryDTO, extracting primitive fields from CreateCategoryDTO to
 * create a Category entity, and updating an existing Category entity with fields from CreateCategoryDTO.
 *
 * <p>This interface provides:
 * <ul>
 *   <li>Mapping from Category to CreateCategoryDTO, including conversion of classification attributes to a list of IDs</li>
 *   <li>Mapping from CreateCategoryDTO to Category, including conversion of a list of IDs to classification attributes</li>
 *   <li>Mapping for updating an existing Category entity from a CreateCategoryDTO, preserving the entity's ID</li>
 * </ul></p>
 *
 * <p>Additionally, it includes named mappings for converting between lists of ClassificationAttribute and lists of IDs.</p>
 */
@Mapper(config = MapperConfig.class, uses = ClassificationAttributeMapper.class)
public interface CategoryMapper {

    @Mapping(source = "classificationAttributes", target = "attributeIds", qualifiedByName = "classificationAttributeToIdList")
    CategoryDTO toCategoryDto(Category entity);

    @Mapping(source = "attributeIds", target = "classificationAttributes", qualifiedByName = "idToClassificationAttributeList")
    Category toEntity(CategoryDTO dto);

    @Mapping(source = "attributeIds", target = "classificationAttributes", qualifiedByName = "idToClassificationAttributeList")
    void updateEntityFromDto(@MappingTarget Category category, CategoryDTO categoryDto);

    @Named("classificationAttributeToIdList")
    @IterableMapping(qualifiedByName = "classificationAttributeToId")
    List<Long> classificationAttributeToIdList(List<ClassificationAttribute> classificationAttributes);

    @Named("idToClassificationAttributeList")
    @IterableMapping(qualifiedByName = "idToClassificationAttribute")
    List<ClassificationAttribute> idToClassificationAttributeList(List<Long> ids);
}
