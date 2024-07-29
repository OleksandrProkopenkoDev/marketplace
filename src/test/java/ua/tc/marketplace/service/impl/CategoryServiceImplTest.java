package ua.tc.marketplace.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.tc.marketplace.exception.category.CategoryNotFoundException;
import ua.tc.marketplace.model.dto.category.CategoryDTO;
import ua.tc.marketplace.model.entity.Category;
import ua.tc.marketplace.repository.CategoryRepository;
import ua.tc.marketplace.util.mapper.CategoryMapper;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void findCategoryById_shouldFindCategory_whenExists() {
        // Arrange
        Long categoryId = 1L;
        Category category = new Category(); // create a sample Category entity
        CategoryDTO categoryDto = new CategoryDTO(); // create a sample CategoryDTO

        // Mock repository method to return a mocked Category entity
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Mock mapper method to return the provided CategoryDTO
        when(categoryMapper.toCategoryDto(category)).thenReturn(categoryDto);

        // Act
        CategoryDTO result = categoryService.findById(categoryId);

        // Assert
        assertEquals(categoryDto, result);

        // Verify that repository method was called with correct argument
        verify(categoryRepository, times(1)).findById(categoryId);

        // Verify that mapper method was called with correct argument
        verify(categoryMapper, times(1)).toCategoryDto(category);
    }

    @Test
    void findCategoryById_shouldThrow_whenNotExists() {
        // Arrange
        Long categoryId = 1L;

        // Mock repository method to return an empty Optional (simulating not found scenario)
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act and Assert
        // Use assertThrows to verify that CategoryNotFoundException is thrown
        assertThrows(CategoryNotFoundException.class, () -> categoryService.findById(categoryId));

        // Verify that repository method was called with correct argument
        verify(categoryRepository, times(1)).findById(categoryId);

        // Verify that mapper method was never called
        verify(categoryMapper, never()).toCategoryDto(any(Category.class));
    }

    @Test
    void createCategory_shouldCreate_whenValidInput() {
        // Arrange
        CategoryDTO categoryDto = new CategoryDTO(); // create a sample CreateCategoryDTO
        Category category = new Category(); // create a sample Category entity
        CategoryDTO createdCategoryDto = new CategoryDTO(); // create a sample created CreateCategoryDTO

        // Mock mapper method to return a Category entity
        when(categoryMapper.toEntity(categoryDto)).thenReturn(category);

        // Mock repository method to return the saved Category entity
        when(categoryRepository.save(category)).thenReturn(category);

        // Mock mapper method to return the created CreateCategoryDTO
        when(categoryMapper.toCategoryDto(category)).thenReturn(createdCategoryDto);

        // Act
        CategoryDTO result = categoryService.createCategory(categoryDto);

        // Assert
        assertEquals(createdCategoryDto, result);

        // Verify that repository method was called with correct argument
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void updateCategory_shouldUpdate_whenValidInput() {
        // Arrange
        Long categoryId = 1L;
        CategoryDTO updateCategoryDto = new CategoryDTO(); // create a sample CreateCategoryDTO
        Category existingCategory = new Category(); // create an existing Category entity
        Category updatedCategory = new Category(); // create an updated Category entity
        CategoryDTO updatedCategoryDto = new CategoryDTO(); // create an updated CreateCategoryDTO

        // Mock repository method to return the existing Category entity
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));

        // Mock repository method to return the saved updated Category entity
        when(categoryRepository.save(existingCategory)).thenReturn(updatedCategory);

        // Mock mapper method to update the existing Category entity
        doNothing().when(categoryMapper).updateEntityFromDto(existingCategory, updateCategoryDto);

        // Mock mapper method to return the updated CreateCategoryDTO
        when(categoryMapper.toCategoryDto(updatedCategory)).thenReturn(updatedCategoryDto);

        // Act
        CategoryDTO result = categoryService.update(categoryId, updateCategoryDto);

        // Assert
        assertEquals(updatedCategoryDto, result);

        // Verify that repository method was called with correct arguments
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(existingCategory);
        verify(categoryMapper, times(1)).updateEntityFromDto(existingCategory, updateCategoryDto);
    }

    @Test
    void deleteCategory_shouldDelete_whenExists() {
        // Arrange
        Long categoryId = 1L;

        // Mock repository method to return true (simulating category exists)
        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        // Act
        categoryService.deleteById(categoryId);

        // Assert
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void deleteCategory_shouldThrow_whenNotExists() {
        // Arrange
        Long categoryId = 1L;

        // Mock repository method to return false (simulating category does not exist)
        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        // Act and Assert
        // Use assertThrows to verify that CategoryNotFoundException is thrown
        assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteById(categoryId));

        // Verify that repository method was called with correct argument
        verify(categoryRepository, times(1)).existsById(categoryId);
        verify(categoryRepository, never()).deleteById(categoryId);
    }

    @Test
    void findAll_shouldReturnPagedCategories() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> categoryPage = new PageImpl<>(Collections.singletonList(new Category()));
        CategoryDTO categoryDto = new CategoryDTO();

        // Mock repository method to return a page of Category entities
        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);

        // Mock mapper method to return a page of CreateCategoryDTOs
        when(categoryMapper.toCategoryDto(any(Category.class))).thenReturn(categoryDto);

        // Act
        Page<CategoryDTO> result = categoryService.findAll(pageable);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals(categoryDto, result.getContent().get(0));

        // Verify that repository method was called with correct argument
        verify(categoryRepository, times(1)).findAll(pageable);
    }
}
