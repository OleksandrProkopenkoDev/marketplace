package ua.tc.marketplace.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.tc.marketplace.config.MapperConfig;
import ua.tc.marketplace.model.dto.ad.AdAttributeDto;
import ua.tc.marketplace.model.entity.AdAttribute;

/** Mapper interface for converting AdAttribute entities to AdAttributeDto. */
@Mapper(config = MapperConfig.class)
public interface AttributeMapper {

  @Mapping(target = "name", source = "attribute.name")
  AdAttributeDto adAttributeDto(AdAttribute adAttribute);
}
