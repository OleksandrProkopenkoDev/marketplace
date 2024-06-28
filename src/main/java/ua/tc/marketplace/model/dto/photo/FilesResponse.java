package ua.tc.marketplace.model.dto.photo;

import java.util.List;
import org.springframework.http.HttpHeaders;

public record FilesResponse(List<byte[]> contents, HttpHeaders headers) {}
