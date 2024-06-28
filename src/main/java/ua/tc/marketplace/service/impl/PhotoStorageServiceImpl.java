package ua.tc.marketplace.service.impl;

import jakarta.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import ua.tc.marketplace.exception.photo.FailedRetrieveFileException;
import ua.tc.marketplace.exception.photo.FailedStoreFileException;
import ua.tc.marketplace.exception.photo.FailedToListFilesInDirectoryException;
import ua.tc.marketplace.exception.photo.PhotoFileNotFoundException;
import ua.tc.marketplace.exception.photo.WrongFilePathException;
import ua.tc.marketplace.model.dto.photo.AdPhotos;
import ua.tc.marketplace.model.dto.photo.FileResponse;
import ua.tc.marketplace.model.dto.photo.FilesResponse;
import ua.tc.marketplace.model.entity.Photo;
import ua.tc.marketplace.model.entity.PhotoMetadata;
import ua.tc.marketplace.service.PhotoStorageService;

@Service
public class PhotoStorageServiceImpl implements PhotoStorageService {

  public static final String AD = "ad";
  public static final String DOT = ".";
  public static final String SLASH = File.separator;

  @Value("${file.upload-dir}")
  private String uploadDir;

  @Override
  public List<Photo> storeAdPhotos(AdPhotos adPhotos) {
    String folder = AD + SLASH + adPhotos.adId();
    Path path = Paths.get(uploadDir).resolve(folder);
    // Ensure the upload directory exists
    try {
      Files.createDirectories(path);
    } catch (IOException e) {
      throw new WrongFilePathException(path.toString(), e);
    }
    List<Photo> photos = new ArrayList<>();

    Arrays.stream(adPhotos.files())
        .forEach(
            file -> {
              try {
                // Generate a unique filename
                String originalFilename = file.getOriginalFilename();
                String extension = FilenameUtils.getExtension(originalFilename);
                String uniqueFilename = UUID.randomUUID() + DOT + extension;

                // Save the file
                Path destinationFile = path.resolve(uniqueFilename);
                file.transferTo(destinationFile.toFile());

                // Extract metadata using Apache Commons Imaging
                ImageInfo imageInfo = Imaging.getImageInfo(destinationFile.toFile());

                int width = imageInfo.getWidth();
                int height = imageInfo.getHeight();
                float size = (float) file.getSize();

                // Create PhotoMetadata
                PhotoMetadata metadata =
                    PhotoMetadata.builder()
                        .width(width)
                        .height(height)
                        .extension(extension)
                        .size(size)
                        .build();

                // Create Photo
                Photo photo =
                    Photo.builder()
                        .path(folder + SLASH + uniqueFilename)
                        .metadata(metadata)
                        .build();
                photos.add(photo);
              } catch (IOException e) {
                throw new FailedStoreFileException(file.getOriginalFilename(), e);
              }
            });
    return photos;
  }

  @Override
  public FilesResponse retrieveAllAdPhotos(Long adId) {
    String currentFolder = AD + SLASH + adId;
    Path path = Paths.get(uploadDir).resolve(currentFolder);
    List<byte[]> fileContents;

    try (Stream<Path> paths = Files.list(path)) {
      fileContents =
          paths
              .filter(Files::isRegularFile)
              .map(this::readFileContent)
              .collect(Collectors.toList());
    } catch (IOException e) {
      throw new FailedToListFilesInDirectoryException(path.toString(), e);
    }
    HttpHeaders headers = getHeaders(path);
    return new FilesResponse(fileContents, headers);
  }

  @Override
  public FileResponse retrieveAdPhoto(Long adId, String filename) {
    String folder = AD + SLASH + adId;
    Path path = Paths.get(uploadDir).resolve(folder);

    return getFileResponse(filename, path);
  }

  @NotNull
  private FileResponse getFileResponse(String filename, Path path) {
    try {
      Path filePath = path.resolve(filename);
      if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
        throw new PhotoFileNotFoundException(filename);
      }

      byte[] fileContent = Files.readAllBytes(filePath);

      HttpHeaders headers = getHeaders(filePath);

      return new FileResponse(fileContent, headers);
    } catch (IOException e) {
      throw new FailedRetrieveFileException(filename, e);
    }
  }

  private HttpHeaders getHeaders(Path filePath) {
    // Adding headers for the current file processed
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filePath.getFileName());
    try {
      headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(filePath));
    } catch (IOException e) {
      throw new PhotoFileNotFoundException(filePath.toString(), e);
    }
    return headers;
  }

  private byte[] readFileContent(Path filePath) {
    try {
      return Files.readAllBytes(filePath);
    } catch (IOException e) {
      throw new FailedRetrieveFileException(filePath.toAbsolutePath().toString(), e);
    }
  }
}
