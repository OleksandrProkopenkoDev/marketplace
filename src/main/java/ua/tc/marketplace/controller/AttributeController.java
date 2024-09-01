package ua.tc.marketplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tc.marketplace.model.dto.attribute.AttributeDTO;
import ua.tc.marketplace.model.dto.attribute.CreateAttributeDTO;
import ua.tc.marketplace.model.dto.attribute.UpdateAttributeDTO;
import ua.tc.marketplace.service.AttributeService;

import java.util.List;

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
    public ResponseEntity<List<AttributeDTO>> getAllAttributes(Pageable pageable) {
        List<AttributeDTO> attributes = attributeService.findAll(pageable);
        log.info("Attributes were retrieved successfully");
        return ResponseEntity.ok(attributes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttributeDTO> getAttributeById(@PathVariable Long id) {
        AttributeDTO attributeDto = attributeService.findById(id);
        log.info("Attribute with ID: {} was retrieved successfully", id);
        return ResponseEntity.ok(attributeDto);
    }

    @PostMapping
    public ResponseEntity<AttributeDTO> createAttribute(@Valid @RequestBody CreateAttributeDTO attributeDTO) {
        AttributeDTO createdAttribute = attributeService.createAttribute(attributeDTO);
        log.info("Attribute was created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAttribute);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttributeDTO> updateAttribute(
            @PathVariable Long id, @RequestBody UpdateAttributeDTO attributeDto) {
        AttributeDTO updatedAttribute = attributeService.update(id, attributeDto);
        log.info("Attribute with ID: {} was updated successfully", id);
        return ResponseEntity.ok(updatedAttribute);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttribute(@PathVariable Long id) {
        attributeService.deleteById(id);
        log.info("Attribute with ID: {} was deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}
