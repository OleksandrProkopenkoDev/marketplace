package ua.tc.marketplace.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ua.tc.marketplace.config.MapperConfig;
import ua.tc.marketplace.model.dto.ad.AdDto;
import ua.tc.marketplace.model.dto.ad.CreateAdDto;
import ua.tc.marketplace.model.dto.ad.UpdateAdDto;
import ua.tc.marketplace.model.entity.Ad;

/**
 * Mapper interface using MapStruct for converting between Ad entities and DTOs. Defines mappings
 * for converting Ad to AdDto, extracting primitive fields from CreateAdDto, and updating an
 * existing Ad entity with fields from UpdateAdDto.
 */
@Mapper(config = MapperConfig.class, uses = AttributeMapper.class)
public interface AdMapper {

  @Mapping(target = "categoryId", source = "category.id")
  @Mapping(target = "authorId", source = "author.id")
  AdDto toAdDto(Ad ad);

  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "thumbnail", ignore = true)
  @Mapping(target = "photos", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "category", ignore = true)
  @Mapping(target = "author", ignore = true)
  Ad getPrimitiveFields(CreateAdDto dto);

  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "thumbnail", ignore = true)
  @Mapping(target = "photos", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "category", ignore = true)
  @Mapping(target = "author", ignore = true)
  void updateAd(UpdateAdDto dto, @MappingTarget Ad ad);
}
