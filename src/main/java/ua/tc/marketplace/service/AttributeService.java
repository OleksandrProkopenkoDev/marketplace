package ua.tc.marketplace.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.tc.marketplace.model.dto.attribute.AttributeDto;
import ua.tc.marketplace.model.dto.attribute.CreateAttributeDTO;
import ua.tc.marketplace.model.dto.attribute.UpdateAttributeDTO;
import ua.tc.marketplace.model.entity.Attribute;

import java.util.List;

public interface AttributeService {

  Attribute findAttributeById(Long attributeId);

  AttributeDto update(Long id, UpdateAttributeDTO attributeDto);

  void deleteById(Long id);

  AttributeDto createAttribute(CreateAttributeDTO attributeDto);

  AttributeDto findById(Long id);

  List<AttributeDto> findAll(Pageable pageable);
}
