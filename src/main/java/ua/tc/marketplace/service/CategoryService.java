package ua.tc.marketplace.service;

import ua.tc.marketplace.model.entity.Category;

public interface CategoryService {

  Category findCategoryById(Long categoryId);
}
