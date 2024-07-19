package ua.tc.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.tc.marketplace.model.entity.Demo;

/**
 * Repository interface for managing {@link Demo} entities.
 *
 * <p>This interface extends {@link org.springframework.data.jpa.repository.JpaRepository},
 * providing the CRUD operations for {@link Demo} entities with {@code Integer} as the ID type.
 */
public interface DemoRepository extends JpaRepository<Demo, Integer> {}
