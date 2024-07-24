package ua.tc.marketplace.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.tc.marketplace.model.dto.category.CreateCategoryDTO;
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

    Page<CreateCategoryDTO> findAll(Pageable pageable);

    CreateCategoryDTO findById(Long id);

    CreateCategoryDTO createCategory(CreateCategoryDTO categoryDto);


    CreateCategoryDTO update(Long id, CreateCategoryDTO categoryDto);

    void deleteById(Long id);
}
