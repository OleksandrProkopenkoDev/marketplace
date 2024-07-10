package ua.tc.marketplace.repository;

import java.nio.file.Path;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import ua.tc.marketplace.model.entity.Photo;

public interface FileStorageRepository {

  void createDirectory(Path path);

  Photo writeFile(MultipartFile file, Path path);

  List<byte[]> readFilesList(Path path);

  byte[] readFile(Path filePath);

  void deleteFile(Path filePath);


  String getUploadDir();

  byte[] readFile(String filename, Path path);
}
