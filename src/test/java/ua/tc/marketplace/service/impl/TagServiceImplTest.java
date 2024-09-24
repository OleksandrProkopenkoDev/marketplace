package ua.tc.marketplace.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.tc.marketplace.exception.TagNotFoundException;
import ua.tc.marketplace.model.dto.tag.CreateTagDto;
import ua.tc.marketplace.model.dto.tag.TagDto;
import ua.tc.marketplace.model.dto.tag.UpdateTagDto;
import ua.tc.marketplace.model.entity.Tag;
import ua.tc.marketplace.repository.TagRepository;
import ua.tc.marketplace.util.mapper.TagMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

  @Mock private TagRepository tagRepository;
  @Mock private TagMapper tagMapper;

  @InjectMocks private TagServiceImpl tagService;

  private Long tagId;
  private TagDto tagDto;
  private UpdateTagDto updateTagDto;
  private TagDto updatedTagDto;
  private CreateTagDto createTagDto;
  private Tag tag;
  private Tag updatedTag;

  @BeforeEach
  void setUp(){
    tagId = 1L;

    tagDto = new TagDto(tagId,"tag_name");

    updateTagDto = new UpdateTagDto("updated_tag_name");

    updatedTagDto = new TagDto(tagId,"updated_tag_name");

    tag = new Tag(tagId,"tag_name");

    updatedTag = new Tag(tagId, "updated_tag_name");

    createTagDto = new CreateTagDto("new_tag");

  }

  @Test
  void findTagById_shouldReturnTagDto_whenExists() {

    // Mock repository
    when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));

    // Mock mapper
    when(tagMapper.toDto(tag)).thenReturn(tagDto);

    // run method
    TagDto result = tagService.findTagById(tagId);

    // Assert results
    assertEquals(tagDto, result);

    // Verify that repository method was called with correct argument
    verify(tagRepository, times(1)).findById(tagId);
  }

  @Test
  void findTagById_shouldThrow_whenNotExists() {

    // Mock repository method to return an empty Optional (simulating not found scenario)
    when(tagRepository.findById(tagId)).thenReturn(Optional.empty());

    // Act and Assert
    // Use assertThrows to verify that TagNotFoundException is thrown
    assertThrows(TagNotFoundException.class, () -> tagService.findTagById(tagId));

    // Verify that repository method was called with correct argument
    verify(tagRepository, times(1)).findById(tagId);
  }

  @Test
  void createTag_shouldCreate_whenValidInput() {

    // Mock mapper from createTagDto to entity
    when(tagMapper.toEntity(createTagDto)).thenReturn(tag);

    // Mock repository
    when(tagRepository.save(tag)).thenReturn(tag);

    // Mock mapper from entity back to Dto
    when(tagMapper.toDto(tag)).thenReturn(tagDto);

    // Act
    TagDto result = tagService.createTag(createTagDto);

    // Assert
    assertEquals(tagDto, result);

    // Verify that TagMapper method was called with correct argument
    verify(tagMapper, times(1)).toEntity(createTagDto);

    // Verify that TagRepository method was called with correct argument
    verify(tagRepository, times(1)).save(tag);

    // Verify that TagMapper method was called with correct argument
    verify(tagMapper, times(1)).toDto(tag);
  }

  @Test
  void updateTag_shouldUpdate_whenValidInput() {


    // Mock TagRepository to return existingTag when findById is called
    when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));

    // Mock TagMapper to return updatedTag when updateEntityFromDto is called
    doAnswer(
            invocation -> {
              UpdateTagDto dto = invocation.getArgument(1);
              Tag TagToUpdate = invocation.getArgument(0);
              TagToUpdate.setName(dto.name());
              return null;
            })
        .when(tagMapper)
        .updateEntityFromDto(tag, updateTagDto);

    // Mock repository
    when(tagRepository.save(tag)).thenReturn(updatedTag);

    // Mock mapper
    when(tagMapper.toDto(updatedTag)).thenReturn(updatedTagDto);

    // Act
    TagDto result = tagService.updateTag(tagId, updateTagDto);

    // Assert
    assertEquals(updatedTagDto, result);

    // Verify that TagRepository method was called with correct argument
    verify(tagRepository, times(1)).findById(tagId);

    // Verify that adMapper method was called with correct argument
    verify(tagMapper, times(1)).updateEntityFromDto(tag, updateTagDto);

    // Verify that TagRepository method was called with correct argument
    verify(tagRepository, times(1)).save(tag);

    // Verify that adMapper method was called with correct argument
    verify(tagMapper, times(1)).toDto(updatedTag);
  }

  @Test
  void updateTag_shouldThrow_whenTagNotExists() {

    assertThrows(
        TagNotFoundException.class, () -> tagService.findTagById(tagId));

    // Verify that repository method was called with correct argument
    verify(tagRepository, times(1)).findById(ArgumentMatchers.anyLong());
  }

  @Test
  void deleteTag_shouldDelete() {

    // Mock TagRepository to return existingTag when findById is called
    when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));

    // Act
    tagService.deleteTagById(tagId);

    // Verify that TagRepository method was called with correct argument
    verify(tagRepository, times(1)).findById(tagId);

    // Verify that TagRepository method was called with correct argument
    verify(tagRepository, times(1)).deleteById(tagId);
  }

  @Test
  public void deleteTag_shouldThrowException_whenTagNotExists() {

    // Mock TagRepository to throw AdNotFoundException when findById is called
    when(tagRepository.findById(tagId)).thenReturn(Optional.empty());

    // Act
    assertThrows(TagNotFoundException.class, () -> tagService.deleteTagById(tagId));

    // Verify that TagRepository method was called with correct argument
    verify(tagRepository, times(1)).findById(tagId);

    // Verify that TagRepository method was called with correct argument
    verify(tagRepository, never()).deleteById(tagId);
  }
}
