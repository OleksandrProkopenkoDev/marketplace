package ua.tc.marketplace.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import ua.tc.marketplace.config.MapperConfig;
import ua.tc.marketplace.exception.category.AttributeNotFoundException;
import ua.tc.marketplace.model.dto.attribute.AttributeDto;
import ua.tc.marketplace.model.entity.Attribute;
import ua.tc.marketplace.repository.AttributeRepository;

import java.util.Set;

@Mapper(config = MapperConfig.class)
public abstract class AttributeMapper {

  @Autowired
  protected AttributeRepository attributeRepository;

  public abstract AttributeDto toDto(Attribute entity);

  public abstract Attribute toEntity(AttributeDto dto);

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
            .orElseThrow(() -> new AttributeNotFoundException(Set.of(id)));
  }
}
