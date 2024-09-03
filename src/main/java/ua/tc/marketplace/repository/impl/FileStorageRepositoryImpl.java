package ua.tc.marketplace.repository.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import ua.tc.marketplace.exception.photo.FailedRetrieveFileException;
import ua.tc.marketplace.exception.photo.FailedStoreFileException;
import ua.tc.marketplace.exception.photo.FailedToListFilesInDirectoryException;
import ua.tc.marketplace.exception.photo.PhotoFileNotFoundException;
import ua.tc.marketplace.exception.photo.WrongFilePathException;
import ua.tc.marketplace.model.entity.Photo;
import ua.tc.marketplace.model.entity.PhotoMetadata;
import ua.tc.marketplace.repository.FileStorageRepository;

/**
 * Implementation of {@link FileStorageRepository} that provides methods to handle file storage
 * operations.
 *
 * <p>This repository manages file storage operations such as creating directories, writing files
 * with metadata, reading files as byte arrays, and deleting files.
 *
 * <p>It uses Apache Commons Imaging library for extracting image metadata and Apache Commons IO for
 * file operations.
 */
@Slf4j
@Getter
@Setter
@Repository
public class FileStorageRepositoryImpl implements FileStorageRepository {

  private static final String DOT = ".";
  private static final String SLASH = File.separator;

  @Value("${file.upload-dir}")
  private String uploadDir;

  @Override
  public void deleteFolder(Path basePath) {
    try {
      Files.walkFileTree(basePath, getVisitor());
    } catch (IOException e) {
      throw new WrongFilePathException(basePath.toString(), e);
    }
  }

  @Override
  public void createDirectory(Path path) {
    try {
      Files.createDirectories(path);
    } catch (IOException e) {
      throw new WrongFilePathException(path.toString(), e);
    }
  }


  @Override
  public Photo writeFile(MultipartFile file, Path path) {
    try {
      String originalFilename = file.getOriginalFilename();
      String extension = FilenameUtils.getExtension(originalFilename);
      String uniqueFilename = UUID.randomUUID() + DOT + extension;

      File destinationFile = path.resolve(uniqueFilename).toFile();
      file.transferTo(destinationFile);

      ImageInfo imageInfo = Imaging.getImageInfo(destinationFile);

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

      return Photo.builder().filename(uniqueFilename).metadata(metadata).build();
    } catch (IOException e) {
      throw new FailedStoreFileException(file.getOriginalFilename(), e);
    }
  }

  @Override
  public List<byte[]> readFilesList(Path path) {
    try (Stream<Path> paths = Files.list(path)) {
      return paths.filter(Files::isRegularFile).map(this::readFile).collect(Collectors.toList());
    } catch (IOException e) {
      throw new FailedToListFilesInDirectoryException(path.toString(), e);
    }
  }

  @Override
  public byte[] readFile(Path filePath) {
    try {
      return Files.readAllBytes(filePath);
    } catch (IOException e) {
      throw new FailedRetrieveFileException(filePath.toAbsolutePath().toString(), e);
    }
  }

  @Override
  public byte[] readFile(String filename, Path path) {
    try {
      Path filePath = path.resolve(filename);
      if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
        throw new PhotoFileNotFoundException(filename);
      }
      return Files.readAllBytes(filePath);
    } catch (IOException e) {
      throw new FailedRetrieveFileException(filename, e);
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

  private SimpleFileVisitor<Path> getVisitor() {
    return new SimpleFileVisitor<>() {
      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Files.delete(file);
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        Files.delete(dir);
        return FileVisitResult.CONTINUE;
      }
    };
  }
}
