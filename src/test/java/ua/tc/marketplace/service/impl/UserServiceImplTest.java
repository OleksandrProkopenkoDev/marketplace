package ua.tc.marketplace.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import ua.tc.marketplace.exception.ad.AdNotFoundException;
import ua.tc.marketplace.model.dto.ad.AdDto;
import ua.tc.marketplace.model.dto.ad.CreateAdDto;
import ua.tc.marketplace.model.dto.ad.UpdateAdDto;
import ua.tc.marketplace.model.dto.user.UserDto;
import ua.tc.marketplace.model.entity.Ad;
import ua.tc.marketplace.model.entity.Category;
import ua.tc.marketplace.model.entity.User;
import ua.tc.marketplace.repository.AdRepository;
import ua.tc.marketplace.repository.UserRepository;
import ua.tc.marketplace.service.CategoryService;
import ua.tc.marketplace.service.PhotoStorageService;
import ua.tc.marketplace.service.UserService;
import ua.tc.marketplace.util.mapper.AdMapper;
import ua.tc.marketplace.util.mapper.UserMapper;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock private UserRepository userRepository;
  @Mock private UserMapper userMapper;
  @Mock private PhotoStorageService photoService;
  @Mock private CategoryService categoryService;

  @InjectMocks private UserService userService;

  @Test
  void findUserById_shouldFindUser_whenExists() {
    // Arrange
    Long userId = 1L;
    User user = new User();
    UserDto userDto =
        new UserDto(
            1L,
            "taras@shevchenko.ua",
            "password",
            "INDIVIDUAL",
            "Taras",
            "Shevchenko",
            null,
            null,
            Collections.emptyList(),
            LocalDateTime.now(),
            LocalDateTime.now());

    // Mock repository
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    // Mock mapper
    when(userMapper.toDto(user)).thenReturn(userDto);

    // run method
    AdDto result = adService.findAdById(adId);

    // Assert results
    assertEquals(adDto, result);

    // Verify that repository method was called with correct argument
    verify(adRepository, times(1)).findById(adId);
  }

  @Test
  void findUserById_shouldThrow_whenNotExists() {
    // Arrange
    Long adId = 1L;

    // Mock repository method to return an empty Optional (simulating not found scenario)
    when(adRepository.findById(adId)).thenReturn(Optional.empty());

    // Act and Assert
    // Use assertThrows to verify that AdNotFoundException is thrown
    assertThrows(AdNotFoundException.class, () -> adService.findAdById(adId));

    // Verify that repository method was called with correct argument
    verify(adRepository, times(1)).findById(adId);
  }

  @Test
  void createUser_shouldCreate_whenValidInput() {
    // Arrange
    // Mock MultipartFile objects
    MultipartFile file1 = mock(MultipartFile.class);
    MultipartFile file2 = mock(MultipartFile.class);

    // Create an array of mock MultipartFiles
    MultipartFile[] files = new MultipartFile[] {file1, file2};

    CreateAdDto createAdDto =
        new CreateAdDto(
            1L, "Sample Ad", "This is a sample ad", BigDecimal.valueOf(100.00), files, 1L);

    User mockUser = new User(); // create a mock User entity
    Category mockCategory = new Category(); // create a mock Category entity
    Ad mockAd = new Ad(); // create a mock Ad entity
    AdDto mockAdDto =
        new AdDto(
            1L,
            1L,
            "Sample Ad",
            "This is a sample ad",
            BigDecimal.valueOf(100.00),
            Collections.emptyList(),
            null,
            1L,
            LocalDateTime.now(),
            LocalDateTime.now()); // create a mock AdDto

    // Mock userService to return mockUser when findUserById is called
    when(userService.findUserById(createAdDto.authorId())).thenReturn(mockUser);

    // Mock categoryService to return mockCategory when findCategoryById is called
    when(categoryService.findCategoryById(createAdDto.categoryId())).thenReturn(mockCategory);

    // Mock adMapper to return mockAd when getPrimitiveFields is called
    when(adMapper.getPrimitiveFields(createAdDto)).thenReturn(mockAd);

    // Mock adRepository to return mockAd when save is called
    when(adRepository.save(mockAd)).thenReturn(mockAd);

    when(adRepository.findById(mockAd.getId())).thenReturn(Optional.of(mockAd));

    // Mock adMapper to return mockAdDto when toAdDto is called
    when(adMapper.toAdDto(mockAd)).thenReturn(mockAdDto);

    // Act
    AdDto result = adService.createNewAd(createAdDto);

    // Assert
    assertEquals(mockAdDto, result);

    // Verify that userService method was called with correct argument
    verify(userService, times(1)).findUserById(createAdDto.authorId());

    // Verify that categoryService method was called with correct argument
    verify(categoryService, times(1)).findCategoryById(createAdDto.categoryId());

    // Verify that adMapper method was called with correct argument
    verify(adMapper, times(1)).getPrimitiveFields(createAdDto);

    // Verify that adRepository method was called with correct argument
    verify(adRepository, times(1)).save(mockAd);

    // Verify that photoService method was called with correct argument (assuming photoService is
    // called inside createNewAd)
    verify(photoService, times(1)).saveAdPhotos(mockAd.getId(), createAdDto.photoFiles());

    // Verify that adMapper method was called to convert Ad entity to AdDto
    verify(adMapper, times(1)).toAdDto(mockAd);
  }

  @Test
  void updateUser_shouldUpdate_whenValidInput() {
    // Arrange
    Long adId = 1L;
    UpdateAdDto updateAdDto = new UpdateAdDto(
        1L, // Mock author ID
        "Updated Title",
        "Updated Description",
        BigDecimal.valueOf(200.00),
        2L // Updated Category ID
    );

    User mockUser = new User(); // create a mock User entity
    Category mockCategory = new Category(); // create a mock Category entity
    Ad existingAd = new Ad(); // create an existing mock Ad entity
    existingAd.setId(adId); // Set the ID for the existing Ad

    Ad updatedAd = new Ad(); // create an updated mock Ad entity
    updatedAd.setId(adId);
    updatedAd.setTitle(updateAdDto.title());
    updatedAd.setDescription(updateAdDto.description());
    updatedAd.setPrice(updateAdDto.price());

    AdDto mockUpdatedAdDto = new AdDto(
        adId,
        updateAdDto.authorId(),
        updateAdDto.title(),
        updateAdDto.description(),
        updateAdDto.price(),
        null, // Assuming no photos in this test case
        null, // Assuming no thumbnail in this test case
        updateAdDto.categoryId(),
        LocalDateTime.now(),
        LocalDateTime.now()
    ); // create a mock updated AdDto

    Ad mockUpdatedAd = new Ad(
        adId,
        mockUser,
        updateAdDto.title(),
        updateAdDto.description(),
        updateAdDto.price(),
        null, // Assuming no photos in this test case
        null, // Assuming no thumbnail in this test case
        mockCategory,
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    // Mock adRepository to return existingAd when findById is called
    when(adRepository.findById(adId)).thenReturn(Optional.of(existingAd));

    // Mock adMapper to return updatedAd when updateAd is called
    doAnswer(invocation -> {
      UpdateAdDto dto = invocation.getArgument(0);
      Ad adToUpdate = invocation.getArgument(1);
      adToUpdate.setTitle(dto.title());
      adToUpdate.setDescription(dto.description());
      adToUpdate.setPrice(dto.price());
      adToUpdate.setCategory(mockCategory); // Setting the updated category
      return null;
    }).when(adMapper).updateAd(updateAdDto, existingAd);

    when(userService.findUserById(anyLong())).thenReturn(mockUser);

    // Mock categoryService to return mockCategory when findCategoryById is called
    when(categoryService.findCategoryById(updateAdDto.categoryId())).thenReturn(mockCategory);

    when(adRepository.save(any(Ad.class))).thenReturn(mockUpdatedAd);

    when(adMapper.toAdDto(mockUpdatedAd)).thenReturn(mockUpdatedAdDto);
    // Act
    AdDto result = adService.updateAd(adId, updateAdDto);

    // Assert
    assertEquals(mockUpdatedAdDto, result);
    assertEquals(updateAdDto.title(), existingAd.getTitle());
    assertEquals(updateAdDto.description(), existingAd.getDescription());
    assertEquals(updateAdDto.price(), existingAd.getPrice());
    assertEquals(mockCategory, existingAd.getCategory());
  }

  @Test
  void updateUser_shouldThrow_whenUserNotExists() {
    // Arrange
    Long adId = 1L;
    UpdateAdDto updateAdDto = new UpdateAdDto(
        1L, // Mock author ID
        "Updated Title",
        "Updated Description",
        BigDecimal.valueOf(200.00),
        2L // Updated Category ID
    );

    User mockUser = new User(); // create a mock User entity
    Category mockCategory = new Category(); // create a mock Category entity
    Ad existingAd = new Ad(); // create an existing mock Ad entity
    existingAd.setId(adId); // Set the ID for the existing Ad

    Ad updatedAd = new Ad(); // create an updated mock Ad entity
    updatedAd.setId(adId);
    updatedAd.setTitle(updateAdDto.title());
    updatedAd.setDescription(updateAdDto.description());
    updatedAd.setPrice(updateAdDto.price());

    AdDto mockUpdatedAdDto = new AdDto(
        adId,
        updateAdDto.authorId(),
        updateAdDto.title(),
        updateAdDto.description(),
        updateAdDto.price(),
        null, // Assuming no photos in this test case
        null, // Assuming no thumbnail in this test case
        updateAdDto.categoryId(),
        LocalDateTime.now(),
        LocalDateTime.now()
    ); // create a mock updated AdDto

    Ad mockUpdatedAd = new Ad(
        adId,
        mockUser,
        updateAdDto.title(),
        updateAdDto.description(),
        updateAdDto.price(),
        null, // Assuming no photos in this test case
        null, // Assuming no thumbnail in this test case
        mockCategory,
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    // Mock adRepository to return existingAd when findById is called
    when(adRepository.findById(adId)).thenReturn(Optional.of(existingAd));

    // Mock adMapper to return updatedAd when updateAd is called
    doAnswer(invocation -> {
      UpdateAdDto dto = invocation.getArgument(0);
      Ad adToUpdate = invocation.getArgument(1);
      adToUpdate.setTitle(dto.title());
      adToUpdate.setDescription(dto.description());
      adToUpdate.setPrice(dto.price());
      adToUpdate.setCategory(mockCategory); // Setting the updated category
      return null;
    }).when(adMapper).updateAd(updateAdDto, existingAd);

    when(userService.findUserById(anyLong())).thenReturn(mockUser);

    // Mock categoryService to return mockCategory when findCategoryById is called
    when(categoryService.findCategoryById(updateAdDto.categoryId())).thenReturn(mockCategory);

    when(adRepository.save(any(Ad.class))).thenReturn(mockUpdatedAd);

    when(adMapper.toAdDto(mockUpdatedAd)).thenReturn(mockUpdatedAdDto);
    // Act
    AdDto result = adService.updateAd(adId, updateAdDto);

    // Assert
    assertEquals(mockUpdatedAdDto, result);
    assertEquals(updateAdDto.title(), existingAd.getTitle());
    assertEquals(updateAdDto.description(), existingAd.getDescription());
    assertEquals(updateAdDto.price(), existingAd.getPrice());
    assertEquals(mockCategory, existingAd.getCategory());
  }

  @Test
  void deleteUser_shouldDelete() {
    // Arrange
    Long adId = 1L;
    Ad mockAd = new Ad();
    mockAd.setId(adId);

    // Mock adRepository to return mockAd when findById is called
    when(adRepository.findById(adId)).thenReturn(Optional.of(mockAd));

    // Act
    adService.deleteAd(adId);

    // Assert
    verify(photoService, times(1)).deleteAllAdPhotos(mockAd);
    verify(adRepository, times(1)).delete(mockAd);
  }

  @Test
  public void deleteUser_shouldThrowException_whenUserNotExists() {
    // Arrange
    Long adId = 1L;

    // Mock adRepository to throw AdNotFoundException when findById is called
    when(adRepository.findById(adId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(AdNotFoundException.class, () -> adService.deleteAd(adId));
    verify(photoService, never()).deleteAllAdPhotos(any(Ad.class));
    verify(adRepository, never()).delete(any(Ad.class));
  }
}
