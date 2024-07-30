package ua.tc.marketplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tc.marketplace.model.dto.category.CategoryDTO;
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
  public ResponseEntity<Page<CategoryDTO>> getAllCategories(Pageable pageable) {
    log.info("Request to get all categories");
    Page<CategoryDTO> categories = categoryService.findAll(pageable);
    log.info("Categories was get successfully");
    return ResponseEntity.ok(categories);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
    log.info("Request to get category with ID: {}", id);
    CategoryDTO categoryDto = categoryService.findById(id);
    log.info("Category with ID: {} got successfully", id);
    return ResponseEntity.ok(categoryDto);
  }

  @PostMapping
  public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
    log.info("Request to create category ");
    CategoryDTO createCategory = categoryService.createCategory(categoryDTO);
    log.info("Category was create successfully");
    return ResponseEntity.status(HttpStatus.CREATED).body(createCategory);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CategoryDTO> updateCategory(
      @PathVariable Long id, @RequestBody CategoryDTO categoryDto) {
    log.info("Request to update category with ID: {}", id);
    CategoryDTO updatedCategory = categoryService.update(id, categoryDto);
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
