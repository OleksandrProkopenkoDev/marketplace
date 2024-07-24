package ua.tc.marketplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tc.marketplace.model.dto.category.CreateCategoryDTO;
import ua.tc.marketplace.service.CategoryService;

/**
 * CategoryController handles HTTP requests related to CRUD operations for the Category entity.
 * It provides an API for retrieving, creating, updating, and deleting categories.
 */
@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<CreateCategoryDTO>> getAllCategories(Pageable pageable) {
        Page<CreateCategoryDTO> categories = categoryService.findAll(pageable);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateCategoryDTO> getCategoryById(@PathVariable Long id) {
        CreateCategoryDTO categoryDto = categoryService.findById(id);
        return ResponseEntity.ok(categoryDto);
    }

    @PostMapping
    public ResponseEntity<CreateCategoryDTO> createCategory(@Valid @RequestBody CreateCategoryDTO categoryDTO) {
        CreateCategoryDTO createCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreateCategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CreateCategoryDTO categoryDto) {
        CreateCategoryDTO updatedCategory = categoryService.update(id, categoryDto);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        logger.info("Request to delete category with ID: {}", id);
        categoryService.deleteById(id);
        logger.info("Category with ID: {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}
