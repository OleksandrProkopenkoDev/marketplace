package ua.tc.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.tc.marketplace.exception.category.CategoryNotFoundException;
import ua.tc.marketplace.model.dto.category.CategoryDto;
import ua.tc.marketplace.model.dto.category.CreateCategoryDTO;
import ua.tc.marketplace.model.entity.Category;
import ua.tc.marketplace.repository.CategoryRepository;
import ua.tc.marketplace.util.mapper.CategoryMapper;

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
  public Page<CategoryDto> findAll(Pageable pageable) {
    Page<Category> categories = categoryRepository.findAll(pageable);
    return categories.map(categoryMapper::toDto);
  }

  @Override
  public CategoryDto findById(Long id) {
    Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryNotFoundException(id));
    return categoryMapper.toDto(category);
  }

  @Override
  public CreateCategoryDTO createCategory(CreateCategoryDTO categoryDto) {
    Category category = categoryMapper.toEntity(categoryDto);
    Category savedCategory = categoryRepository.save(category);
    return categoryMapper.toCreateCategoryDto(savedCategory);
  }

  @Override
  public CategoryDto update(Long id, CategoryDto categoryDto) {
    Category updatedCategory = categoryRepository.findById(id)
            .map(existingCategory -> {
              categoryMapper.updateEntityFromDto(existingCategory, categoryDto);
              return categoryRepository.save(existingCategory);
            })
            .orElseThrow(() -> new CategoryNotFoundException(id));
    return categoryMapper.toDto(updatedCategory);
  }

  @Override
  public void deleteById(Long id) {
    if (!categoryRepository.existsById(id)) {
      throw new CategoryNotFoundException(id);
    }
    categoryRepository.deleteById(id);
  }
}
