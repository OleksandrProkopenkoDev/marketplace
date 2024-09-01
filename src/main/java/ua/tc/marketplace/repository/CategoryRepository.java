package ua.tc.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.tc.marketplace.model.entity.Category;

/**
 * Repository interface for managing {@link Category} entities.
 *
 * <p>This interface extends {@link JpaRepository} and provides basic CRUD operations
 * for {@link Category} entities, including methods for saving, finding, updating, and deleting
 * categories. The repository uses JPA to interact with the database and manage {@link Category}
 * objects.</p>
 *
 * <p>By extending {@link JpaRepository}, this interface inherits a set of pre-defined methods
 * for querying and persisting {@link Category} entities, and it can be further customized
 * with additional query methods if needed.</p>
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByAttributesId(Long attributeId);
}
