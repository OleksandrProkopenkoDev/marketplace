package ua.tc.marketplace.util.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tc.marketplace.model.dto.attribute.AttributeDto;
import ua.tc.marketplace.model.dto.attribute.AttributeRequest;

import java.util.List;

/**
 * This interface defines the OpenAPI annotations for the {@link ua.tc.marketplace.controller.AttributeController} class.
 * It provides endpoints for managing attributes.
 */
@Tag(name = "Attribute API", description = "API for managing attributes")
public interface AttributeOpenApi {

  @Operation(
          summary = "Get all attributes",
          description = "Retrieves a pageable list of all attributes."
  )
  @GetMapping
  ResponseEntity<List<AttributeDto>> getAllAttributes(@PageableDefault Pageable pageable);

  @Operation(
          summary = "Get attribute by ID",
          description = "Retrieves an attribute by its unique identifier."
  )
  @GetMapping("/{id}")
  ResponseEntity<AttributeDto> getAttributeById(@PathVariable Long id);

  @Operation(
          summary = "Create a new attribute",
          description = "Creates a new attribute based on the provided data."
  )
  @PostMapping
  ResponseEntity<AttributeDto> createAttribute(@Valid @RequestBody AttributeRequest attributeDTO);

  @Operation(
          summary = "Update an existing attribute",
          description = "Updates an existing attribute with the provided data."
  )
  @PutMapping("/{id}")
  ResponseEntity<AttributeDto> updateAttribute(
          @PathVariable Long id,
          @RequestBody AttributeRequest attributeDto
  );

  @Operation(
          summary = "Delete an attribute",
          description = "Deletes an attribute by its unique identifier."
  )
  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteAttribute(@PathVariable Long id);
}
