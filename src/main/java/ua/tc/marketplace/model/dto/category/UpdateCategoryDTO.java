package ua.tc.marketplace.model.dto.category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object for a category.
 * This class is used to transfer data between the client and the server.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryDTO {

    /**
     * The name of the category.
     * Cannot be null or empty.
     * Must be between 1 and 100 characters.
     */
    @NotNull(message = "Category name cannot be null")
    @NotEmpty(message = "Category name cannot be empty")
    @Size(min = 1, max = 100, message = "Category name must be between 1 and 100 characters")
    private String name;

    /**
     * List of classification attribute IDs associated with the category.
     * Cannot be null or empty.
     */
    @NotNull(message = "Classification attributes cannot be null")
    @NotEmpty(message = "Classification attributes cannot be empty")
    private List<Long> attributeIds;
}
