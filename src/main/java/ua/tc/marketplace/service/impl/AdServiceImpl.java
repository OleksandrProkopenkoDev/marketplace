package ua.tc.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tc.marketplace.exception.ad.AdNotFoundException;
import ua.tc.marketplace.model.dto.ad.AdDto;
import ua.tc.marketplace.model.dto.ad.CreateAdDto;
import ua.tc.marketplace.model.dto.ad.UpdateAdDto;
import ua.tc.marketplace.model.entity.Ad;
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

  @Transactional(readOnly = true)
  @Override
  public Page<AdDto> findAll(Pageable pageable) {
    return adRepository.findAll(pageable).map(adMapper::toAdDto);
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
}
