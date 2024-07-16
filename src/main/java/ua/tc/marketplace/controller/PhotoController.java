package ua.tc.marketplace.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
  @DeleteMapping("/ad/{adId}")
  public ResponseEntity<List<String>> deleteAdPhotosWithFiles(
      @RequestBody List<Long> photoIds, @PathVariable Long adId) {
    List<String> deletedFiles = photoStorageService.deleteAdPhotos(adId, photoIds);
    return ResponseEntity.ok(deletedFiles);
  }

  @GetMapping("/ad/{adId}")
  public ResponseEntity<List<Photo>> findAllPhotosByAdId(@PathVariable Long adId) {
    List<Photo> photos = photoStorageService.findAllPhotosByAdId(adId);
    return ResponseEntity.ok(photos);
  }

  @PostMapping("/user/{userId}")
  public ResponseEntity<Photo> saveUserPhotoFile(
      @PathVariable Long userId, @RequestPart("file") MultipartFile file) {
    Photo photo = photoStorageService.saveUserPhoto(userId, file);
    return ResponseEntity.ok(photo);
  }

  @DeleteMapping("/user/{userId}")
  public ResponseEntity<String> deleteUserProfilePicture(@PathVariable Long userId) {
    String deleted = photoStorageService.deleteUserProfilePicture(userId);
    return ResponseEntity.ok(deleted);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<Photo> findPhotoByUserId(@PathVariable Long userId) {
    Photo userProfilePicture = photoStorageService.findUserProfilePicture(userId);
    return ResponseEntity.ok(userProfilePicture);
  }
}
