package ua.tc.marketplace.repository;

import java.nio.file.Path;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import ua.tc.marketplace.model.entity.Photo;

public interface FileStorageRepository {

  void createDirectory(Path path);

  Photo storeFile(MultipartFile file, Path path);

  List<byte[]> listFiles(Path path);

  byte[] readFileContent(Path filePath);

  void deleteFile(Path filePath);


  String getUploadDir();

  byte[] retrieveFile(String filename, Path path);
}
