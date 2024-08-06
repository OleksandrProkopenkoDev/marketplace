package ua.tc.marketplace.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
import ua.tc.marketplace.model.dto.category.CategoryDto;
import ua.tc.marketplace.model.dto.category.CreateCategoryDto;
import ua.tc.marketplace.model.dto.category.UpdateCategoryDto;
import ua.tc.marketplace.model.entity.Attribute;
import ua.tc.marketplace.model.entity.Category;
import ua.tc.marketplace.repository.AttributeRepository;
import ua.tc.marketplace.repository.CategoryRepository;
import ua.tc.marketplace.util.mapper.CategoryMapper;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
  @Mock private AttributeRepository attributeRepository;

  @Mock private CategoryRepository categoryRepository;

  @Mock private CategoryMapper categoryMapper;

  @InjectMocks private CategoryServiceImpl categoryService;

  @Test
  void findCategoryById_shouldFindCategory_whenExists() {
    Long categoryId = 1L;
    Category category = new Category(); // create a sample Category entity
    CategoryDto categoryDto = new CategoryDto(); // create a sample CategoryDto

    when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
    when(categoryMapper.toCategoryDto(category)).thenReturn(categoryDto);

    CategoryDto result = categoryService.findById(categoryId);

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
    CreateCategoryDto createCategoryDto =
        new CreateCategoryDto(); // create a sample CreateCategoryDto
    Category category = new Category(); // create a sample Category entity
    CategoryDto createdCategoryDto = new CategoryDto(); // create a sample created CategoryDto
    List<Attribute> attributes =
        Collections.singletonList(new Attribute()); // create a list of attributes

    when(categoryMapper.toEntity(createCategoryDto)).thenReturn(category);
    when(categoryRepository.save(category)).thenReturn(category);
    when(categoryMapper.toCategoryDto(category)).thenReturn(createdCategoryDto);

    // Mock the attribute repository call

    CategoryDto result = categoryService.createCategory(createCategoryDto);

    assertEquals(createdCategoryDto, result);
    verify(categoryRepository, times(1)).save(category);
    verify(categoryMapper, times(1)).toEntity(createCategoryDto);
    verify(categoryMapper, times(1)).toCategoryDto(category);
  }

  @Test
  void updateCategory_shouldUpdate_whenValidInput() {
    Long categoryId = 1L;
    UpdateCategoryDto updateCategoryDto =
        new UpdateCategoryDto(); // create a sample UpdateCategoryDto
    Category existingCategory = new Category(); // create an existing Category entity
    Category updatedCategory = new Category(); // create an updated Category entity
    CategoryDto updatedCategoryDto = new CategoryDto(); // create an updated CategoryDto

    when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
    when(categoryRepository.save(existingCategory)).thenReturn(updatedCategory);
    doNothing().when(categoryMapper).updateEntityFromDto(existingCategory, updateCategoryDto);
    when(categoryMapper.toCategoryDto(updatedCategory)).thenReturn(updatedCategoryDto);

    CategoryDto result = categoryService.update(categoryId, updateCategoryDto);

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
    CategoryDto categoryDto = new CategoryDto();

    when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
    when(categoryMapper.toCategoryDto(any(Category.class))).thenReturn(categoryDto);

    Page<CategoryDto> result = categoryService.findAll(pageable);

    assertEquals(1, result.getTotalElements());
    assertEquals(categoryDto, result.getContent().get(0));
    verify(categoryRepository, times(1)).findAll(pageable);
    verify(categoryMapper, times(1)).toCategoryDto(any(Category.class));
  }
}
