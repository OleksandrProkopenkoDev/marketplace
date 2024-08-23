package ua.tc.marketplace.model.dto.attribute;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.tc.marketplace.model.enums.ValueType;

/**

 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAttributeDTO {


    @NotNull(message = "Attribute name cannot be null")
    @NotEmpty(message = "Attribute name cannot be empty")
    @Size(min = 1, max = 100, message = "Attribute name must be between 1 and 100 characters")
    private String name;


    @NotNull(message = "ValueType cannot be null")
    private ValueType valueType;
}

