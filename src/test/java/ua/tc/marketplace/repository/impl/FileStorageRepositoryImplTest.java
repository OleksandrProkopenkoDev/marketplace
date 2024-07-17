package ua.tc.marketplace.repository.impl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
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
import ua.tc.marketplace.exception.photo.FailedRetrieveFileException;
import ua.tc.marketplace.exception.photo.FailedStoreFileException;
import ua.tc.marketplace.exception.photo.FailedToListFilesInDirectoryException;
import ua.tc.marketplace.exception.photo.PhotoFileNotFoundException;
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

      when(file.getOriginalFilename()).thenReturn(originalFilename);
      filenameUtilsMock
          .when(() -> FilenameUtils.getExtension(originalFilename))
          .thenReturn(extension);
      doNothing().when(file).transferTo(any(File.class));
      imagingMock.when(() -> Imaging.getImageInfo(any(File.class))).thenReturn(imageInfo);
      when(imageInfo.getWidth()).thenReturn(1920);
      when(imageInfo.getHeight()).thenReturn(1080);
      when(file.getSize()).thenReturn(1024L);

      // Act
      Photo result = fileStorageRepository.writeFile(file, path);

      // Assert
      assertNotNull(result);

      assertEquals(1920, result.getMetadata().getWidth());
      assertEquals(1080, result.getMetadata().getHeight());
      assertEquals(extension, result.getMetadata().getExtension());
      assertEquals(1024L, result.getMetadata().getSize());

      imagingMock.verify(() -> Imaging.getImageInfo(any(File.class)));
    }
  }

  @Test
  void writeFile_shouldThrowFailedStoreFileException_whenIOExceptionOccurs() throws Exception {
    try (MockedStatic<Files> filesMock = mockStatic(Files.class);
        MockedStatic<FilenameUtils> filenameUtilsMock = mockStatic(FilenameUtils.class);
        MockedStatic<Imaging> imagingMock = mockStatic(Imaging.class)) {

      // Arrange
      String originalFilename = "test-image.jpg";
      String extension = "jpg";

      when(file.getOriginalFilename()).thenReturn(originalFilename);
      filenameUtilsMock
          .when(() -> FilenameUtils.getExtension(originalFilename))
          .thenReturn(extension);
      doThrow(new IOException()).when(file).transferTo(any(File.class));

      // Act & Assert
      assertThrows(
          FailedStoreFileException.class, () -> fileStorageRepository.writeFile(file, path));
    }
  }

  @Test
  void writeFile_shouldThrowFailedStoreFileException_whenImagingExceptionOccurs() throws Exception {
    try (MockedStatic<Files> filesMock = mockStatic(Files.class);
        MockedStatic<FilenameUtils> filenameUtilsMock = mockStatic(FilenameUtils.class);
        MockedStatic<Imaging> imagingMock = mockStatic(Imaging.class)) {

      // Arrange
      String originalFilename = "test-image.jpg";
      String extension = "jpg";

      when(file.getOriginalFilename()).thenReturn(originalFilename);
      filenameUtilsMock
          .when(() -> FilenameUtils.getExtension(originalFilename))
          .thenReturn(extension);
      doNothing().when(file).transferTo(any(File.class));
      imagingMock.when(() -> Imaging.getImageInfo(any(File.class))).thenThrow(new IOException());

      // Act & Assert
      assertThrows(
          FailedStoreFileException.class, () -> fileStorageRepository.writeFile(file, path));
    }
  }

  @Test
  void readFilesList_shouldReturnListOfByteArrays(@TempDir Path tempDir) throws IOException {
    // Arrange
    Path testFile1 = tempDir.resolve("file1.txt");
    Path testFile2 = tempDir.resolve("file2.txt");
    Files.createFile(testFile1);
    Files.createFile(testFile2);
    byte[] fileContent1 = "content1".getBytes();
    byte[] fileContent2 = "content2".getBytes();
    Files.write(testFile1, fileContent1);
    Files.write(testFile2, fileContent2);

    try (MockedStatic<Files> filesMock = mockStatic(Files.class)) {
      filesMock.when(() -> Files.list(tempDir)).thenReturn(Stream.of(testFile1, testFile2));
      filesMock.when(() -> Files.isRegularFile(testFile1)).thenReturn(true);
      filesMock.when(() -> Files.isRegularFile(testFile2)).thenReturn(true);
      filesMock.when(() -> Files.readAllBytes(testFile1)).thenReturn(fileContent1);
      filesMock.when(() -> Files.readAllBytes(testFile2)).thenReturn(fileContent2);

      // Act
      List<byte[]> result = fileStorageRepository.readFilesList(tempDir);

      // Assert
      assertEquals(2, result.size());
      assertEquals("content1", new String(result.get(0)));
      assertEquals("content2", new String(result.get(1)));
    }
  }

  @Test
  void readFilesList_shouldThrowFailedToListFilesInDirectoryException_whenIOException(
      @TempDir Path tempDir) {
    // Arrange
    try (MockedStatic<Files> filesMock = mockStatic(Files.class)) {
      filesMock.when(() -> Files.list(tempDir)).thenThrow(new IOException());

      // Act & Assert
      assertThrows(
          FailedToListFilesInDirectoryException.class,
          () -> fileStorageRepository.readFilesList(tempDir));
    }
  }

  @Test
  void readFile_shouldReturnFileContent(@TempDir Path tempDir) throws IOException {
    // Arrange
    Path testFile = tempDir.resolve("file1.txt");
    byte[] fileContent = "content1".getBytes();
    Files.write(testFile, fileContent);

    try (MockedStatic<Files> filesMock = mockStatic(Files.class)) {
      filesMock.when(() -> Files.readAllBytes(testFile)).thenReturn(fileContent);

      // Act
      byte[] result = fileStorageRepository.readFile(testFile);

      // Assert
      assertArrayEquals(fileContent, result);
    }
  }

  @Test
  void readFile_shouldThrowFailedRetrieveFileException_whenIOException(@TempDir Path tempDir) {
    // Arrange
    Path testFile = tempDir.resolve("file1.txt");

    try (MockedStatic<Files> filesMock = mockStatic(Files.class)) {
      filesMock.when(() -> Files.readAllBytes(testFile)).thenThrow(new IOException());

      // Act & Assert
      assertThrows(
          FailedRetrieveFileException.class, () -> fileStorageRepository.readFile(testFile));
    }
  }

  @Test
  void readFile_withFilename_shouldReturnFileContent(@TempDir Path tempDir) throws IOException {
    // Arrange
    String filename = "file1.txt";
    Path testFile = tempDir.resolve(filename);
    byte[] fileContent = "content1".getBytes();
    Files.write(testFile, fileContent);

    try (MockedStatic<Files> filesMock = mockStatic(Files.class)) {
      filesMock.when(() -> Files.exists(testFile)).thenReturn(true);
      filesMock.when(() -> Files.isRegularFile(testFile)).thenReturn(true);
      filesMock.when(() -> Files.readAllBytes(testFile)).thenReturn(fileContent);

      // Act
      byte[] result = fileStorageRepository.readFile(filename, tempDir);

      // Assert
      assertArrayEquals(fileContent, result);
    }
  }

  @Test
  void readFile_withFilename_shouldThrowPhotoFileNotFoundException_whenFileDoesNotExist(
      @TempDir Path tempDir) {
    // Arrange
    String filename = "file1.txt";
    Path testFile = tempDir.resolve(filename);

    try (MockedStatic<Files> filesMock = mockStatic(Files.class)) {
      filesMock.when(() -> Files.exists(testFile)).thenReturn(false);

      // Act & Assert
      assertThrows(
          PhotoFileNotFoundException.class,
          () -> fileStorageRepository.readFile(filename, tempDir));
    }
  }

  @Test
  void readFile_withFilename_shouldThrowFailedRetrieveFileException_whenIOException(
      @TempDir Path tempDir) {
    // Arrange
    String filename = "file1.txt";
    Path testFile = tempDir.resolve(filename);

    try (MockedStatic<Files> filesMock = mockStatic(Files.class)) {
      filesMock.when(() -> Files.exists(testFile)).thenReturn(true);
      filesMock.when(() -> Files.isRegularFile(testFile)).thenReturn(true);
      filesMock.when(() -> Files.readAllBytes(testFile)).thenThrow(new IOException());

      // Act & Assert
      assertThrows(
          FailedRetrieveFileException.class,
          () -> fileStorageRepository.readFile(filename, tempDir));
    }
  }

  @Test
  void deleteFile_shouldDeleteFileSuccessfully(@TempDir Path tempDir) throws IOException {
    // Arrange
    Path testFile = tempDir.resolve("file1.txt");
    Files.createFile(testFile);

    try (MockedStatic<Files> filesMock = mockStatic(Files.class)) {
      filesMock
          .when(() -> Files.delete(testFile))
          .thenAnswer(
              invocation -> {
                Files.deleteIfExists(testFile);
                return null;
              });

      // Act
      fileStorageRepository.deleteFile(testFile);

      // Assert
      filesMock.verify(() -> Files.delete(testFile));
    }
  }

  @Test
  void deleteFile_shouldThrowFailedRetrieveFileException_whenIOException(@TempDir Path tempDir)
      throws IOException {
    // Arrange
    Path testFile = tempDir.resolve("file1.txt");
    Files.createFile(testFile);

    try (MockedStatic<Files> filesMock = mockStatic(Files.class)) {
      filesMock.when(() -> Files.delete(testFile)).thenThrow(new IOException());

      // Act & Assert
      assertThrows(
          FailedRetrieveFileException.class, () -> fileStorageRepository.deleteFile(testFile));
    }
  }
}
