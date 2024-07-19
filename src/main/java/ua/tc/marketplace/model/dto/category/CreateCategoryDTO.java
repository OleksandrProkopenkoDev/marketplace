package ua.tc.marketplace.model.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryDTO {
    private String name;
    private List<Long> classificationAttributes;
}
