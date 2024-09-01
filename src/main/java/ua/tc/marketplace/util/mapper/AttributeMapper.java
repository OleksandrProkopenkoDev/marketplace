package ua.tc.marketplace.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.mapstruct.Mapping;
import ua.tc.marketplace.config.MapperConfig;
import ua.tc.marketplace.exception.attribute.InvalidAttributeIdsException;
import ua.tc.marketplace.model.dto.attribute.AttributeDTO;
import ua.tc.marketplace.model.dto.attribute.CreateAttributeDTO;
import ua.tc.marketplace.model.dto.attribute.UpdateAttributeDTO;
import ua.tc.marketplace.model.entity.Attribute;
import ua.tc.marketplace.repository.AttributeRepository;

import java.util.Set;

@Mapper(config = MapperConfig.class)
public abstract class AttributeMapper {

  @Autowired
  protected AttributeRepository attributeRepository;

  public abstract AttributeDTO toDto(Attribute entity);

  public abstract Attribute toEntity(AttributeDTO dto);

  @Mapping(target = "id", ignore = true)
  public abstract Attribute toEntity(CreateAttributeDTO dto);

  @Mapping(target = "id", ignore = true)
  public abstract void updateEntityFromDto(UpdateAttributeDTO dto, @MappingTarget Attribute entity);


  @Named("attributeToId")
  public Long attributeToId(Attribute attribute) {
    return attribute != null ? attribute.getId() : null;
  }

  @Named("idToAttribute")
  public Attribute idToAttribute(Long id) {
    if (id == null) {
      return null;
    }
    return attributeRepository.findById(id)
            .orElseThrow(() -> new InvalidAttributeIdsException(Set.of(id)));
  }
}
