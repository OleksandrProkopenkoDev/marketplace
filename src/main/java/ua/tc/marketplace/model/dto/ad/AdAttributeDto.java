package ua.tc.marketplace.model.dto.ad;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Data Transfer Object (DTO) representing an ad attribute. */
public record AdAttributeDto(
    @NotBlank(message = "Attribute name cannot be blank")
        @Size(max = 100, message = "Attribute name cannot exceed 100 characters")
        String name,
    @NotBlank(message = "Attribute value cannot be blank")
        @Size(max = 255, message = "Attribute value cannot exceed 255 characters")
        String value) {}
