package ua.tc.marketplace.repository.impl;

import jakarta.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import ua.tc.marketplace.exception.photo.FailedRetrieveFileException;
import ua.tc.marketplace.exception.photo.FailedStoreFileException;
import ua.tc.marketplace.exception.photo.FailedToListFilesInDirectoryException;
import ua.tc.marketplace.exception.photo.PhotoFileNotFoundException;
import ua.tc.marketplace.exception.photo.WrongFilePathException;
import ua.tc.marketplace.model.dto.photo.FileResponse;
import ua.tc.marketplace.model.entity.Photo;
import ua.tc.marketplace.model.entity.PhotoMetadata;
import ua.tc.marketplace.repository.FileStorageRepository;

@Getter
@Repository
public class FileStorageRepositoryImpl implements FileStorageRepository {

  private static final String DOT = ".";
  private static final String SLASH = File.separator;

  @Value("${file.upload-dir}")
  private String uploadDir;

  @Override
  public void createDirectory(Path path) {
    try {
      Files.createDirectories(path);
    } catch (IOException e) {
      throw new WrongFilePathException(path.toString(), e);
    }
  }

  @Override
  public Photo storeFile(MultipartFile file, Path path) {
    try {
      String originalFilename = file.getOriginalFilename();
      String extension = FilenameUtils.getExtension(originalFilename);
      String uniqueFilename = UUID.randomUUID() + DOT + extension;

      Path destinationFile = path.resolve(uniqueFilename);
      file.transferTo(destinationFile.toFile());

      ImageInfo imageInfo = Imaging.getImageInfo(destinationFile.toFile());

      int width = imageInfo.getWidth();
      int height = imageInfo.getHeight();
      float size = (float) file.getSize();

      PhotoMetadata metadata =
          PhotoMetadata.builder()
              .width(width)
              .height(height)
              .extension(extension)
              .size(size)
              .build();

      return Photo.builder().path(uniqueFilename).metadata(metadata).build();
    } catch (IOException e) {
      throw new FailedStoreFileException(file.getOriginalFilename(), e);
    }
  }

  @Override
  public List<byte[]> listFiles(Path path) {
    try (Stream<Path> paths = Files.list(path)) {
      return paths
          .filter(Files::isRegularFile)
          .map(this::readFileContent)
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new FailedToListFilesInDirectoryException(path.toString(), e);
    }
  }

  @Override
  public byte[] readFileContent(Path filePath) {
    try {
      return Files.readAllBytes(filePath);
    } catch (IOException e) {
      throw new FailedRetrieveFileException(filePath.toAbsolutePath().toString(), e);
    }
  }

  @Override
  public void deleteFile(Path filePath) {
    try {
      Files.delete(filePath);
    } catch (IOException e) {
      throw new FailedRetrieveFileException(filePath.toAbsolutePath().toString(), e);
    }
  }

  @Override
  @NotNull
  public HttpHeaders getHeaders(Path filePath) {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filePath.getFileName());
    try {
      headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(filePath));
    } catch (IOException e) {
      throw new PhotoFileNotFoundException(filePath.toString(), e);
    }
    return headers;
  }

  @Override
  public FileResponse retrieveFileWithHeaders(String filename, Path path) {
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
}
