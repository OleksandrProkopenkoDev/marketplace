package ua.tc.marketplace.repository;

import jakarta.validation.constraints.NotNull;
import java.nio.file.Path;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;
import ua.tc.marketplace.model.dto.photo.FileResponse;
import ua.tc.marketplace.model.entity.Photo;

public interface FileStorageRepository {

  void createDirectory(Path path);

  Photo storeFile(MultipartFile file, Path path);

  List<byte[]> listFiles(Path path);

  byte[] readFileContent(Path filePath);

  void deleteFile(Path filePath);

  @NotNull
  HttpHeaders getHeaders(Path filePath);

  String getUploadDir();

  FileResponse retrieveFileWithHeaders(String filename, Path path);
}
