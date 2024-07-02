package ua.tc.marketplace.util.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ua.tc.marketplace.model.dto.photo.AdPhotoPaths;
import ua.tc.marketplace.model.dto.photo.AdPhotos;
import ua.tc.marketplace.model.entity.Photo;

/**
 * This interface defines the OpenAPI annotations for the PhotoController class.
 * It provides endpoints for uploading, retrieving, downloading, and deleting photos related to advertisements.
 */
@Tag(name = "Photo API", description = "API for managing photos related to advertisements")
public interface PhotoOpenApi {

  @Operation(
      summary = "Upload photos for an advertisement",
      description = "Uploads multiple photos for a specific advertisement."
  )
  @PostMapping("/ad")
  ResponseEntity<List<Photo>> uploadFile(@ModelAttribute AdPhotos adPhotos);

  @Operation(
      summary = "Retrieve all photos for an advertisement",
      description = "Retrieves all photos associated with a specific advertisement by its ID."
  )
  @GetMapping("/ad/{adId}")
  ResponseEntity<List<byte[]>> retrieveAllPhotos(@PathVariable Long adId);

  @Operation(
      summary = "Download a specific photo by filename",
      description = "Downloads a specific photo associated with an advertisement by the advertisement ID and filename."
  )
  @GetMapping("/ad/{adId}/{filename}")
  ResponseEntity<byte[]> downloadFile(@PathVariable Long adId, @PathVariable String filename);

  @Operation(
      summary = "Delete specific photos for an advertisement",
      description = "Deletes multiple photos associated with a specific advertisement by providing their paths."
  )
  @DeleteMapping("/ad")
  ResponseEntity<List<String>> deletePhotos(@RequestBody AdPhotoPaths adPhotoPaths);
}
