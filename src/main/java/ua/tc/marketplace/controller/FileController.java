package ua.tc.marketplace.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.tc.marketplace.model.dto.photo.FileResponse;
import ua.tc.marketplace.service.FileStorageService;

@RestController
@RequestMapping("/api/v1/file")
public class FileController {

  private final FileStorageService fileStorageService;

  public FileController(FileStorageService fileStorageService) {
    this.fileStorageService = fileStorageService;
  }

  @PostMapping("/upload")
  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
    String filename = fileStorageService.storeFile(file);
    return ResponseEntity.ok(filename);
  }

  @GetMapping("/download/{filename}")
  public ResponseEntity<byte[]> downloadFile(@PathVariable String filename) {
    FileResponse fileResponse = fileStorageService.retrieveFile(filename);
    return new ResponseEntity<>(fileResponse.content(), fileResponse.headers(), HttpStatus.OK);
  }
}
