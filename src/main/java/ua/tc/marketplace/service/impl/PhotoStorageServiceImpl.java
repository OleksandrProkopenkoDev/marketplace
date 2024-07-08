package ua.tc.marketplace.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.tc.marketplace.exception.ad.AdNotFoundException;
import ua.tc.marketplace.exception.photo.PhotoFileNotFoundException;
import ua.tc.marketplace.model.dto.photo.AdPhotoPaths;
import ua.tc.marketplace.model.dto.photo.AdPhotos;
import ua.tc.marketplace.model.dto.photo.FileResponse;
import ua.tc.marketplace.model.dto.photo.FilesResponse;
import ua.tc.marketplace.model.entity.Photo;
import ua.tc.marketplace.repository.AdRepository;
import ua.tc.marketplace.repository.FileStorageRepository;
import ua.tc.marketplace.service.PhotoStorageService;

@Service
@RequiredArgsConstructor
public class PhotoStorageServiceImpl implements PhotoStorageService {

  private final AdRepository adRepository;
  private final FileStorageRepository fileStorageRepository;

  private static final String AD = "ad";
  private static final String SLASH = File.separator;

  @Override
  public List<Photo> storeAdPhotos(AdPhotos adPhotos) {
    String folder = AD + SLASH + adPhotos.adId();
    Path path = Paths.get(fileStorageRepository.getUploadDir()).resolve(folder);
    fileStorageRepository.createDirectory(path);

    return Arrays.stream(adPhotos.files())
        .map(file -> fileStorageRepository.storeFile(file, path))
        .collect(Collectors.toList());
  }

  @Override
  public FilesResponse retrieveAllAdPhotos(Long adId) {
    String currentFolder = AD + SLASH + adId;
    Path path = Paths.get(fileStorageRepository.getUploadDir()).resolve(currentFolder);
    List<byte[]> fileContents = fileStorageRepository.listFiles(path);
    return new FilesResponse(fileContents, fileStorageRepository.getHeaders(path));
  }

  @Override
  public FileResponse retrieveAdPhoto(Long adId, String filename) {
    String folder = AD + SLASH + adId;
    Path path = Paths.get(fileStorageRepository.getUploadDir()).resolve(folder);
    return fileStorageRepository.retrieveFileWithHeaders(filename, path);
  }

  @Override
  public List<String> deletePhotos(AdPhotoPaths adPhotoPaths) {
    Long adId = adPhotoPaths.adId();

    String folder = AD + SLASH + adId;
    Path basePath = Paths.get(fileStorageRepository.getUploadDir()).resolve(folder);

    if (!adRepository.existsById(adId)) {
      throw new AdNotFoundException(adId);
    }

    List<String> paths =
        Arrays.stream(adPhotoPaths.paths())
            .map(filename -> fileStorageRepository.getUploadDir() + SLASH + folder + SLASH + filename)
            .toList();

    return paths.stream()
        .map(basePath::resolve)
        .filter(filePath -> {
          if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
            return true;
          } else {
            throw new PhotoFileNotFoundException(filePath.toString());
          }
        })
        .peek(fileStorageRepository::deleteFile)
        .map(Path::toString)
        .collect(Collectors.toList());
  }
}
