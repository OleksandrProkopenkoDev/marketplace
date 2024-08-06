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
import ua.tc.marketplace.model.dto.category.CreateCategoryDTO;
import ua.tc.marketplace.model.dto.category.UpdateCategoryDTO;
import ua.tc.marketplace.model.entity.Category;
import ua.tc.marketplace.model.entity.ClassificationAttribute;
import ua.tc.marketplace.repository.CategoryRepository;
import ua.tc.marketplace.repository.ClassificationAttributeRepository;
import ua.tc.marketplace.service.impl.CategoryServiceImpl;
import ua.tc.marketplace.util.mapper.CategoryMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private ClassificationAttributeRepository attributeRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void findCategoryById_shouldFindCategory_whenExists() {
        Long categoryId = 1L;
        Category category = new Category(); // create a sample Category entity
        CategoryDTO categoryDto = new CategoryDTO(); // create a sample CategoryDTO

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.toCategoryDto(category)).thenReturn(categoryDto);

        CategoryDTO result = categoryService.findById(categoryId);

        assertEquals(categoryDto, result);
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryMapper, times(1)).toCategoryDto(category);
    }

    @Test
    void findCategoryById_shouldThrow_whenNotExists() {
        Long categoryId = 1L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.findById(categoryId));

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryMapper, never()).toCategoryDto(any(Category.class));
    }

    @Test
    void createCategory_shouldCreate_whenValidInput() {
        CreateCategoryDTO createCategoryDto = new CreateCategoryDTO(); // create a sample CreateCategoryDTO
        Category category = new Category(); // create a sample Category entity
        CategoryDTO createdCategoryDto = new CategoryDTO(); // create a sample created CategoryDTO
        List<ClassificationAttribute> attributes = Collections.singletonList(new ClassificationAttribute()); // create a list of attributes

        when(categoryMapper.toEntity(createCategoryDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toCategoryDto(category)).thenReturn(createdCategoryDto);

        // Mock the attribute repository call

        CategoryDTO result = categoryService.createCategory(createCategoryDto);

        assertEquals(createdCategoryDto, result);
        verify(categoryRepository, times(1)).save(category);
        verify(categoryMapper, times(1)).toEntity(createCategoryDto);
        verify(categoryMapper, times(1)).toCategoryDto(category);
    }

    @Test
    void updateCategory_shouldUpdate_whenValidInput() {
        Long categoryId = 1L;
        UpdateCategoryDTO updateCategoryDto = new UpdateCategoryDTO(); // create a sample UpdateCategoryDTO
        Category existingCategory = new Category(); // create an existing Category entity
        Category updatedCategory = new Category(); // create an updated Category entity
        CategoryDTO updatedCategoryDto = new CategoryDTO(); // create an updated CategoryDTO

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(updatedCategory);
        doNothing().when(categoryMapper).updateEntityFromDto(existingCategory, updateCategoryDto);
        when(categoryMapper.toCategoryDto(updatedCategory)).thenReturn(updatedCategoryDto);

        CategoryDTO result = categoryService.update(categoryId, updateCategoryDto);

        assertEquals(updatedCategoryDto, result);
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(existingCategory);
        verify(categoryMapper, times(1)).updateEntityFromDto(existingCategory, updateCategoryDto);
        verify(categoryMapper, times(1)).toCategoryDto(updatedCategory);
    }

    @Test
    void deleteCategory_shouldDelete_whenExists() {
        Long categoryId = 1L;

        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        categoryService.deleteById(categoryId);

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void deleteCategory_shouldThrow_whenNotExists() {
        Long categoryId = 1L;

        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteById(categoryId));

        verify(categoryRepository, times(1)).existsById(categoryId);
        verify(categoryRepository, never()).deleteById(categoryId);
    }

    @Test
    void findAll_shouldReturnPagedCategories() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> categoryPage = new PageImpl<>(Collections.singletonList(new Category()));
        CategoryDTO categoryDto = new CategoryDTO();

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        when(categoryMapper.toCategoryDto(any(Category.class))).thenReturn(categoryDto);

        Page<CategoryDTO> result = categoryService.findAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(categoryDto, result.getContent().get(0));
        verify(categoryRepository, times(1)).findAll(pageable);
        verify(categoryMapper, times(1)).toCategoryDto(any(Category.class));
    }
}
