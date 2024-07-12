package ua.tc.marketplace.util.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ua.tc.marketplace.model.dto.photo.AdPhotoPaths;
import ua.tc.marketplace.model.dto.photo.PhotoFilesDto;
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
  @PostMapping("/ad")
  ResponseEntity<List<Photo>> saveAdPhotoFiles(@ModelAttribute PhotoFilesDto photoFilesDto);

  @Operation(
      summary = "Delete specific photos for an advertisement",
      description =
          "Deletes multiple photos associated with a specific advertisement by providing their paths.")
  @DeleteMapping("/ad/{adId}")
  ResponseEntity<List<String>> deleteAdPhotosWithFiles(
      @RequestBody List<Long> photoIds, @PathVariable Long adId);
}
