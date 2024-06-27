package ua.tc.marketplace.service.impl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import ua.tc.marketplace.exception.photo.FailedRetrieveFileException;
import ua.tc.marketplace.exception.photo.FailedStoreFileException;
import ua.tc.marketplace.exception.photo.PhotoFileNotFoundException;
import ua.tc.marketplace.model.dto.photo.FileResponse;
import ua.tc.marketplace.service.FileStorageService;

@Service
public class FileStorageServiceImpl implements FileStorageService {

  @Value("${file.upload-dir}")
  private String uploadDir;

  @Override
  public String storeFile(MultipartFile file) {
    try {
      // Ensure the upload directory exists
      Files.createDirectories(Paths.get(uploadDir));

      // Generate a unique filename
      String originalFilename = file.getOriginalFilename();
      String extension = FilenameUtils.getExtension(originalFilename);
      String uniqueFilename = UUID.randomUUID() + "." + extension;

      // Save the file
      File destinationFile = new File(uploadDir, uniqueFilename);
      FileUtils.copyInputStreamToFile(file.getInputStream(), destinationFile);

      return uniqueFilename;
    } catch (IOException e) {
      throw new FailedStoreFileException(file.getOriginalFilename(), e);
    }
  }

  @Override
  public FileResponse retrieveFile(String filename) {
    try {
      File file = new File(uploadDir, filename);
      if (!file.exists() || !file.isFile()) {
        throw new PhotoFileNotFoundException(filename);
      }

      byte[] fileContent = Files.readAllBytes(file.toPath());

      HttpHeaders headers = new HttpHeaders();
      headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
      headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(file.toPath()));

      return new FileResponse(fileContent, headers);
    } catch (IOException e) {
      throw new FailedRetrieveFileException(filename, e);
    }
  }
}

