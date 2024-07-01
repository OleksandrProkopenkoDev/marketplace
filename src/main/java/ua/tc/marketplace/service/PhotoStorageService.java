package ua.tc.marketplace.service;

import java.util.List;
import ua.tc.marketplace.model.dto.photo.AdPhotos;
import ua.tc.marketplace.model.dto.photo.AdPhotoPaths;
import ua.tc.marketplace.model.dto.photo.FileResponse;
import ua.tc.marketplace.model.dto.photo.FilesResponse;
import ua.tc.marketplace.model.entity.Photo;

public interface PhotoStorageService {

  List<Photo> storeAdPhotos(AdPhotos adPhotos);

  FilesResponse retrieveAllAdPhotos(Long adId);

  FileResponse retrieveAdPhoto(Long adId, String filename);

  FilesResponse addPhotos(AdPhotos adPhotos);

  FilesResponse deletePhotos(AdPhotoPaths adPhotoPaths);
}
