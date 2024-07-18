package ua.tc.marketplace.service.impl;

import jakarta.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ua.tc.marketplace.exception.ad.AdNotFoundException;
import ua.tc.marketplace.exception.photo.PhotoFileNotFoundException;
import ua.tc.marketplace.exception.photo.PhotoNotFoundException;
import ua.tc.marketplace.exception.user.UserNotFoundException;
import ua.tc.marketplace.model.dto.photo.FileResponse;
import ua.tc.marketplace.model.dto.photo.FilesResponse;
import ua.tc.marketplace.model.entity.Ad;
import ua.tc.marketplace.model.entity.Photo;
import ua.tc.marketplace.model.entity.User;
import ua.tc.marketplace.repository.AdRepository;
import ua.tc.marketplace.repository.FileStorageRepository;
import ua.tc.marketplace.repository.PhotoRepository;
import ua.tc.marketplace.repository.UserRepository;
import ua.tc.marketplace.service.PhotoStorageService;

/**
 * Implementation of {@link PhotoStorageService} interface for managing photo storage related
 * operations.
 *
 * <p>This service provides methods for saving and retrieving photos associated with users and
 * advertisements. It handles operations such as saving user profile pictures, saving advertisement
 * photos, retrieving user profile pictures and advertisement photos, as well as deleting user
 * profile pictures and advertisement photos.
 */
@Transactional
@Service
@RequiredArgsConstructor
public class PhotoStorageServiceImpl implements PhotoStorageService {

  private final AdRepository adRepository;
  private final UserRepository userRepository;
  private final FileStorageRepository fileStorageRepository;
  private final PhotoRepository photoRepository;

  private static final String USER = "user";
  private static final String AD = "ad";
  private static final String SLASH = File.separator;

  @Override
  public Photo saveUserPhoto(Long userId, MultipartFile file) {
    User user = findUserById(userId);

    if (user.getProfilePicture() != null) {
      deleteUserProfilePicture(userId);
    }

    String folder = getUserFolder(userId);
    Path path = getPath(folder);
    fileStorageRepository.createDirectory(path);

    Photo photo = fileStorageRepository.writeFile(file, path);

    user.setProfilePicture(photo);
    user = userRepository.save(user);
    return user.getProfilePicture();
  }

  @Transactional(readOnly = true)
  @Override
  public Photo findUserProfilePicture(Long userId) {
    User user = findUserById(userId);
    return user.getProfilePicture();
  }

  @Override
  public List<Photo> saveAdPhotos(Long adId, MultipartFile[] files) {
    Ad ad = findAdById(adId);

    String folder = getAdFolder(adId);
    Path path = getPath(folder);
    fileStorageRepository.createDirectory(path);

    List<Photo> photos =
        Arrays.stream(files).map(file -> fileStorageRepository.writeFile(file, path)).toList();

    ad.getPhotos().addAll(photos);
    if (!photos.isEmpty()) {
      ad.setThumbnail(photos.getFirst());
    }
    ad = adRepository.save(ad);
    return ad.getPhotos();
  }

  @Transactional(readOnly = true)
  @Override
  public List<Photo> findAllPhotosByAdId(Long adId) {
    Ad ad = findAdById(adId);
    return ad.getPhotos();
  }

  @Transactional(readOnly = true)
  @Override
  public FilesResponse findAllAdPhotoFiles(Long adId) {
    String folder = getAdFolder(adId);
    Path path = getPath(folder);
    List<byte[]> fileContents = fileStorageRepository.readFilesList(path);
    return new FilesResponse(fileContents, getHeaders(path));
  }

  @Transactional(readOnly = true)
  @Override
  public FileResponse findUserProfilePictureFile(Long userId) {
    User user = findUserById(userId);
    String folder = getUserFolder(userId);
    Path path = getPath(folder);
    byte[] bytes = new byte[0];
    if (user.getProfilePicture() != null) {
      bytes = fileStorageRepository.readFile(user.getProfilePicture().getFilename(), path);
    }
    return new FileResponse(bytes, getHeaders(path));
  }

  @Transactional(readOnly = true)
  @Override
  public FileResponse findAdPhotoFileById(Long adId, Long photoId) {
    Photo photo = findPhotoById(photoId);

    String folder = getAdFolder(adId);
    Path path = getPath(folder);

    byte[] bytes = fileStorageRepository.readFile(photo.getFilename(), path);
    return new FileResponse(bytes, getHeaders(path));
  }

  @Override
  public String deleteUserProfilePicture(Long userId) {
    String folder = getUserFolder(userId);

    User user = findUserById(userId);

    Photo photo = user.getProfilePicture();

    user.setProfilePicture(null);

    photoRepository.delete(photo);
    userRepository.save(user);

    Path filePath = Paths.get(fileStorageRepository.getUploadDir(), folder, photo.getFilename());

    if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
      fileStorageRepository.deleteFile(filePath); // Perform the delete action here
      return filePath.toString();
    } else {
      throw new PhotoFileNotFoundException(filePath.toString());
    }
  }

  @Override
  public void deleteAllAdPhotos(Ad ad) {
    String folder = getAdFolder(ad.getId());
    Path basePath = getPath(folder);
    fileStorageRepository.deleteFolder(basePath);
  }

  @Override
  public List<String> deleteAdPhotos(Long adId, List<Long> photoIds) {
    String folder = getAdFolder(adId);
    Path basePath = getPath(folder);

    Ad ad = findAdById(adId);

    List<Photo> photos = ad.getPhotos();

    List<Photo> photosToDelete =
        photos.stream().filter(photo -> photoIds.contains(photo.getId())).toList();

    ad.getPhotos().removeAll(photosToDelete);
    boolean deleteThumbnail = false;
    if (ad.getThumbnail() != null) {
      deleteThumbnail = photosToDelete.stream().anyMatch(photo -> ad.getThumbnail().equals(photo));
    }
    if (deleteThumbnail) {
      ad.setThumbnail(null);
    }
    adRepository.save(ad);
    photoRepository.deleteAll(photosToDelete);
    if (!ad.getPhotos().isEmpty() && deleteThumbnail) {
      ad.setThumbnail(ad.getPhotos().getFirst());
    }

    List<String> paths =
        photosToDelete.stream()
            .map(
                photo ->
                    fileStorageRepository.getUploadDir()
                        + SLASH
                        + folder
                        + SLASH
                        + photo.getFilename())
            .toList();

    return paths.stream()
        .map(basePath::resolve)
        .map(
            filePath -> {
              if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
                fileStorageRepository.deleteFile(filePath); // Perform the delete action here
                return filePath;
              } else {
                throw new PhotoFileNotFoundException(filePath.toString());
              }
            })
        .map(Path::toString)
        .toList();
  }

  private Ad findAdById(Long adId) {
    return adRepository.findById(adId).orElseThrow(() -> new AdNotFoundException(adId));
  }

  @NotNull
  private HttpHeaders getHeaders(Path filePath) {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filePath.getFileName());
    try {
      headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(filePath));
    } catch (IOException e) {
      throw new PhotoFileNotFoundException(filePath.toString(), e);
    }
    return headers;
  }

  private User findUserById(Long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
  }

  private Photo findPhotoById(Long photoId) {
    return photoRepository.findById(photoId).orElseThrow(() -> new PhotoNotFoundException(photoId));
  }

  private String getAdFolder(Long adId) {
    return AD + SLASH + adId;
  }

  private String getUserFolder(Long userId) {
    return USER + SLASH + userId;
  }

  private Path getPath(String folder) {
    return Paths.get(fileStorageRepository.getUploadDir()).resolve(folder);
  }
}
