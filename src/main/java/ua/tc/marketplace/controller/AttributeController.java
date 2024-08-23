package ua.tc.marketplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tc.marketplace.model.dto.attribute.AttributeDto;
import ua.tc.marketplace.model.dto.attribute.CreateAttributeDTO;
import ua.tc.marketplace.model.dto.attribute.UpdateAttributeDTO;
import ua.tc.marketplace.service.AttributeService;

/**
 * AttributeController handles HTTP requests related to CRUD operations for the Attribute entity. It
 * provides an API for retrieving, creating, updating, and deleting attributes.
 */
@RestController
@RequestMapping("/api/v1/attribute")
@RequiredArgsConstructor
@Slf4j
public class AttributeController {

    private final AttributeService attributeService;

    @GetMapping
    public ResponseEntity<Page<AttributeDto>> getAllAttributes(Pageable pageable) {
        log.info("Request to get all attributes");
        Page<AttributeDto> attributes = attributeService.findAll(pageable);
        log.info("Attributes were retrieved successfully");
        return ResponseEntity.ok(attributes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttributeDto> getAttributeById(@PathVariable Long id) {
        log.info("Request to get attribute with ID: {}", id);
        AttributeDto attributeDto = attributeService.findById(id);
        log.info("Attribute with ID: {} was retrieved successfully", id);
        return ResponseEntity.ok(attributeDto);
    }

    @PostMapping
    public ResponseEntity<AttributeDto> createAttribute(@Valid @RequestBody CreateAttributeDTO attributeDTO) {
        log.info("Request to create attribute");
        AttributeDto createdAttribute = attributeService.createAttribute(attributeDTO);
        log.info("Attribute was created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAttribute);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttributeDto> updateAttribute(
            @PathVariable Long id, @RequestBody UpdateAttributeDTO attributeDto) {
        log.info("Request to update attribute with ID: {}", id);
        AttributeDto updatedAttribute = attributeService.update(id, attributeDto);
        log.info("Attribute with ID: {} was updated successfully", id);
        return ResponseEntity.ok(updatedAttribute);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttribute(@PathVariable Long id) {
        log.info("Request to delete attribute with ID: {}", id);
        attributeService.deleteById(id);
        log.info("Attribute with ID: {} was deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}
