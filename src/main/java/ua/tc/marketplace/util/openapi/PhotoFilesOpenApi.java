package ua.tc.marketplace.util.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * This interface defines the OpenAPI annotations for the PhotoController class. It provides
 * endpoints for uploading, retrieving, downloading, and deleting photos related to advertisements.
 */
@Tag(name = "Photo API", description = "API for managing photos related to advertisements")
public interface PhotoFilesOpenApi {

  @Operation(
      summary = "Retrieve all photo files for an advertisement",
      description = "Retrieves all photos associated with a specific advertisement by its ID.")
  @GetMapping("/ad/{adId}")
  ResponseEntity<List<byte[]>> findAllPhotoFilesByAdId(@PathVariable Long adId);

  @Operation(
      summary = "Download a specific photo by photoId",
      description =
          "Downloads a specific photo associated with an advertisement by the advertisement ID and photoId.")
  @GetMapping("/ad/{adId}/photo/{photoId}")
  ResponseEntity<byte[]> findFileByPhotoId(@PathVariable Long adId, @PathVariable Long photoId);

  @Operation(
      summary = "Download a file of User's profile picture",
      description =
          "Downloads a specific photo associated with an advertisement by the advertisement ID and filename.")
  @GetMapping("/user/{userId}")
  ResponseEntity<byte[]> findUserProfilePictureFile(@PathVariable Long userId);
}
