package ua.tc.marketplace.util.mapper;

import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import ua.tc.marketplace.config.MapperConfig;
import ua.tc.marketplace.exception.attribute.InvalidAttributeIdsException;
import ua.tc.marketplace.model.dto.attribute.AttributeDto;
import ua.tc.marketplace.model.dto.attribute.AttributeRequest;
import ua.tc.marketplace.model.entity.Attribute;
import ua.tc.marketplace.repository.AttributeRepository;

@Mapper(config = MapperConfig.class)
public abstract class AttributeMapper {

  @Autowired protected AttributeRepository attributeRepository;

  public abstract AttributeDto toDto(Attribute entity);

  public abstract Attribute toEntity(AttributeDto dto);

  @Mapping(target = "id", ignore = true)
  public abstract Attribute toEntity(AttributeRequest dto);

  @Mapping(target = "id", ignore = true)
  public abstract void updateEntityFromDto(AttributeRequest dto, @MappingTarget Attribute entity);

  @Named("attributeToId")
  public Long attributeToId(Attribute attribute) {
    return attribute != null ? attribute.getId() : null;
  }

  @Named("idToAttribute")
  public Attribute idToAttribute(Long id) {
    if (id == null) {
      return null;
    }
    return attributeRepository
        .findById(id)
        .orElseThrow(() -> new InvalidAttributeIdsException(Set.of(id)));
  }
}
