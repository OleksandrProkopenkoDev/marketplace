package ua.tc.marketplace.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import ua.tc.marketplace.exception.ad.AdNotFoundException;
import ua.tc.marketplace.exception.user.UserNotFoundException;
import ua.tc.marketplace.model.entity.Ad;
import ua.tc.marketplace.model.entity.Photo;
import ua.tc.marketplace.model.entity.User;
import ua.tc.marketplace.repository.AdRepository;
import ua.tc.marketplace.repository.FileStorageRepository;
import ua.tc.marketplace.repository.PhotoRepository;
import ua.tc.marketplace.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class PhotoStorageServiceImplTest {

  @Mock private AdRepository adRepository;
  @Mock private UserRepository userRepository;
  @Mock private FileStorageRepository fileStorageRepository;
  @Mock private PhotoRepository photoRepository;

  @InjectMocks private PhotoStorageServiceImpl photoStorageService;

  private User user;
  private Photo photo;
  private MultipartFile file;

  @BeforeEach
  void setUp() {
    user = new User();
    user.setId(1L);

    photo = new Photo();
    photo.setFilename("photo.jpg");

    file = mock(MultipartFile.class);
  }

  @Test
  void saveUserPhoto_newPhoto_success() {
    // Given
    String uploadDir = "temp";
    when(fileStorageRepository.getUploadDir()).thenReturn(uploadDir);
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(fileStorageRepository.writeFile(any(MultipartFile.class), any(Path.class)))
        .thenReturn(photo);
    when(userRepository.save(any(User.class))).thenReturn(user);

    // When
    Photo result = photoStorageService.saveUserPhoto(1L, file);

    // Then
    assertNotNull(result);
    assertEquals(photo, result);
    verify(fileStorageRepository).createDirectory(any(Path.class));
    verify(userRepository).save(user);
  }

  @Test
  void saveUserPhoto_replaceExistingPhoto_success() {
    // Given
    user.setProfilePicture(photo);
    String uploadDir = "temp";
    when(fileStorageRepository.getUploadDir()).thenReturn(uploadDir);
    Long userId = 1L;
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    when(fileStorageRepository.writeFile(any(MultipartFile.class), any(Path.class)))
        .thenReturn(photo);
    when(userRepository.save(any(User.class))).thenReturn(user);
    doNothing().when(photoRepository).delete(any(Photo.class));

    Photo result;
    try (MockedStatic<Files> filesMock = mockStatic(Files.class)) {
      filesMock.when(() -> Files.exists(any(Path.class))).thenReturn(true);
      filesMock.when(() -> Files.isRegularFile(any(Path.class))).thenReturn(true);
      // When
      result = photoStorageService.saveUserPhoto(userId, file);
    }

    // Then
    assertNotNull(result);
    assertEquals(photo, result);
    verify(fileStorageRepository).createDirectory(any(Path.class));
    verify(userRepository, times(2)).save(user);
    verify(photoRepository).delete(any(Photo.class));
  }

  @Test
  void saveUserPhoto_userNotFound_throwsException() {
    // Given
    when(userRepository.findById(1L)).thenReturn(Optional.empty());

    // When / Then
    assertThrows(UserNotFoundException.class, () -> photoStorageService.saveUserPhoto(1L, file));
  }

  @Test
  public void saveAdPhotos_success() {
    // Mock data
    Long adId = 1L;
    Ad ad = new Ad(); // Create ad entity as needed for testing
    // Mock MultipartFile objects
    MultipartFile file1 = mock(MultipartFile.class);
    MultipartFile file2 = mock(MultipartFile.class);

    // Create an array of mock MultipartFiles
    MultipartFile[] files = new MultipartFile[] {file1, file2};

    // Stubbing behavior
    when(adRepository.findById(adId)).thenReturn(Optional.of(ad));
    doNothing().when(fileStorageRepository).createDirectory(any());
    String uploadDir = "temp";
    when(fileStorageRepository.getUploadDir()).thenReturn(uploadDir);

    when(fileStorageRepository.writeFile(any(MultipartFile.class), any(Path.class)))
        .thenAnswer(
            invocation -> {
              MultipartFile file = invocation.getArgument(0);
              Photo photo1 = new Photo();
              photo1.setFilename(file.getOriginalFilename());
              return photo1; // Create mock Photo entity
            });
    when(adRepository.save(any(Ad.class))).thenReturn(ad);

    // Call the method under test
    List<Photo> savedPhotos = photoStorageService.saveAdPhotos(adId, files);

    // Assertions
    verify(adRepository, times(1)).findById(adId); // Verify findById was called once with adId
    verify(fileStorageRepository, times(1)).createDirectory(any()); // Verify directory creation
    verify(fileStorageRepository, times(2))
        .writeFile(
            any(MultipartFile.class), any()); // Verify writeFile called twice (for each file)
    verify(adRepository, times(1))
        .save(any(Ad.class)); // Verify save method called once with updated ad

    // Additional assertions as needed for savedPhotos
    assertEquals(2, savedPhotos.size());
    // Assert specific properties or conditions on savedPhotos
  }

  @Test
  public void saveAdPhotos_adNotFound() {
    Long adId = 1L;
    MultipartFile[] files = new MultipartFile[2];

    // Stubbing behavior for adRepository.findById
    when(adRepository.findById(adId)).thenReturn(Optional.empty());

    // Assertions
    assertThrows(
        AdNotFoundException.class,
        () -> {
          photoStorageService.saveAdPhotos(adId, files);
        });

    verify(fileStorageRepository, never())
        .createDirectory(any()); // Verify directory creation was not called
    verify(fileStorageRepository, never())
        .writeFile(any(MultipartFile.class), any()); // Verify writeFile was not called
    verify(adRepository, times(1)).findById(adId); // Verify findById was called once with adId
  }
}
