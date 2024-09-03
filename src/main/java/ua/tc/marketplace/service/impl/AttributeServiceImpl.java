package ua.tc.marketplace.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tc.marketplace.exception.attribute.AttributeDeletionException;
import ua.tc.marketplace.exception.attribute.AttributeNotFoundException;
import ua.tc.marketplace.model.dto.attribute.AttributeDto;
import ua.tc.marketplace.model.dto.attribute.AttributeRequest;
import ua.tc.marketplace.model.entity.Attribute;
import ua.tc.marketplace.repository.AttributeRepository;
import ua.tc.marketplace.repository.CategoryRepository;
import ua.tc.marketplace.service.AttributeService;
import ua.tc.marketplace.util.mapper.AttributeMapper;

/**
 * Implementation of the {@link ua.tc.marketplace.service.AttributeService} interface for managing
 * attribute-related operations.
 *
 * <p>This service provides methods for creating, updating, deleting, and retrieving attribute. It
 * handles operations such as saving new attributes, updating existing attributes, retrieving all
 * attributes with pagination support, retrieving a attribute by its ID, and deleting a attribute by
 * its ID.
 */
@Service
@RequiredArgsConstructor
public class AttributeServiceImpl implements AttributeService {

  private final AttributeRepository attributeRepository;
  private final AttributeMapper attributeMapper;
  private final CategoryRepository categoryRepository;

  @Override
  public Attribute findAttributeById(Long attributeId) {
    return attributeRepository
        .findById(attributeId)
        .orElseThrow(() -> new AttributeNotFoundException(attributeId));
  }

  @Override
  public List<AttributeDto> findAll(Pageable pageable) {
    Page<Attribute> attributes = attributeRepository.findAll(pageable);
    return attributes.stream().map(attributeMapper::toDto).collect(Collectors.toList());
  }

  @Override
  public AttributeDto findById(Long id) {
    Attribute attribute = findAttributeById(id);
    return attributeMapper.toDto(attribute);
  }

  @Transactional
  @Override
  public AttributeDto createAttribute(AttributeRequest attributeDto) {
    Attribute attribute = attributeMapper.toEntity(attributeDto);
    Attribute savedAttribute = attributeRepository.save(attribute);
    return attributeMapper.toDto(savedAttribute);
  }

  @Transactional
  @Override
  public AttributeDto update(Long id, AttributeRequest attributeDto) {
    Attribute existingAttribute = findAttributeById(id);
    attributeMapper.updateEntityFromDto(attributeDto, existingAttribute);
    Attribute updatedAttribute = attributeRepository.save(existingAttribute);
    return attributeMapper.toDto(updatedAttribute);
  }

  @Transactional
  @Override
  public void deleteById(Long attributeId) {
    if (!attributeRepository.existsById(attributeId)) {
      throw new AttributeNotFoundException(attributeId);
    }

    if (isAttributeLinkedToCategory(attributeId)) {
      throw new AttributeDeletionException(attributeId);
    }

    attributeRepository.deleteById(attributeId);
  }

  private boolean isAttributeLinkedToCategory(Long attributeId) {

    return categoryRepository.existsByAttributesId(attributeId);
  }
}
