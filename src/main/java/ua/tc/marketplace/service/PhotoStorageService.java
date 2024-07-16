package ua.tc.marketplace.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import ua.tc.marketplace.model.dto.photo.PhotoFilesDto;
import ua.tc.marketplace.model.dto.photo.FileResponse;
import ua.tc.marketplace.model.dto.photo.FilesResponse;
import ua.tc.marketplace.model.entity.Photo;

/**
 * Service interface for managing photo storage operations.
 *
 * <p>This interface defines methods for storing, retrieving, and deleting photos associated with
 * advertisements. It provides functionality to store multiple photos, retrieve all photos or a
 * specific photo by its filename, and delete photos based on their paths.
 */
public interface PhotoStorageService {

  Photo saveUserPhoto(Long userId, MultipartFile file);

  Photo findUserProfilePicture(Long userId);

  List<Photo> saveAdPhotos(PhotoFilesDto photoFilesDto);

  List<Photo> findAllPhotosByAdId(Long adId);

  FilesResponse findAllAdPhotoFiles(Long adId);

  FileResponse findUserProfilePictureFile(Long userId);

  FileResponse findAdPhotoFileByName(Long adId, Long filename);

  String deleteUserProfilePicture(Long userId);

  List<String> deleteAdPhotos(Long adId, List<Long> photoIds);
}
