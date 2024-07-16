package ua.tc.marketplace.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.tc.marketplace.model.entity.Photo;
import ua.tc.marketplace.service.PhotoStorageService;
import ua.tc.marketplace.util.openapi.PhotoOpenApi;

/**
 * Controller for handling photo-related operations.
 *
 * <p>This controller provides endpoints for saving, deleting, and retrieving photos for both
 * advertisements and user profiles. It leverages the {@link PhotoStorageService} for the actual
 * storage and retrieval logic.
 *
 * <p>Endpoints:
 *
 * <ul>
 *   <li>POST /api/v1/photo/ad/{adId}: Save photos for an advertisement
 *   <li>DELETE /api/v1/photo/ad/{adId}: Delete photos for an advertisement by photo IDs
 *   <li>GET /api/v1/photo/ad/{adId}: Retrieve all photos for an advertisement
 *   <li>POST /api/v1/photo/user/{userId}: Save a profile photo for a user
 *   <li>DELETE /api/v1/photo/user/{userId}: Delete a user's profile picture
 *   <li>GET /api/v1/photo/user/{userId}: Retrieve a user's profile picture
 * </ul>
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/photo")
public class PhotoController implements PhotoOpenApi {

  private final PhotoStorageService photoStorageService;

  @PostMapping("/ad/{adId}")
  public ResponseEntity<List<Photo>> saveAdPhotoFiles(
      @PathVariable Long adId, @RequestPart("files") MultipartFile[] files) {
    List<Photo> photos = photoStorageService.saveAdPhotos(adId, files);
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
