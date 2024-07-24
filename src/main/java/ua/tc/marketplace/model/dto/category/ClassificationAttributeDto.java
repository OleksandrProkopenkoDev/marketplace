package ua.tc.marketplace.model.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.tc.marketplace.model.enums.ValueType;

/**
 * Data Transfer Object (DTO) for ClassificationAttribute.
 * Represents a classification attribute with its ID, name, and value type.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassificationAttributeDto {

    /**
     * Unique identifier for the classification attribute.
     */
    private Long id;

    /**
     * Name of the classification attribute.
     * Must not be blank and have a size between 1 and 255 characters.
     */
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    private String name;

    /**
     * Value type of the classification attribute.
     * Must not be null.
     */
    @NotNull(message = "ValueType cannot be null")
    private ValueType valueType;
}
