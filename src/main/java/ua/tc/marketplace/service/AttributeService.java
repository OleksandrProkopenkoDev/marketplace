package ua.tc.marketplace.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import ua.tc.marketplace.model.dto.attribute.AttributeDto;
import ua.tc.marketplace.model.dto.attribute.AttributeRequest;
import ua.tc.marketplace.model.entity.Attribute;

public interface AttributeService {

  Attribute findAttributeById(Long attributeId);

  AttributeDto update(Long id, AttributeRequest attributeDto);

  void deleteById(Long id);

  AttributeDto createAttribute(AttributeRequest attributeDto);

  AttributeDto findById(Long id);

  List<AttributeDto> findAll(Pageable pageable);
}
