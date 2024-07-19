package ua.tc.marketplace.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ua.tc.marketplace.config.MapperConfig;
import ua.tc.marketplace.model.dto.category.CategoryDto;
import ua.tc.marketplace.model.dto.category.CreateCategoryDTO;
import ua.tc.marketplace.model.entity.Category;

@Mapper(config = MapperConfig.class, uses = ClassificationAttributeMapper.class)
public interface CategoryMapper {

    CategoryDto toDto(Category entity);

    CreateCategoryDTO toCreateCategoryDto(Category entity);

    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryDto dto);

    @Mapping(target = "id", ignore = true)
    Category toEntity(CreateCategoryDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(@MappingTarget Category category, CategoryDto categoryDto);


}
