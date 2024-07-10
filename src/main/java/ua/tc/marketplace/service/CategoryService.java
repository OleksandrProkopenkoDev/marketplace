package ua.tc.marketplace.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ua.tc.marketplace.exception.model.CategoryCreationException;
import ua.tc.marketplace.exception.model.CategoryNotFoundException;
import ua.tc.marketplace.exception.model.MappingException;
import ua.tc.marketplace.model.entity.Category;
import ua.tc.marketplace.repository.CategoryRepository;
import ua.tc.marketplace.model.dto.CategoryDto;
import ua.tc.marketplace.util.mapper.CategoryMapper;
import ua.tc.marketplace.util.mapper.ClassificationAttributeMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final CategoryMapper categoryMapper;
    @Autowired
    private final ClassificationAttributeMapper classificationAttributeMapper;



    public Page<CategoryDto> findAll(Pageable pageable) {
        try {
            Page<Category> categories = categoryRepository.findAll(pageable);
            return categories.map(categoryMapper::convertToDto);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e);
        }
    }

    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + id + " not found"));
        return categoryMapper.convertToDto(category);
    }

    public CategoryDto createCategory(CategoryDto categoryDto) {
        try {
            Category category = categoryMapper.toEntity(categoryDto);
            Category savedCategory = categoryRepository.save(category);
            return categoryMapper.convertToDto(savedCategory);
        } catch (MappingException e) {

            System.err.println("Error during mapping: " + e.getMessage());
            throw new CategoryCreationException("Error creating category: mapping error", e);
        } catch (Exception e) {

            System.err.println("Error creating category: " + e.getMessage());
            throw new CategoryCreationException("Error creating category", e);
        }
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public CategoryDto update(Long id, CategoryDto  categoryDto) {
        try {
            Category category = categoryMapper.toEntity(categoryDto);

            Category updatedCategory = categoryRepository.findById(id)
                    .map(existingCategory -> {
                        category.setId(id);
                        return categoryRepository.save(category);
                    })
                    .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + id + " not found"));

            return categoryMapper.convertToDto(updatedCategory);
        } catch (CategoryNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e);
        }
    }

    public void deleteById(Long id) {
        try {
            if (!categoryRepository.existsById(id)) {
                throw new CategoryNotFoundException("Category with ID " + id + " not found");
            }
            categoryRepository.deleteById(id);
        } catch (CategoryNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Data integrity violation", e);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e);
        }
    }
}
