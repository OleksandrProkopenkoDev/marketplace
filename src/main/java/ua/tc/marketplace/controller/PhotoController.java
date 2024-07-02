package ua.tc.marketplace.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tc.marketplace.model.dto.photo.AdPhotoPaths;
import ua.tc.marketplace.model.dto.photo.AdPhotos;
import ua.tc.marketplace.model.dto.photo.FileResponse;
import ua.tc.marketplace.model.dto.photo.FilesResponse;
import ua.tc.marketplace.model.entity.Photo;
import ua.tc.marketplace.service.PhotoStorageService;
import ua.tc.marketplace.util.openapi.PhotoOpenApi;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/photo")
public class PhotoController implements PhotoOpenApi {

  private final PhotoStorageService fileStorageService;

  @Override
  @PostMapping("/ad")
  public ResponseEntity<List<Photo>> uploadFile(@ModelAttribute @Valid AdPhotos adPhotos) {
    List<Photo> photos = fileStorageService.storeAdPhotos(adPhotos);
    return ResponseEntity.ok(photos);
  }

  @Override
  @GetMapping("/ad/{adId}")
  public ResponseEntity<List<byte[]>> retrieveAllPhotos(@PathVariable Long adId) {
    FilesResponse filesResponse = fileStorageService.retrieveAllAdPhotos(adId);
    return new ResponseEntity<>(filesResponse.contents(), filesResponse.headers(), HttpStatus.OK);
  }

  @Override
  @GetMapping("/ad/{adId}/{filename}")
  public ResponseEntity<byte[]> downloadFile(
      @PathVariable Long adId, @PathVariable String filename) {
    FileResponse fileResponse = fileStorageService.retrieveAdPhoto(adId, filename);
    return new ResponseEntity<>(fileResponse.content(), fileResponse.headers(), HttpStatus.OK);
  }

  @Override
  @DeleteMapping("/ad")
  public ResponseEntity<List<String>> deletePhotos(@RequestBody @Valid AdPhotoPaths adPhotoPaths) {
    List<String> deletedFiles = fileStorageService.deletePhotos(adPhotoPaths);
    return ResponseEntity.ok(deletedFiles);
  }
}
