package ua.tc.marketplace.util.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ua.tc.marketplace.model.entity.Photo;

/**
 * This interface defines the OpenAPI annotations for the PhotoController class. It provides
 * endpoints for uploading, retrieving, downloading, and deleting photos related to advertisements.
 */
@Tag(name = "Photo API", description = "API for managing photos related to advertisements")
public interface PhotoOpenApi {

  @Operation(
      summary = "Upload photos for an advertisement",
      description = "Uploads multiple photos for a specific advertisement.")
  @PostMapping("/ad/{adId}")
  ResponseEntity<List<Photo>> saveAdPhotoFiles(
      @PathVariable Long adId, @RequestPart("files") MultipartFile[] files);

  @Operation(
      summary = "Delete specific photos for an advertisement",
      description =
          "Deletes multiple photos associated with a specific advertisement by providing their paths.")
  @DeleteMapping("/ad/{adId}")
  ResponseEntity<List<String>> deleteAdPhotosWithFiles(
      @RequestBody List<Long> photoIds, @PathVariable Long adId);

  @Operation(
      summary = "Retrieve all photos for an advertisement",
      description = "Retrieves a list of all photos associated with a specific advertisement.")
  @GetMapping("/ad/{adId}")
  ResponseEntity<List<Photo>> findAllPhotosByAdId(@PathVariable Long adId);

  @Operation(
      summary = "Upload a profile photo for a user",
      description = "Uploads a profile photo for a specific user.")
  @PostMapping("/user/{userId}")
  ResponseEntity<Photo> saveUserPhotoFile(
      @PathVariable Long userId, @RequestPart("file") MultipartFile file);

  @Operation(
      summary = "Delete a user's profile picture",
      description = "Deletes the profile picture of a specific user.")
  @DeleteMapping("/user/{userId}")
  ResponseEntity<String> deleteUserProfilePicture(@PathVariable Long userId);

  @Operation(
      summary = "Retrieve a user's profile picture",
      description = "Retrieves the profile picture of a specific user.")
  @GetMapping("/user/{userId}")
  ResponseEntity<Photo> findPhotoByUserId(@PathVariable Long userId);
}
