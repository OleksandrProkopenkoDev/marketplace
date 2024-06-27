package ua.tc.marketplace.service;

import org.springframework.web.multipart.MultipartFile;
import ua.tc.marketplace.model.dto.photo.FileResponse;

public interface FileStorageService {

  String storeFile(MultipartFile file);

  FileResponse retrieveFile(String filename);
}
