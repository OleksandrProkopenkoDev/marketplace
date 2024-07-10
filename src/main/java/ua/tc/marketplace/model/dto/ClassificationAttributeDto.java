package ua.tc.marketplace.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.tc.marketplace.model.enums.ValueType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassificationAttributeDto {
    private Long id;
    private String name;
    private ValueType valueType;
}
