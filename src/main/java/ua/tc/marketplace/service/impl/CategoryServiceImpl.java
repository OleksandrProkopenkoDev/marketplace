package ua.tc.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.tc.marketplace.exception.category.CategoryNotFoundException;
import ua.tc.marketplace.model.entity.Category;
import ua.tc.marketplace.repository.CategoryRepository;
import ua.tc.marketplace.service.CategoryService;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  @Override
  public Category findCategoryById(Long categoryId) {
    return categoryRepository
        .findById(categoryId)
        .orElseThrow(() -> new CategoryNotFoundException(categoryId));
  }
}
