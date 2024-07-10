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
      summary = "Retrieve all photos for an advertisement",
      description = "Retrieves all photos associated with a specific advertisement by its ID.")
  @GetMapping("/ad/{adId}")
  ResponseEntity<List<byte[]>> retrieveAllPhotoFilesByAdId(@PathVariable Long adId);

  @Operation(
      summary = "Download a specific photo by filename",
      description =
          "Downloads a specific photo associated with an advertisement by the advertisement ID and filename.")
  @GetMapping("/ad/{adId}/{filename}")
  ResponseEntity<byte[]> retrieveAdPhotoFileByName(@PathVariable Long adId, @PathVariable String filename);
}
