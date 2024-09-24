package ua.tc.marketplace.util.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tc.marketplace.model.dto.tag.CreateTagDto;
import ua.tc.marketplace.model.dto.tag.TagDto;
import ua.tc.marketplace.model.dto.tag.UpdateTagDto;

/**
 * This interface defines the OpenAPI annotations for the {@link ua.tc.marketplace.controller.TagController} class. It provides endpoints
 * for managing tags.
 */
@Tag(name = "Tag API", description = "API for managing tags")
public interface TagOpenApi {

  @Operation(
      summary = "Get all tag",
      description = "Retrieves a pageable list of all tags.")
  @GetMapping("/all")
  ResponseEntity<Page<TagDto>> getAllTags(@PageableDefault Pageable pageable);

  @Operation(
      summary = "Get tag by ID",
      description = "Retrieves a tag by its unique identifier.")
  @GetMapping("/{id}")
  ResponseEntity<TagDto> getTagById(@PathVariable Long id);


  @Operation(
          summary = "Create a new tag",
          description = "Creates a new tag based on the provided data.")
  @PostMapping
  ResponseEntity<TagDto> createTag(@Valid CreateTagDto dto);

  @Operation(
      summary = "Updates an existing tag",
      description = "Updates an existing tag with the provided data.")
  @PutMapping()
  ResponseEntity<TagDto> updateTag(@PathVariable Long tagId, @RequestBody @Valid UpdateTagDto tagDto);

  @Operation(
      summary = "Delete a tag",
      description = "Deletes a tag by its unique identifier.")
  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteTag(@PathVariable Long id);
}
