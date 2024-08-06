package ua.tc.marketplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tc.marketplace.model.dto.category.CategoryDto;
import ua.tc.marketplace.model.dto.category.CreateCategoryDto;
import ua.tc.marketplace.model.dto.category.UpdateCategoryDto;
import ua.tc.marketplace.service.CategoryService;

/**
 * CategoryController handles HTTP requests related to CRUD operations for the Category entity. It
 * provides an API for retrieving, creating, updating, and deleting categories.
 */
@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping
  public ResponseEntity<Page<CategoryDto>> getAllCategories(Pageable pageable) {
    log.info("Request to get all categories");
    Page<CategoryDto> categories = categoryService.findAll(pageable);
    log.info("Categories was get successfully");
    return ResponseEntity.ok(categories);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
    log.info("Request to get category with ID: {}", id);
    CategoryDto categoryDto = categoryService.findById(id);
    log.info("Category with ID: {} got successfully", id);
    return ResponseEntity.ok(categoryDto);
  }

  @PostMapping
  public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CreateCategoryDto categoryDTO) {
    log.info("Request to create category ");
    CategoryDto createCategory = categoryService.createCategory(categoryDTO);
    log.info("Category was create successfully");
    return ResponseEntity.status(HttpStatus.CREATED).body(createCategory);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CategoryDto> updateCategory(
      @PathVariable Long id, @RequestBody UpdateCategoryDto categoryDto) {
    log.info("Request to update category with ID: {}", id);
    CategoryDto updatedCategory = categoryService.update(id, categoryDto);
    log.info("Category with ID: {} updated successfully", id);
    return ResponseEntity.ok(updatedCategory);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    log.info("Request to delete category with ID: {}", id);
    categoryService.deleteById(id);
    log.info("Category with ID: {} deleted successfully", id);
    return ResponseEntity.noContent().build();
  }
}
