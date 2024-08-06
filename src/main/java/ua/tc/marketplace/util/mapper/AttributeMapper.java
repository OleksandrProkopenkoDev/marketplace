package ua.tc.marketplace.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ua.tc.marketplace.config.MapperConfig;
import ua.tc.marketplace.model.dto.attribute.AttributeDto;
import ua.tc.marketplace.model.entity.Attribute;

@Mapper(config = MapperConfig.class)
public interface AttributeMapper {

  AttributeDto toDto(Attribute entity);

  Attribute toEntity(AttributeDto dto);

  @Named("attributeToId")
  default Long attributeToId(Attribute attribute) {
    return attribute != null ? attribute.getId() : null;
  }

  @Named("idToAttribute")
  default Attribute idToAttribute(Long id) {
    if (id == null) {
      return null;
    }
    Attribute attribute = new Attribute();
    attribute.setId(id);
    return attribute;
  }
}
