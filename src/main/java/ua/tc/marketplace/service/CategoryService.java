package ua.tc.marketplace.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.tc.marketplace.model.dto.category.CategoryDTO;
import ua.tc.marketplace.model.entity.Category;

/**
 * Service interface defining operations for managing categories. Includes methods for
 * retrieving, creating, updating, and deleting categories.
 *
 * <p>This interface provides operations for:
 * <ul>
 *   <li>Retrieving a category by its ID</li>
 *   <li>Retrieving all categories with pagination support</li>
 *   <li>Creating a new category</li>
 *   <li>Updating an existing category</li>
 *   <li>Deleting a category by its ID</li>
 * </ul>
 * </p>
 */
public interface CategoryService {
    Category findCategoryById(Long categoryId);

    Page<CategoryDTO> findAll(Pageable pageable);

    CategoryDTO findById(Long id);

    CategoryDTO createCategory(CategoryDTO categoryDto);


    CategoryDTO update(Long id, CategoryDTO categoryDto);

    void deleteById(Long id);
}
