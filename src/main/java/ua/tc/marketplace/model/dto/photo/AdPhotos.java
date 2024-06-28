package ua.tc.marketplace.model.dto.photo;

import org.springframework.web.multipart.MultipartFile;

public record AdPhotos(Long adId, MultipartFile[] files) {}
