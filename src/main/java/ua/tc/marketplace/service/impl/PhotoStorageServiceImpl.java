package ua.tc.marketplace.service.impl;

import jakarta.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tc.marketplace.exception.ad.AdNotFoundException;
import ua.tc.marketplace.exception.photo.PhotoFileNotFoundException;
import ua.tc.marketplace.model.dto.photo.AdPhotoPaths;
import ua.tc.marketplace.model.dto.photo.FileResponse;
import ua.tc.marketplace.model.dto.photo.FilesResponse;
import ua.tc.marketplace.model.dto.photo.PhotoFilesDto;
import ua.tc.marketplace.model.entity.Ad;
import ua.tc.marketplace.model.entity.Photo;
import ua.tc.marketplace.repository.AdRepository;
import ua.tc.marketplace.repository.FileStorageRepository;
import ua.tc.marketplace.repository.PhotoRepository;
import ua.tc.marketplace.service.PhotoStorageService;

@Transactional
@Service
@RequiredArgsConstructor
public class PhotoStorageServiceImpl implements PhotoStorageService {

  private final AdRepository adRepository;
  private final FileStorageRepository fileStorageRepository;
  private final PhotoRepository photoRepository;

  private static final String AD = "ad";
  private static final String SLASH = File.separator;

  @Override
  public List<Photo> saveAdPhotos(PhotoFilesDto photoFilesDto) {
    Ad ad = adRepository
        .findById(photoFilesDto.adId())
        .orElseThrow(() -> new AdNotFoundException(photoFilesDto.adId()));

    String folder = AD + SLASH + photoFilesDto.adId();
    Path path = Paths.get(fileStorageRepository.getUploadDir()).resolve(folder);
    fileStorageRepository.createDirectory(path);

    List<Photo> photos =
        Arrays.stream(photoFilesDto.files())
            .map(file -> fileStorageRepository.writeFile(file, path))
            .toList();

    ad.getPhotos().addAll(photos);
    ad = adRepository.save(ad);
    return ad.getPhotos();
  }

  @Transactional(readOnly = true)
  @Override
  public FilesResponse retrieveAllAdPhotos(Long adId) {
    String currentFolder = AD + SLASH + adId;
    Path path = Paths.get(fileStorageRepository.getUploadDir()).resolve(currentFolder);
    List<byte[]> fileContents = fileStorageRepository.readFilesList(path);
    return new FilesResponse(fileContents, getHeaders(path));
  }

  @Transactional(readOnly = true)
  @Override
  public FileResponse retrieveAdPhoto(Long adId, String filename) {
    String folder = AD + SLASH + adId;
    Path path = Paths.get(fileStorageRepository.getUploadDir()).resolve(folder);

    byte[] bytes = fileStorageRepository.readFile(filename, path);
    return new FileResponse(bytes, getHeaders(path));
  }

  @Override
  public List<String> deleteAdPhotos(AdPhotoPaths adPhotoPaths) {
    Long adId = adPhotoPaths.adId();

    String folder = AD + SLASH + adId;
    Path basePath = Paths.get(fileStorageRepository.getUploadDir()).resolve(folder);

    if (!adRepository.existsById(adId)) {
      throw new AdNotFoundException(adId);
    }

    List<String> paths =
        Arrays.stream(adPhotoPaths.paths())
            .map(
                filename ->
                    fileStorageRepository.getUploadDir() + SLASH + folder + SLASH + filename)
            .toList();

    return paths.stream()
        .map(basePath::resolve)
        .filter(
            filePath -> {
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
}
