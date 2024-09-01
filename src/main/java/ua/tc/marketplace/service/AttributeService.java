package ua.tc.marketplace.service;

import org.springframework.data.domain.Pageable;
import ua.tc.marketplace.model.dto.attribute.AttributeDTO;
import ua.tc.marketplace.model.dto.attribute.CreateAttributeDTO;
import ua.tc.marketplace.model.dto.attribute.UpdateAttributeDTO;
import ua.tc.marketplace.model.entity.Attribute;

import java.util.List;

public interface AttributeService {

  Attribute findAttributeById(Long attributeId);

  AttributeDTO update(Long id, UpdateAttributeDTO attributeDto);

  void deleteById(Long id);

  AttributeDTO createAttribute(CreateAttributeDTO attributeDto);

  AttributeDTO findById(Long id);

  List<AttributeDTO> findAll(Pageable pageable);
}
