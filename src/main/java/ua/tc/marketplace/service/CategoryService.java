package ua.tc.marketplace.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.tc.marketplace.model.dto.category.CategoryDto;
import ua.tc.marketplace.model.dto.category.CreateCategoryDTO;
import ua.tc.marketplace.model.entity.Category;

public interface CategoryService {
    Category findCategoryById(Long categoryId);

    Page<CategoryDto> findAll(Pageable pageable);

    CategoryDto findById(Long id);

    CreateCategoryDTO createCategory(CreateCategoryDTO categoryDto);

    CategoryDto update(Long id, CategoryDto categoryDto);

    void deleteById(Long id);
}
