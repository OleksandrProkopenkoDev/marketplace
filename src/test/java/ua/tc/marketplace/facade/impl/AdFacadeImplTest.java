package ua.tc.marketplace.facade.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.tc.marketplace.data.AdTestData.getAdDto;
import static ua.tc.marketplace.data.AdTestData.getAttributeDtos;
import static ua.tc.marketplace.data.AdTestData.getAttributes;
import static ua.tc.marketplace.data.AdTestData.getCreateAdDto;
import static ua.tc.marketplace.data.AdTestData.getMockUpdatedAd;
import static ua.tc.marketplace.data.AdTestData.getMockUpdatedAdDto;
import static ua.tc.marketplace.data.AdTestData.getUpdateAdDto;
import static ua.tc.marketplace.data.AdTestData.getUpdatedAdAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;
import ua.tc.marketplace.exception.ad.AdNotFoundException;
import ua.tc.marketplace.model.dto.ad.AdAttributeDto;
import ua.tc.marketplace.model.dto.ad.AdDto;
import ua.tc.marketplace.model.dto.ad.CreateAdDto;
import ua.tc.marketplace.model.dto.ad.UpdateAdDto;
import ua.tc.marketplace.model.entity.Ad;
import ua.tc.marketplace.model.entity.AdAttribute;
import ua.tc.marketplace.model.entity.Category;
import ua.tc.marketplace.model.entity.User;
import ua.tc.marketplace.service.AdService;
import ua.tc.marketplace.service.AuthenticationService;
import ua.tc.marketplace.service.CategoryService;
import ua.tc.marketplace.service.DistanceService;
import ua.tc.marketplace.service.LocationService;
import ua.tc.marketplace.service.PhotoStorageService;
import ua.tc.marketplace.service.UserService;
import ua.tc.marketplace.util.ad_filtering.FilterSpecificationFactory;
import ua.tc.marketplace.util.mapper.AdMapper;

/**
 * This is a unit test class for the AdFacadeImpl class, responsible for testing the various
 * functionalities of the AdFacadeImpl implementation.
 *
 * <p>Test data is provided by static methods from the `AdTestData` class.
 */
@ExtendWith(MockitoExtension.class)
class AdFacadeImplTest {

  @Mock private AdService adService;
  @Mock private AdMapper adMapper;
  @Mock private PhotoStorageService photoService;
  @Mock private UserService userService;
  @Mock private CategoryService categoryService;
  @Mock private FilterSpecificationFactory filterSpecificationFactory;
  @Mock private  DistanceService distanceService;
  @Mock private  AuthenticationService authenticationService;
  @Mock private  LocationService locationService;


  @InjectMocks private AdFacadeImpl adFacade;

  @Test
  public void testGetAds() {
    // Arrange
    Ad ad = new Ad();
    List<AdAttributeDto> adAttributeDtos = getAttributeDtos();
    AdDto adDto = getAdDto(adAttributeDtos); // create a sample AdDto
    Pageable pageable = PageRequest.of(0, 10);
    Page<Ad> adPage = new PageImpl<>(Collections.singletonList(ad), pageable, 1);
    Map<String, String> filterCriteria = Map.of("title", "example");
    Specification<Ad> specification = mock(Specification.class);

    when(filterSpecificationFactory.getSpecification(anyMap())).thenReturn(specification);
    when(adService.findAll(any(Specification.class), any(Pageable.class))).thenReturn(adPage);
    when(adMapper.toAdDto(any(Ad.class))).thenReturn(adDto);

    // Act
    Page<AdDto> result = adFacade.findAll(filterCriteria, pageable);

    // Assert
    verify(filterSpecificationFactory).getSpecification(filterCriteria);
    verify(adService).findAll(specification, pageable);
    verify(adMapper).toAdDto(ad);
    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    assertEquals(adDto, result.getContent().getFirst());
  }

  @Test
  void findById_shouldFindAd_whenExists() {
    // Arrange
    List<AdAttributeDto> adAttributeDtos = getAttributeDtos();
    Long adId = 1L;
    Ad ad = new Ad(); // create a sample Ad entity
    AdDto adDto = getAdDto(adAttributeDtos); // create a sample AdDto

    // Mock repository method to return a mocked Ad entity
    when(adService.findAdById(adId)).thenReturn(ad);

    // Mock mapper method to return the provided AdDto
    when(adMapper.toAdDto(ad)).thenReturn(adDto);

    // Act
    AdDto result = adFacade.findAdById(adId);

    // Assert
    assertEquals(adDto, result);

    // Verify that repository method was called with correct argument
    verify(adService, times(1)).findAdById(adId);
  }

  @Test
  void findById_shouldThrow_whenNotExists() {
    // Arrange
    Long adId = 1L;

    // Mock repository method to return an empty Optional (simulating not found scenario)
    when(adService.findAdById(adId)).thenThrow(new AdNotFoundException(adId));

    // Act and Assert
    // Use assertThrows to verify that AdNotFoundException is thrown
    assertThrows(AdNotFoundException.class, () -> adFacade.findAdById(adId));

    // Verify that repository method was called with correct argument
    verify(adService, times(1)).findAdById(adId);
  }

  @Test
  void createNewAd_shouldCreate_whenValidInput() {
    // Arrange
    // Mock MultipartFile objects
    MultipartFile file1 = mock(MultipartFile.class);
    MultipartFile file2 = mock(MultipartFile.class);

    // Create an array of mock MultipartFiles
    MultipartFile[] files = new MultipartFile[] {file1, file2};

    CreateAdDto createAdDto = getCreateAdDto(files);

    User mockUser = new User(); // create a mock User entity
    Ad mockAd = new Ad(); // create a mock Ad entity
    Category mockCategory = new Category(); // create a mock Category entity
    mockCategory.setAttributes(getAttributes());
    AdDto mockAdDto = getAdDto(getAttributeDtos()); // create a mock AdDto

    // Mock userService to return mockUser when findUserById is called
    when(userService.findUserById(createAdDto.authorId())).thenReturn(mockUser);

    // Mock categoryService to return mockCategory when findCategoryById is called
    when(categoryService.findCategoryById(createAdDto.categoryId())).thenReturn(mockCategory);

    // Mock adMapper to return mockAd when getPrimitiveFields is called
    when(adMapper.getPrimitiveFields(createAdDto)).thenReturn(mockAd);

    // Mock adRepository to return mockAd when save is called
    when(adService.save(mockAd)).thenReturn(mockAd);

    when(adService.findAdById(mockAd.getId())).thenReturn(mockAd);

    // Mock adMapper to return mockAdDto when toAdDto is called
    when(adMapper.toAdDto(mockAd)).thenReturn(mockAdDto);

    // Act
    AdDto result = adFacade.createNewAd(createAdDto);

    // Assert
    assertEquals(mockAdDto, result);

    // Verify that userService method was called with correct argument
    verify(userService, times(1)).findUserById(createAdDto.authorId());

    // Verify that categoryService method was called with correct argument
    verify(categoryService, times(1)).findCategoryById(createAdDto.categoryId());

    // Verify that adMapper method was called with correct argument
    verify(adMapper, times(1)).getPrimitiveFields(createAdDto);

    // Verify that adRepository method was called with correct argument
    verify(adService, times(1)).save(mockAd);

    // Verify that photoService method was called with correct argument (assuming photoService is
    // called inside createNewAd)
    verify(photoService, times(1)).saveAdPhotos(mockAd.getId(), createAdDto.photoFiles());

    // Verify that adMapper method was called to convert Ad entity to AdDto
    verify(adMapper, times(1)).toAdDto(mockAd);
  }

  @Test
  void updateAd_shouldUpdate_whenValidInput() {
    // Arrange
    Long adId = 1L;
    UpdateAdDto updateAdDto = getUpdateAdDto();

    User mockUser = new User(); // create a mock User entity
    Category mockCategory = new Category(); // create a mock Category entity
    Ad existingAd = new Ad(); // create an existing mock Ad entity
    existingAd.setId(adId); // Set the ID for the existing Ad

    Ad updatedAd = new Ad(); // create an updated mock Ad entity
    updatedAd.setId(adId);
    updatedAd.setTitle(updateAdDto.title());
    updatedAd.setDescription(updateAdDto.description());
    updatedAd.setPrice(updateAdDto.price());

    AdDto mockUpdatedAdDto = getMockUpdatedAdDto(adId, updateAdDto); // create a mock updated AdDto

    Ad mockUpdatedAd = getMockUpdatedAd(adId, mockUser, updateAdDto, mockCategory);
    List<AdAttribute> adAttributes = getUpdatedAdAttributes(mockUpdatedAd);
    mockUpdatedAd.setAdAttributes(adAttributes);
    // Mock adRepository to return existingAd when findById is called
    when(adService.findAdById(adId)).thenReturn(existingAd);

    // Mock adMapper to return updatedAd when updateAd is called
    doAnswer(
            invocation -> {
              UpdateAdDto dto = invocation.getArgument(0);
              Ad adToUpdate = invocation.getArgument(1);
              adToUpdate.setTitle(dto.title());
              adToUpdate.setDescription(dto.description());
              adToUpdate.setPrice(dto.price());
              adToUpdate.setCategory(mockCategory); // Setting the updated category
              return null;
            })
        .when(adMapper)
        .updateAd(updateAdDto, existingAd);

    when(userService.findUserById(anyLong())).thenReturn(mockUser);

    // Mock categoryService to return mockCategory when findCategoryById is called
    when(categoryService.findCategoryById(updateAdDto.categoryId())).thenReturn(mockCategory);

    when(adService.save(any(Ad.class))).thenReturn(mockUpdatedAd);

    when(adMapper.toAdDto(mockUpdatedAd)).thenReturn(mockUpdatedAdDto);
    // Act
    AdDto result = adFacade.updateAd(adId, updateAdDto);

    // Assert
    assertEquals(mockUpdatedAdDto, result);
    assertEquals(updateAdDto.title(), existingAd.getTitle());
    assertEquals(updateAdDto.description(), existingAd.getDescription());
    assertEquals(updateAdDto.price(), existingAd.getPrice());
    assertEquals(mockCategory, existingAd.getCategory());
  }

  @Test
  void deleteAd_shouldDelete() {
    // Arrange
    Long adId = 1L;
    Ad mockAd = new Ad();
    mockAd.setId(adId);

    // Mock adRepository to return mockAd when findById is called
    when(adService.findAdById(adId)).thenReturn(mockAd);

    // Act
    adFacade.deleteAd(adId);

    // Assert
    verify(photoService, times(1)).deleteAllAdPhotos(mockAd);
    verify(adService, times(1)).delete(mockAd);
  }

  @Test
  public void deleteAd_shouldThrowAdNotFoundException_whenInvalidId() {
    // Arrange
    Long adId = 1L;

    // Mock adRepository to throw AdNotFoundException when findById is called
    when(adService.findAdById(adId)).thenThrow(new AdNotFoundException(adId));

    // Act & Assert
    assertThrows(AdNotFoundException.class, () -> adFacade.deleteAd(adId));
    verify(photoService, never()).deleteAllAdPhotos(any(Ad.class));
    verify(adService, never()).delete(any(Ad.class));
  }
}
