package ua.tc.marketplace.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;
import ua.tc.marketplace.exception.photo.FailedRetrieveFileException;
import ua.tc.marketplace.repository.AdRepository;
import ua.tc.marketplace.repository.FileStorageRepository;

@ExtendWith(MockitoExtension.class)
public class PhotoStorageServiceImplTest {

  @Mock private AdRepository adRepository;

  @Mock private FileStorageRepository fileStorageRepository;

  @InjectMocks private PhotoStorageServiceImpl photoStorageService;

  @Value("${file.upload-dir}")
  private String uploadDir;

  private String testUploadDir;

  private static final Long EXAMPLE_AD_ID = 1L;

  private static final String SLASH = File.separator;

  @BeforeEach
  public void setup() {
    createTempDirectory();
  }

  @AfterEach
  public void cleanup() {
    deleteTempDirectory();
  }

  /*

    @Test
    void storeAdPhotos_shouldSave_whenValidInput() {




      // Mock data
      MockMultipartFile file1 = createJPEGImage("image1");
      MockMultipartFile file2 = createJPEGImage("image2");
      MockMultipartFile[] files = {file1, file2};
      PhotoFilesDto photoFilesDto = new PhotoFilesDto(EXAMPLE_AD_ID, files);

      // Call the method
      List<Photo> photos = photoStorageService.storeAdPhotos(photoFilesDto);

      // Assertions
      assertNotNull(photos);
      assertEquals(2, photos.size());

      // Verify file existence in the upload directory
      String folderPath = uploadDir + SLASH + "ad" + SLASH + EXAMPLE_AD_ID;
      for (Photo photo : photos) {
        Path filePath = Paths.get(folderPath, photo.getFilename());
        assertTrue(Files.exists(filePath));
      }
    }

    @Test
    void retrieveAllAdPhotos_returnList_whenPhotoExist() {
      // Setup: Store some photos for the ad
      MockMultipartFile file1 = createJPEGImage("image1");
      MockMultipartFile file2 = createJPEGImage("image2");
      MockMultipartFile[] files = {file1, file2};
      PhotoFilesDto photoFilesDto = new PhotoFilesDto(EXAMPLE_AD_ID, files);
      photoStorageService.storeAdPhotos(photoFilesDto);

      // Call the method
      FilesResponse response = photoStorageService.retrieveAllAdPhotos(EXAMPLE_AD_ID);

      // Assertions
      assertNotNull(response);
      assertNotNull(response.contents());
      assertEquals(2, response.contents().size());

      // Verify file contents are non-empty
      response.contents().forEach(fileContent -> assertNotEquals(0, fileContent.length));

      // Verify headers
      assertNotNull(response.headers());
      assertAll(
          () -> assertTrue(response.headers().containsKey(HttpHeaders.CONTENT_DISPOSITION)),
          () -> assertTrue(response.headers().containsKey(HttpHeaders.CONTENT_TYPE)));
    }

    @Test
    void retrieveAllAdPhotos_shouldReturnEmptyList_whenEmptyDirectory() {
      // Setup: Ensure the directory exists but is empty
      Path filename = Paths.get(testUploadDir, "ad", EXAMPLE_AD_ID.toString());
      try {
        Files.createDirectories(filename);
      } catch (IOException e) {
        fail("Failed to create directory for testing: " + filename);
      }

      // Call the method
      FilesResponse response = photoStorageService.retrieveAllAdPhotos(EXAMPLE_AD_ID);

      // Assertions
      assertNotNull(response);
      assertNotNull(response.contents());
      assertEquals(0, response.contents().size());
    }

    @Test
    void retrieveAllAdPhotos_shouldThrow_whenDirectoryDoesNotExist() {
      // Call the method and expect an exception
      assertThrows(
          FailedToListFilesInDirectoryException.class,
          () -> photoStorageService.retrieveAllAdPhotos(EXAMPLE_AD_ID));
    }

    @Test
    void retrieveAdPhoto_returnAdPhoto_whenExist() {
      // Setup: Store a photo for the ad
      MockMultipartFile file = createJPEGImage("image1");
      MockMultipartFile[] files = {file};
      PhotoFilesDto photoFilesDto = new PhotoFilesDto(EXAMPLE_AD_ID, files);
      List<Photo> photos = photoStorageService.storeAdPhotos(photoFilesDto);

      String filename = photos.getFirst().getFilename();

      // Call the method
      FileResponse response = photoStorageService.retrieveAdPhoto(EXAMPLE_AD_ID, filename);

      // Assertions
      assertNotNull(response);
      assertNotNull(response.content());
      assertNotEquals(0, response.content().length);

      // Verify headers
      assertNotNull(response.headers());
      assertAll(
          () -> assertTrue(response.headers().containsKey(HttpHeaders.CONTENT_DISPOSITION)),
          () -> assertTrue(response.headers().containsKey(HttpHeaders.CONTENT_TYPE)));
    }

    @Test
    void retrieveAdPhoto_shouldThrow_whenFileNotFound() {
      // Call the method and expect an exception
      String nonExistentFilename = "non-existent.jpg";
      assertThrows(
          PhotoFileNotFoundException.class,
          () -> photoStorageService.retrieveAdPhoto(EXAMPLE_AD_ID, nonExistentFilename));
    }

    @Test
    void retrieveAdPhoto_shouldThrow_whenDirectoryDoesNotExist() {
      // Call the method and expect an exception
      String filename = "image1.jpg";
      assertThrows(
          PhotoFileNotFoundException.class,
          () -> photoStorageService.retrieveAdPhoto(EXAMPLE_AD_ID, filename));
    }

    @Test
    public void deletePhotos_shouldDelete_Ad_whenValidName() {
      // Setup: Store photos for the ad
      MockMultipartFile file1 = createJPEGImage("image1");
      MockMultipartFile file2 = createJPEGImage("image2");
      MockMultipartFile[] files = {file1, file2};
      PhotoFilesDto photoFilesDto = new PhotoFilesDto(EXAMPLE_AD_ID, files);
      List<Photo> photos = photoStorageService.storeAdPhotos(photoFilesDto);

      String filename1 = photos.get(0).getFilename();
      String filename2 = photos.get(1).getFilename();

      AdPhotoPaths adPhotoPaths =
          new AdPhotoPaths(EXAMPLE_AD_ID, new String[] {filename1, filename2});

      when(adRepository.existsById(EXAMPLE_AD_ID)).thenReturn(true);

      // Call the method
      List<String> deletedFiles = photoStorageService.deleteAdPhotos(adPhotoPaths);

      // Assertions
      assertNotNull(deletedFiles);
      assertEquals(2, deletedFiles.size());

      // Verify file deletion in the upload directory
      deletedFiles.forEach(
          deletedFile -> {
            Path filePath = Paths.get(deletedFile);
            assertFalse(Files.exists(filePath));
          });
    }

    @Test
    public void deleteAdPhotos_shouldThrow_whenPhotoNotFound() {
      // Setup: Store one photo for the ad
      MockMultipartFile file = createJPEGImage("image1");
      MockMultipartFile[] files = {file};
      PhotoFilesDto photoFilesDto = new PhotoFilesDto(EXAMPLE_AD_ID, files);
      photoStorageService.storeAdPhotos(photoFilesDto);

      String filename1 = file.getOriginalFilename();
      String nonExistentFilename = "non-existent.jpg";

      AdPhotoPaths adPhotoPaths =
          new AdPhotoPaths(EXAMPLE_AD_ID, new String[] {filename1, nonExistentFilename});

      when(adRepository.existsById(EXAMPLE_AD_ID)).thenReturn(false);

      // Call the method and expect an exception
      assertThrows(AdNotFoundException.class, () -> photoStorageService.deleteAdPhotos(adPhotoPaths));
    }

    @Test
    public void deleteAdPhotos_shouldThrow_whenAdNotFound() {
      // Call the method and expect an exception
      Long nonExistentAdId = 999L;
      AdPhotoPaths adPhotoPaths = new AdPhotoPaths(nonExistentAdId, new String[] {"image1.jpg"});

      assertThrows(AdNotFoundException.class, () -> photoStorageService.deleteAdPhotos(adPhotoPaths));
    }

    private MockMultipartFile createJPEGImage(String name) {
      try {
        // Create a BufferedImage with example content
        BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setPaint(Color.BLUE);
        graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        graphics.setPaint(Color.RED);
        graphics.drawString(name, 10, 50);
        graphics.dispose();

        // Convert BufferedImage to byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        // Create and return the MockMultipartFile
        return new MockMultipartFile("file", name + ".jpg", "image/jpeg", imageBytes);
      } catch (IOException e) {
        throw new FailedRetrieveFileException(name, e);
      }
    }
  */

  private void createTempDirectory() {
    testUploadDir = System.getProperty("java.io.tmpdir") + "test-upload";
    this.uploadDir = testUploadDir;
    try {
      Files.createDirectories(Paths.get(testUploadDir));
    } catch (IOException e) {
      // Optionally, fail the test if directory creation fails
      fail("Failed to create temporary directory for testing: " + testUploadDir);
    }
    // Mock the behavior of @Value("${file.upload-dir}")
    ReflectionTestUtils.setField(photoStorageService, "uploadDir", testUploadDir);
  }

  private void deleteTempDirectory() {
    // Delete the temporary directory and its contents after each test
    if (testUploadDir != null) {
      Path directoryPath = Paths.get(testUploadDir);
      if (Files.exists(directoryPath)) {
        try (Stream<Path> paths = Files.walk(directoryPath)) {
          paths
              .sorted((a, b) -> -a.compareTo(b)) // Reverse order for directories
              .forEach(
                  file -> {
                    try {
                      Files.delete(file);
                    } catch (IOException e) {
                      throw new FailedRetrieveFileException(file.toAbsolutePath().toString(), e);
                    }
                  });
        } catch (IOException e) {
          // Handle IOException occurred in opening or closing stream
          throw new FailedRetrieveFileException(directoryPath.toAbsolutePath().toString(), e);
        }
      }
    }
  }
}
