package ua.tc.marketplace.service;

import java.util.List;
import ua.tc.marketplace.model.dto.photo.AdPhotoPaths;
import ua.tc.marketplace.model.dto.photo.AdPhotosDto;
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

  List<Photo> storeAdPhotos(AdPhotosDto adPhotosDto);

  FilesResponse retrieveAllAdPhotos(Long adId);

  FileResponse retrieveAdPhoto(Long adId, String filename);

  List<String> deletePhotos(AdPhotoPaths adPhotoPaths);
}
