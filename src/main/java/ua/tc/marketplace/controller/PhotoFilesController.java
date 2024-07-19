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

/**
 * Controller for handling photo file-related operations.
 *
 * <p>This controller provides endpoints for retrieving photo files associated
 * with advertisements and user profiles. The file data is returned in byte arrays.
 * It leverages the {@link PhotoStorageService} for the actual file retrieval logic.
 *
 * <p>Endpoints:
 * <ul>
 *   <li>GET /api/v1/file/ad/{adId}: Retrieve all photo files for an advertisement</li>
 *   <li>GET /api/v1/file/ad/{adId}/photo/{photoId}: Retrieve a specific photo file for an advertisement by photo ID</li>
 *   <li>GET /api/v1/file/user/{userId}: Retrieve a user's profile picture file</li>
 * </ul>
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/file")
public class PhotoFilesController implements PhotoFilesOpenApi {

  private final PhotoStorageService photoStorageService;

  @Override
  @GetMapping("/ad/{adId}")
  public ResponseEntity<List<byte[]>> findAllPhotoFilesByAdId(@PathVariable Long adId) {
    FilesResponse filesResponse = photoStorageService.findAllAdPhotoFiles(adId);
    return new ResponseEntity<>(filesResponse.contents(), filesResponse.headers(), HttpStatus.OK);
  }

  @Override
  @GetMapping("/ad/{adId}/photo/{photoId}")
  public ResponseEntity<byte[]> findFileByPhotoId(
      @PathVariable Long adId, @PathVariable Long photoId) {
    FileResponse fileResponse = photoStorageService.findAdPhotoFileById(adId, photoId);
    return new ResponseEntity<>(fileResponse.content(), fileResponse.headers(), HttpStatus.OK);
  }

  @Override
  @GetMapping("/user/{userId}")
  public ResponseEntity<byte[]> findUserProfilePictureFile(@PathVariable Long userId) {
    FileResponse fileResponse = photoStorageService.findUserProfilePictureFile(userId);
    return new ResponseEntity<>(fileResponse.content(), fileResponse.headers(), HttpStatus.OK);
  }
}
