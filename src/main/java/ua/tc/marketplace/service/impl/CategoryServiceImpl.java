package ua.tc.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tc.marketplace.exception.category.CategoryNotFoundException;
import ua.tc.marketplace.model.dto.category.CategoryDTO;
import ua.tc.marketplace.model.entity.Category;
import ua.tc.marketplace.repository.CategoryRepository;
import ua.tc.marketplace.util.mapper.CategoryMapper;

/**
 * Implementation of the {@link ua.tc.marketplace.service.CategoryService} interface for managing
 * category-related operations.
 *
 * <p>This service provides methods for creating, updating, deleting, and retrieving categories.
 * It handles operations such as saving new categories, updating existing categories,
 * retrieving all categories with pagination support, retrieving a category by its ID,
 * and deleting a category by its ID.</p>
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ua.tc.marketplace.service.CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  @Override
  public Category findCategoryById(Long categoryId) {
    return categoryRepository
        .findById(categoryId)
        .orElseThrow(() -> new CategoryNotFoundException(categoryId));
  }


  @Override
  public Page<CategoryDTO> findAll(Pageable pageable) {
    Page<Category> categories = categoryRepository.findAll(pageable);
    return categories.map(categoryMapper::toCategoryDto);
  }

  @Override
  public CategoryDTO findById(Long id) {
    Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryNotFoundException(id));
    return categoryMapper.toCategoryDto(category);
  }

  @Transactional
  @Override
  public CategoryDTO createCategory(CategoryDTO categoryDto) {
    Category category = categoryMapper.toEntity(categoryDto);
    Category savedCategory = categoryRepository.save(category);
    return categoryMapper.toCategoryDto(savedCategory);
  }

  @Transactional
  @Override
  public CategoryDTO update(Long id, CategoryDTO categoryDto) {
    Category existingCategory = categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryNotFoundException(id));
    categoryMapper.updateEntityFromDto(existingCategory, categoryDto);
    Category updatedCategory = categoryRepository.save(existingCategory);
    return categoryMapper.toCategoryDto(updatedCategory);
  }

  @Override
  public void deleteById(Long id) {
    if (!categoryRepository.existsById(id)) {
      throw new CategoryNotFoundException(id);
    }
    categoryRepository.deleteById(id);
  }
}
