package ua.tc.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tc.marketplace.exception.category.CategoryNotFoundException;
import ua.tc.marketplace.exception.attribute.InvalidAttributeIdsException;
import ua.tc.marketplace.model.dto.category.CategoryDto;
import ua.tc.marketplace.model.dto.category.CreateCategoryDto;
import ua.tc.marketplace.model.dto.category.UpdateCategoryDto;
import ua.tc.marketplace.model.entity.Category;
import ua.tc.marketplace.repository.AttributeRepository;
import ua.tc.marketplace.repository.CategoryRepository;
import ua.tc.marketplace.service.CategoryService;
import ua.tc.marketplace.util.mapper.CategoryMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;
  private final AttributeRepository attributeRepository;

  @Override
  public Category findCategoryById(Long categoryId) {
    return categoryRepository
        .findById(categoryId)
        .orElseThrow(() -> new CategoryNotFoundException(categoryId));
  }


  @Override
  public Page<CategoryDto> findAll(Pageable pageable) {
    Page<Category> categories = categoryRepository.findAll(pageable);
    return categories.map(categoryMapper::toCategoryDto);
  }

  @Override
  public CategoryDto findById(Long id) {
    Category category = findCategoryById(id);
    return categoryMapper.toCategoryDto(category);
  }

  @Transactional
  @Override
  public CategoryDto createCategory(CreateCategoryDto categoryDto) {
    List<Long> attributeIds = categoryDto.getAttributeIds();

    invalidAttribute(attributeIds);
    Category category = categoryMapper.toEntity(categoryDto);
    Category savedCategory = categoryRepository.save(category);
    return categoryMapper.toCategoryDto(savedCategory);
  }


  @Transactional
  @Override
  public CategoryDto update(Long id, UpdateCategoryDto categoryDto) {
    List<Long> attributeIds = categoryDto.getAttributeIds();
    invalidAttribute(attributeIds);
    Category existingCategory = findCategoryById(id);
    categoryMapper.updateEntityFromDto(existingCategory, categoryDto);
    Category updatedCategory = categoryRepository.save(existingCategory);
    return categoryMapper.toCategoryDto(updatedCategory);
  }

  @Transactional
  @Override
  public void deleteById(Long id) {
    if (!categoryRepository.existsById(id)) {
      throw new CategoryNotFoundException(id);
    }
    categoryRepository.deleteById(id);
  }

  private void invalidAttribute(List<Long> attributeIds) {
    Set<Long> notFoundIds = attributeIds.stream()
            .filter(id -> !attributeRepository.existsById(id))
            .collect(Collectors.toSet());

    if (!notFoundIds.isEmpty()) {
      throw new InvalidAttributeIdsException(notFoundIds);
    }
  }

}
