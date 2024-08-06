package ua.tc.marketplace.model.dto.ad;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** Data Transfer Object (DTO) representing a request for an ad attribute. */
public record AdAttributeRequestDto(
    @NotNull(message = "Attribute ID cannot be null") Long attributeId,
    @NotBlank(message = "Attribute value cannot be blank")
        @Size(max = 255, message = "Attribute value cannot exceed 255 characters")
        String value) {}
