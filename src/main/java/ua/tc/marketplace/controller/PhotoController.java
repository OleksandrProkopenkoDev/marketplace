package ua.tc.marketplace.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tc.marketplace.model.dto.photo.AdPhotoPaths;
import ua.tc.marketplace.model.dto.photo.PhotoFilesDto;
import ua.tc.marketplace.model.entity.Photo;
import ua.tc.marketplace.service.PhotoStorageService;
import ua.tc.marketplace.util.openapi.PhotoOpenApi;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/photo")
public class PhotoController implements PhotoOpenApi {

  private final PhotoStorageService photoStorageService;

  @PostMapping("/ad")
  public ResponseEntity<List<Photo>> saveAdPhotoFiles(
      @ModelAttribute @Valid PhotoFilesDto photoFilesDto) {
    List<Photo> photos = photoStorageService.saveAdPhotos(photoFilesDto);
    return ResponseEntity.ok(photos);
  }

  @Override
  @DeleteMapping("/ad")
  public ResponseEntity<List<String>> deleteAdPhotoFiles(
      @RequestBody @Valid AdPhotoPaths adPhotoPaths) {
    List<String> deletedFiles = photoStorageService.deleteAdPhotos(adPhotoPaths);
    return ResponseEntity.ok(deletedFiles);
  }

  @GetMapping("/ad/{adId}")
  public ResponseEntity<List<Photo>> findAllPhotosByAdId(@PathVariable Long adId) {
    List<Photo> photos = photoStorageService.findAllPhotosByAdId(adId);
    return ResponseEntity.ok(photos);
  }

  @PostMapping("/user")
  public ResponseEntity<List<Photo>> saveUserPhotoFile(
      @ModelAttribute @Valid PhotoFilesDto photoFilesDto) {
    List<Photo> photos = photoStorageService.saveAdPhotos(photoFilesDto);
    return ResponseEntity.ok(photos);
  }

  @DeleteMapping("/user")
  public ResponseEntity<List<String>> deleteUserPhotoFiles(
      @RequestBody @Valid AdPhotoPaths adPhotoPaths) {
    List<String> deletedFiles = photoStorageService.deleteAdPhotos(adPhotoPaths);
    return ResponseEntity.ok(deletedFiles);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<Photo> findPhotoByUserId(@PathVariable Long userId) {
    // todo
    return ResponseEntity.ok(new Photo());
  }
}
