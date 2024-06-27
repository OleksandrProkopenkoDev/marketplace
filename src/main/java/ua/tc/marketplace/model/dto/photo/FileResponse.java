package ua.tc.marketplace.model.dto.photo;

import org.springframework.http.HttpHeaders;

public record FileResponse(byte[] content, HttpHeaders headers) {}
