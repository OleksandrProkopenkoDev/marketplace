package ua.tc.marketplace.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/v1/photo")
public class PhotoFilesController implements PhotoFilesOpenApi {

  private final PhotoStorageService fileStorageService;

  @Override
  @GetMapping("/ad/{adId}")
  public ResponseEntity<List<byte[]>> retrieveAllPhotoFilesByAdId(@PathVariable Long adId) {
    FilesResponse filesResponse = fileStorageService.retrieveAllAdPhotos(adId);
    return new ResponseEntity<>(filesResponse.contents(), filesResponse.headers(), HttpStatus.OK);
  }

  @Override
  @GetMapping("/ad/{adId}/{filename}")
  public ResponseEntity<byte[]> retrieveAdPhotoFileByName(
      @PathVariable Long adId, @PathVariable String filename) {
    FileResponse fileResponse = fileStorageService.retrieveAdPhoto(adId, filename);
    return new ResponseEntity<>(fileResponse.content(), fileResponse.headers(), HttpStatus.OK);
  }

  @GetMapping("/user/{userId}/{filename}")
  public ResponseEntity<byte[]> retrieveUserPhotoFileByName(
      @PathVariable Long userId, @PathVariable String filename) {
    // todo
    return ResponseEntity.ok(new byte[10]);
  }
}
