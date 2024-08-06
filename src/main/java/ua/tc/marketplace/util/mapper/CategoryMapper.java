package ua.tc.marketplace.util.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import ua.tc.marketplace.config.MapperConfig;
import ua.tc.marketplace.model.dto.category.CategoryDto;
import ua.tc.marketplace.model.dto.category.CreateCategoryDto;
import ua.tc.marketplace.model.dto.category.UpdateCategoryDto;
import ua.tc.marketplace.model.entity.Attribute;
import ua.tc.marketplace.model.entity.Category;

@Mapper(config = MapperConfig.class, uses = AttributeMapper.class)
public interface CategoryMapper {

  @Mapping(source = "attributes", target = "attribute")
  CategoryDto toCategoryDto(Category entity);

  @Mapping(source = "attribute", target = "attributes")
  Category toEntity(CategoryDto dto);

  @Mapping(target = "id", ignore = true)
  @Mapping(source = "attributeIds", target = "attributes", qualifiedByName = "idToAttributeList")
  Category toEntity(CreateCategoryDto dto);

  @Mapping(source = "attributes", target = "attributeIds", qualifiedByName = "attributeToIdList")
  CreateCategoryDto toCreateCategoryDto(Category entity);

  @Mapping(target = "id", ignore = true)
  @Mapping(source = "name", target = "name")
  @Mapping(source = "attributeIds", target = "attributes", qualifiedByName = "idToAttributeList")
  void updateEntityFromDto(@MappingTarget Category category, UpdateCategoryDto dto);

  @Named("attributeToIdList")
  @IterableMapping(qualifiedByName = "attributeToId")
  List<Long> attributeToIdList(List<Attribute> attributes);

  @Named("idToAttributeList")
  @IterableMapping(qualifiedByName = "idToAttribute")
  List<Attribute> idToAttributeList(List<Long> ids);
}
