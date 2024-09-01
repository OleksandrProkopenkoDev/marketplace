package ua.tc.marketplace.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.tc.marketplace.exception.attribute.AttributeDeletionException;
import ua.tc.marketplace.exception.attribute.AttributeNotFoundException;
import ua.tc.marketplace.model.dto.attribute.AttributeDTO;
import ua.tc.marketplace.model.dto.attribute.CreateAttributeDTO;
import ua.tc.marketplace.model.dto.attribute.UpdateAttributeDTO;
import ua.tc.marketplace.model.entity.Attribute;
import ua.tc.marketplace.repository.AttributeRepository;
import ua.tc.marketplace.repository.CategoryRepository;
import ua.tc.marketplace.util.mapper.AttributeMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttributeServiceImplTest {

    @Mock
    private AttributeRepository attributeRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private AttributeMapper attributeMapper;

    @InjectMocks
    private AttributeServiceImpl attributeService;

    private Attribute attribute;
    private AttributeDTO attributeDto;
    private CreateAttributeDTO createAttributeDTO;
    private UpdateAttributeDTO updateAttributeDTO;

    @BeforeEach
    void setUp() {
        attribute = new Attribute();
        attribute.setId(1L);
        attribute.setName("Test Attribute");

        attributeDto = new AttributeDTO();
        attributeDto.setId(1L);
        attributeDto.setName("Test Attribute");

        createAttributeDTO = new CreateAttributeDTO();
        createAttributeDTO.setName("New Attribute");

        updateAttributeDTO = new UpdateAttributeDTO();
        updateAttributeDTO.setName("Updated Attribute");
    }

    @Test
    void findAttributeById_ShouldReturnAttribute_WhenAttributeExists() {
        when(attributeRepository.findById(1L)).thenReturn(Optional.of(attribute));

        Attribute foundAttribute = attributeService.findAttributeById(1L);

        assertNotNull(foundAttribute);
        assertEquals(attribute.getId(), foundAttribute.getId());
        verify(attributeRepository, times(1)).findById(1L);
    }

    @Test
    void findAttributeById_ShouldThrowException_WhenAttributeDoesNotExist() {
        when(attributeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AttributeNotFoundException.class, () -> attributeService.findAttributeById(1L));
        verify(attributeRepository, times(1)).findById(1L);
    }

    @Test
    void findAll_ShouldReturnListOfAttributes() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Attribute> attributesPage = new PageImpl<>(Collections.singletonList(attribute));

        when(attributeRepository.findAll(pageable)).thenReturn(attributesPage);
        when(attributeMapper.toDto(attribute)).thenReturn(attributeDto);

        List<AttributeDTO> result = attributeService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(attributeDto, result.get(0));
        verify(attributeRepository, times(1)).findAll(pageable);
    }


    @Test
    void createAttribute_ShouldReturnSavedAttribute() {
        when(attributeMapper.toEntity(createAttributeDTO)).thenReturn(attribute);
        when(attributeRepository.save(any(Attribute.class))).thenReturn(attribute);
        when(attributeMapper.toDto(attribute)).thenReturn(attributeDto);

        AttributeDTO result = attributeService.createAttribute(createAttributeDTO);

        assertNotNull(result);
        assertEquals(attribute.getId(), result.getId());
        verify(attributeRepository, times(1)).save(attribute);
    }

    @Test
    void update_ShouldReturnUpdatedAttribute() {
        when(attributeRepository.findById(1L)).thenReturn(Optional.of(attribute));
        when(attributeRepository.save(any(Attribute.class))).thenReturn(attribute);
        when(attributeMapper.toDto(attribute)).thenReturn(attributeDto);

        AttributeDTO result = attributeService.update(1L, updateAttributeDTO);

        assertNotNull(result);
        assertEquals(attribute.getId(), result.getId());
        verify(attributeRepository, times(1)).save(attribute);
    }

    @Test
    void deleteById_ShouldDeleteAttribute_WhenAttributeExistsAndNotLinkedToCategory() {

        when(attributeRepository.existsById(1L)).thenReturn(true);

        when(categoryRepository.existsByAttributesId(1L)).thenReturn(false);

        attributeService.deleteById(1L);

        verify(attributeRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_ShouldThrowException_WhenAttributeIsLinkedToCategory() {

        when(attributeRepository.existsById(1L)).thenReturn(true);

        when(categoryRepository.existsByAttributesId(1L)).thenReturn(true);

        AttributeDeletionException exception = assertThrows(AttributeDeletionException.class, () -> attributeService.deleteById(1L));
        assertEquals("Cannot delete an attribute that is associated with an existing category", exception.getMessage());


        verify(attributeRepository, never()).deleteById(1L);
    }
}
