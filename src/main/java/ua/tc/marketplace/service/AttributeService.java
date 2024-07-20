package ua.tc.marketplace.service;

import ua.tc.marketplace.model.entity.Attribute;

public interface AttributeService {

  Attribute findAttributeById(Long attributeId);
}
