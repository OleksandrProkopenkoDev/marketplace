package ua.tc.marketplace.util.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tc.marketplace.model.dto.category.CategoryCountedDto;
import ua.tc.marketplace.model.dto.category.CategoryDto;
import ua.tc.marketplace.model.dto.category.CreateCategoryDto;
import ua.tc.marketplace.model.dto.category.UpdateCategoryDto;

/**
 * This interface defines the OpenAPI annotations for the {@link ua.tc.marketplace.controller.CategoryController} class.
 * It provides endpoints for managing categories.
 */
@Tag(name = "Category API", description = "API for managing categories")
public interface CategoryOpenApi {

  @Operation(
          summary = "Get all categories",
          description = "Retrieves a pageable list of all categories."
  )
  @GetMapping
  ResponseEntity<Page<CategoryDto>> getAllCategories(@PageableDefault Pageable pageable);

  @Operation(
          summary = "Get all counted categories",
          description = "Retrieves a pageable list of all categories with ad counts."
  )
  @GetMapping("/counted")
  ResponseEntity<Page<CategoryCountedDto>> getAllCountedCategories(@PageableDefault Pageable pageable);

  @Operation(
          summary = "Get category by ID",
          description = "Retrieves a category by its unique identifier."
  )
  @GetMapping("/{id}")
  ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id);

  @Operation(
          summary = "Create a new category",
          description = "Creates a new category based on the provided data."
  )
  @PostMapping
  ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CreateCategoryDto categoryDTO);

  @Operation(
          summary = "Update an existing category",
          description = "Updates an existing category with the provided data."
  )
  @PutMapping("/{id}")
  ResponseEntity<CategoryDto> updateCategory(
          @PathVariable Long id,
          @RequestBody UpdateCategoryDto categoryDto
  );

  @Operation(
          summary = "Delete a category",
          description = "Deletes a category by its unique identifier."
  )
  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteCategory(@PathVariable Long id);
}
