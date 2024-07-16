package ua.tc.marketplace.repository.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import ua.tc.marketplace.exception.photo.FailedStoreFileException;
import ua.tc.marketplace.exception.photo.WrongFilePathException;
import ua.tc.marketplace.model.entity.Photo;

@ExtendWith(MockitoExtension.class)
class FileStorageRepositoryImplTest {

  @Mock private MultipartFile file;

  @Mock private ImageInfo imageInfo;

  @InjectMocks private FileStorageRepositoryImpl fileStorageRepository;

  private Path path;

  @BeforeEach
  void setUp() {
    path = Paths.get("test-directory");
    fileStorageRepository.setUploadDir("test-upload-dir");
  }

  @Test
  void createDirectory_shouldCreateDirectorySuccessfully() {
    // Arrange
    Path testPath = Paths.get("testDirectory");

    try (MockedStatic<Files> filesMockedStatic = mockStatic(Files.class)) {
      filesMockedStatic.when(() -> Files.createDirectories(testPath)).thenReturn(testPath);

      // Act
      fileStorageRepository.createDirectory(testPath);

      // Assert
      filesMockedStatic.verify(() -> Files.createDirectories(testPath));
    }
  }

  @Test
  void createDirectory_shouldThrowExceptionOnIOException(@TempDir Path tempDir) throws IOException {
    // Arrange
    Path testPath = tempDir.resolve("testDirectory");

    try (MockedStatic<Files> filesMockedStatic = mockStatic(Files.class)) {
      // Mock IOException when creating directory
      when(Files.createDirectories(any(Path.class))).thenThrow(new IOException());

      // Act & Assert
      assertThrows(
          WrongFilePathException.class, () -> fileStorageRepository.createDirectory(testPath));
    }
  }

  @Test
  void writeFile_shouldWriteFileSuccessfully() throws Exception {
    try (MockedStatic<Files> filesMock = mockStatic(Files.class);
        MockedStatic<FilenameUtils> filenameUtilsMock = mockStatic(FilenameUtils.class);
        MockedStatic<Imaging> imagingMock = mockStatic(Imaging.class)) {

      // Arrange
      String originalFilename = "test-image.jpg";
      String extension = "jpg";
      String uniqueFilename = "unique-id." + extension;

      Path destinationFile = path.resolve(uniqueFilename);

      when(file.getOriginalFilename()).thenReturn(originalFilename);
      filenameUtilsMock.when(() -> FilenameUtils.getExtension(originalFilename)).thenReturn(extension);
      doNothing().when(file).transferTo(destinationFile.toFile());
      imagingMock.when(() -> Imaging.getImageInfo(destinationFile.toFile())).thenReturn(imageInfo);
      when(imageInfo.getWidth()).thenReturn(1920);
      when(imageInfo.getHeight()).thenReturn(1080);
      when(file.getSize()).thenReturn(1024L);

      // Act
      Photo result = fileStorageRepository.writeFile(file, path);

      // Assert
      assertNotNull(result);
      assertEquals(uniqueFilename, result.getFilename());
      assertEquals(1920, result.getMetadata().getWidth());
      assertEquals(1080, result.getMetadata().getHeight());
      assertEquals(extension, result.getMetadata().getExtension());
      assertEquals(1024.0, Optional.ofNullable(result.getMetadata().getSize()));

      verify(file).transferTo(destinationFile.toFile());
      imagingMock.verify(() -> Imaging.getImageInfo(destinationFile.toFile()));
    }
  }

  @Test
  void writeFile_shouldThrowFailedStoreFileExceptionWhenIOExceptionOccurs() throws Exception {
    try (MockedStatic<Files> filesMock = mockStatic(Files.class);
        MockedStatic<FilenameUtils> filenameUtilsMock = mockStatic(FilenameUtils.class);
        MockedStatic<Imaging> imagingMock = mockStatic(Imaging.class)) {

      // Arrange
      String originalFilename = "test-image.jpg";
      String extension = "jpg";
      String uniqueFilename = "unique-id." + extension;

      Path destinationFile = path.resolve(uniqueFilename);

      when(file.getOriginalFilename()).thenReturn(originalFilename);
      filenameUtilsMock.when(() -> FilenameUtils.getExtension(originalFilename)).thenReturn(extension);
      doThrow(new IOException()).when(file).transferTo(destinationFile.toFile());

      // Act & Assert
      assertThrows(FailedStoreFileException.class, () -> fileStorageRepository.writeFile(file, path));
    }
  }

  @Test
  void writeFile_shouldThrowFailedStoreFileExceptionWhenImagingExceptionOccurs() throws Exception {
    try (MockedStatic<Files> filesMock = mockStatic(Files.class);
        MockedStatic<FilenameUtils> filenameUtilsMock = mockStatic(FilenameUtils.class);
        MockedStatic<Imaging> imagingMock = mockStatic(Imaging.class)) {

      // Arrange
      String originalFilename = "test-image.jpg";
      String extension = "jpg";
      String uniqueFilename = "unique-id." + extension;

      Path destinationFile = path.resolve(uniqueFilename);

      when(file.getOriginalFilename()).thenReturn(originalFilename);
      filenameUtilsMock.when(() -> FilenameUtils.getExtension(originalFilename)).thenReturn(extension);
      doNothing().when(file).transferTo(destinationFile.toFile());
      imagingMock.when(() -> Imaging.getImageInfo(destinationFile.toFile())).thenThrow(new IOException());

      // Act & Assert
      assertThrows(FailedStoreFileException.class, () -> fileStorageRepository.writeFile(file, path));
    }
  }
}
