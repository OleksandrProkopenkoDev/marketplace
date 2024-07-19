package ua.tc.marketplace.repository;

import java.nio.file.Path;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import ua.tc.marketplace.model.entity.Photo;

/**
 * Repository interface for handling file storage operations.
 *
 * <p>This interface defines methods for managing file operations such as creating directories,
 * writing files with associated metadata, reading files as byte arrays, deleting files, retrieving
 * the upload directory filename, and reading specific files by filename.
 */
public interface FileStorageRepository {

  void createDirectory(Path path);

  Photo writeFile(MultipartFile file, Path path);

  List<byte[]> readFilesList(Path path);

  byte[] readFile(Path filePath);

  void deleteFile(Path filePath);

  String getUploadDir();

  byte[] readFile(String filename, Path path);
}
