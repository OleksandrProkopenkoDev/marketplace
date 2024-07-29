package ua.tc.marketplace.facade.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tc.marketplace.exception.attribute.AdAttributesNotMatchCategoryException;
import ua.tc.marketplace.exception.attribute.AttributeNotFoundException;
import ua.tc.marketplace.exception.attribute.FailedToParseAdAttributesJsonException;
import ua.tc.marketplace.facade.AdFacade;
import ua.tc.marketplace.model.dto.ad.AdAttributeRequestDto;
import ua.tc.marketplace.model.dto.ad.AdDto;
import ua.tc.marketplace.model.dto.ad.CreateAdDto;
import ua.tc.marketplace.model.dto.ad.UpdateAdDto;
import ua.tc.marketplace.model.entity.Ad;
import ua.tc.marketplace.model.entity.AdAttribute;
import ua.tc.marketplace.model.entity.Attribute;
import ua.tc.marketplace.model.entity.Category;
import ua.tc.marketplace.model.entity.User;
import ua.tc.marketplace.service.AdService;
import ua.tc.marketplace.service.CategoryService;
import ua.tc.marketplace.service.PhotoStorageService;
import ua.tc.marketplace.service.UserService;
import ua.tc.marketplace.util.ad_filtering.FilterSpecificationFactory;
import ua.tc.marketplace.util.mapper.AdMapper;

/**
 * AdFacadeImpl implements the AdFacade interface, providing concrete operations for managing ads in
 * the system.
 *
 * <p>This class is responsible for interacting with the underlying services and repositories to
 * handle ad-related operations, including creation, update, retrieval, and deletion of ads. It also
 * manages ad attributes and performs filtering based on criteria provided in the form of DTOs.
 */
@Service
@RequiredArgsConstructor
public class AdFacadeImpl implements AdFacade {

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final AdService adService;
  private final AdMapper adMapper;
  private final PhotoStorageService photoService;
  private final UserService userService;
  private final CategoryService categoryService;
  private final FilterSpecificationFactory filterSpecificationFactory;

  @Override
  @Transactional(readOnly = true)
  public Page<AdDto> findAll(Map<String, String> filterCriteria, Pageable pageable) {
    Specification<Ad> specification = filterSpecificationFactory.getSpecification(filterCriteria);
    return adService.findAll(specification, pageable).map(adMapper::toAdDto);
  }

  @Transactional(readOnly = true)
  @Override
  public AdDto findAdById(Long adId) {
    Ad ad = adService.findAdById(adId);
    return adMapper.toAdDto(ad);
  }

  @Override
  public AdDto createNewAd(CreateAdDto dto) {
    Ad ad = adMapper.getPrimitiveFields(dto);

    User author = userService.findUserById(dto.authorId());

    Category category = categoryService.findCategoryById(dto.categoryId());

    List<AdAttributeRequestDto> adAttributeRequestDtos = parseJsonAdAttributes(dto);

    List<AdAttribute> adAttributes = mapToAdAttributes(adAttributeRequestDtos, category, ad);

    ad.setAdAttributes(adAttributes);
    ad.setAuthor(author);
    ad.setCategory(category);
    ad = adService.save(ad);

    photoService.saveAdPhotos(ad.getId(), dto.photoFiles());

    ad = adService.findAdById(ad.getId());
    return adMapper.toAdDto(ad);
  }

  @Override
  public AdDto updateAd(Long adId, UpdateAdDto dto) {
    Ad ad = adService.findAdById(adId);
    adMapper.updateAd(dto, ad);
    updateAdAttributes(dto, ad);

    Category category = categoryService.findCategoryById(dto.categoryId());
    User author = userService.findUserById(dto.authorId());
    ad.setCategory(category);
    ad.setAuthor(author);

    ad = adService.save(ad);
    return adMapper.toAdDto(ad);
  }

  @Override
  public void deleteAd(Long adId) {
    Ad ad = adService.findAdById(adId);
    photoService.deleteAllAdPhotos(ad);
    adService.delete(ad);
  }

  private AdAttribute getAdAttribute(Entry<Long, String> entry, Ad finalAd, Category category) {
    return new AdAttribute(
        null,
        finalAd,
        category.getAttributes().stream()
            .filter(attribute -> attribute.getId().equals(entry.getKey()))
            .findFirst()
            .orElseThrow(() -> new AttributeNotFoundException(entry.getKey())),
        entry.getValue());
  }

  private List<AdAttributeRequestDto> parseJsonAdAttributes(CreateAdDto dto) {
    try {
      return objectMapper.readValue(dto.adAttributes(), new TypeReference<>() {});
    } catch (IOException e) {
      throw new FailedToParseAdAttributesJsonException(dto.adAttributes());
    }
  }

  private List<AdAttribute> mapToAdAttributes(
      List<AdAttributeRequestDto> adAttributeRequestDtos, Category category, Ad ad) {

    Map<Long, String> attributeMap =
        adAttributeRequestDtos.stream()
            .collect(
                Collectors.toMap(AdAttributeRequestDto::attributeId, AdAttributeRequestDto::value));
    // Convert the list of attributes to a set of IDs
    Set<Long> requiredAttributeIds =
        category.getAttributes().stream().map(Attribute::getId).collect(Collectors.toSet());

    // Check if every key in attributeMap is present in attributeIds
    boolean allKeysPresent = requiredAttributeIds.containsAll(attributeMap.keySet());

    if (allKeysPresent) {
      return attributeMap.entrySet().stream()
          .map(entry -> getAdAttribute(entry, ad, category))
          .collect(Collectors.toList());
    } else {
      throw new AdAttributesNotMatchCategoryException(attributeMap.keySet(), requiredAttributeIds);
    }
  }

  private void updateAdAttributes(UpdateAdDto dto, Ad ad) {
    List<AdAttributeRequestDto> adAttributeRequestDtos = dto.adAttributes();
    List<AdAttribute> adAttributes = ad.getAdAttributes();

    // Step 1: Convert adAttributeRequestDtos to a Map<Long, String> for quick lookup
    Map<Long, String> attributeUpdates =
        adAttributeRequestDtos.stream()
            .collect(
                Collectors.toMap(AdAttributeRequestDto::attributeId, AdAttributeRequestDto::value));

    // Step 2: Update adAttributes list based on the attributeUpdates map
    adAttributes.forEach(
        adAttribute -> {
          if (attributeUpdates.containsKey(adAttribute.getAttribute().getId())) {
            adAttribute.setValue(attributeUpdates.get(adAttribute.getAttribute().getId()));
          }
        });
  }
}
