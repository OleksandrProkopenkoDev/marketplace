package ua.tc.marketplace.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import ua.tc.marketplace.exception.ad.AdNotFoundException;
import ua.tc.marketplace.exception.attribute.AdAttributesNotMatchCategoryException;
import ua.tc.marketplace.exception.attribute.AttributeNotFoundException;
import ua.tc.marketplace.exception.attribute.FailedToParseAdAttributesJsonException;
import ua.tc.marketplace.model.dto.ad.AdAttributeRequestDto;
import ua.tc.marketplace.model.dto.ad.AdDto;
import ua.tc.marketplace.model.dto.ad.CreateAdDto;
import ua.tc.marketplace.model.dto.ad.UpdateAdDto;
import ua.tc.marketplace.model.entity.Ad;
import ua.tc.marketplace.model.entity.AdAttribute;
import ua.tc.marketplace.model.entity.Attribute;
import ua.tc.marketplace.model.entity.Category;
import ua.tc.marketplace.model.entity.User;
import ua.tc.marketplace.repository.AdRepository;
import ua.tc.marketplace.service.AdService;
import ua.tc.marketplace.service.CategoryService;
import ua.tc.marketplace.service.PhotoStorageService;
import ua.tc.marketplace.service.UserService;
import ua.tc.marketplace.util.mapper.AdMapper;

/**
 * Implementation of the AdService interface providing CRUD operations for managing advertisements.
 * Uses AdRepository for database interactions, AdMapper for DTO mapping, and services for related
 * entities. Manages transactions and handles exceptions such as AdNotFoundException.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AdServiceImpl implements AdService {

  private final AdRepository adRepository;
  private final AdMapper adMapper;
  private final PhotoStorageService photoService;
  private final UserService userService;
  private final CategoryService categoryService;

  @Override
  @Transactional(readOnly = true)
  public Page<AdDto> findAll(Map<String, String> filterCriteria, Pageable pageable) {
    Specification<Ad> specification = getAdSpecification(filterCriteria);
    return adRepository.findAll(specification, pageable).map(adMapper::toAdDto);
  }

  @Transactional(readOnly = true)
  @Override
  public AdDto findAdById(Long adId) {
    Ad ad = getAd(adId);
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
    ad = adRepository.save(ad);

    photoService.saveAdPhotos(ad.getId(), dto.photoFiles());

    ad = getAd(ad.getId());
    return adMapper.toAdDto(ad);
  }

  @Override
  public AdDto updateAd(Long adId, UpdateAdDto dto) {
    Ad ad = getAd(adId);
    adMapper.updateAd(dto, ad);
    updateAdAttributes(dto, ad);

    Category category = categoryService.findCategoryById(dto.categoryId());
    User author = userService.findUserById(dto.authorId());
    ad.setCategory(category);
    ad.setAuthor(author);

    ad = adRepository.save(ad);
    return adMapper.toAdDto(ad);
  }

  @Override
  public void deleteAd(Long adId) {
    Ad ad = getAd(adId);
    photoService.deleteAllAdPhotos(ad);
    adRepository.delete(ad);
  }

  private Ad getAd(Long adId) {
    return adRepository.findById(adId).orElseThrow(() -> new AdNotFoundException(adId));
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
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readValue(dto.adAttributes(), new TypeReference<>() {});
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

  private Specification<Ad> getAdSpecification(Map<String, String> filterCriteria) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      filterCriteria.forEach(
          (key, value) -> {
            if (value != null) {
              switch (key) {
                case "title":
                  predicates.add(cb.like(root.get("title"), "%" + value + "%"));
                  break;
                case "description":
                  predicates.add(cb.like(root.get("description"), "%" + value + "%"));
                  break;
                case "priceMin":
                  predicates.add(cb.greaterThanOrEqualTo(root.get("price"), new BigDecimal(value)));
                  break;
                case "priceMax":
                  predicates.add(cb.lessThanOrEqualTo(root.get("price"), new BigDecimal(value)));
                  break;
                case "category":
                  predicates.add(cb.equal(root.get("category").get("id"), Long.valueOf(value)));
                  break;
                case "authorId":
                  predicates.add(cb.equal(root.get("author").get("id"), Long.valueOf(value)));
                  break;
                default:
                  if (key.startsWith("attribute_")) {
                    String attributeName = key.substring(10); // Remove "attribute_" prefix
                    Join<Ad, AdAttribute> join = root.join("adAttributes", JoinType.LEFT);
                    predicates.add(
                        cb.and(
                            cb.equal(join.get("attribute").get("name"), attributeName),
                            cb.like(join.get("value"), "%" + value + "%")));
                  }
                  break;
              }
            }
          });

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
