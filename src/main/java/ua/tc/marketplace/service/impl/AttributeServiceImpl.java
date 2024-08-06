package ua.tc.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.tc.marketplace.exception.attribute.AttributeNotFoundException;
import ua.tc.marketplace.model.entity.Attribute;
import ua.tc.marketplace.repository.AttributeRepository;
import ua.tc.marketplace.service.AttributeService;

@Service
@RequiredArgsConstructor
public class AttributeServiceImpl implements AttributeService {

  private final AttributeRepository attributeRepository;

  @Override
  public Attribute findAttributeById(Long attributeId) {
    return attributeRepository
        .findById(attributeId)
        .orElseThrow(() -> new AttributeNotFoundException(attributeId));
  }
}
