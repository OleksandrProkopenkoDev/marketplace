package ua.tc.marketplace.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tc.marketplace.model.dto.photo.FileResponse;
import ua.tc.marketplace.model.dto.photo.FilesResponse;
import ua.tc.marketplace.service.PhotoStorageService;
import ua.tc.marketplace.util.openapi.PhotoFilesOpenApi;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/file")
public class PhotoFilesController implements PhotoFilesOpenApi {

  private final PhotoStorageService photoStorageService;

  @Override
  @GetMapping("/ad/{adId}")
  public ResponseEntity<List<byte[]>> findAllPhotoFilesByAdId(@PathVariable Long adId) {
    FilesResponse filesResponse = photoStorageService.findAllAdPhotoFiles(adId);
    return new ResponseEntity<>(filesResponse.contents(), filesResponse.headers(), HttpStatus.OK);
  }

  @Override
  @GetMapping("/ad/{adId}/{filename}")
  public ResponseEntity<byte[]> retrieveAdPhotoFileByName(
      @PathVariable Long adId, @PathVariable String filename) {
    FileResponse fileResponse = photoStorageService.findAdPhotoFileByName(adId, filename);
    return new ResponseEntity<>(fileResponse.content(), fileResponse.headers(), HttpStatus.OK);
  }

  @GetMapping("/user/{userId}/{filename}")
  public ResponseEntity<byte[]> retrieveUserPhotoFileByName(
      @PathVariable Long userId, @PathVariable String filename) {
    // todo
    return ResponseEntity.ok(new byte[10]);
  }
}
