package ua.tc.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.tc.marketplace.model.entity.Photo;

/**
 * Repository interface for managing {@link Photo} entities.
 *
 * <p>This interface extends {@link org.springframework.data.jpa.repository.JpaRepository},
 * providing the CRUD operations for {@link Photo} entities with {@code Long} as the ID type.
 */
public interface PhotoRepository extends JpaRepository<Photo, Long> {}
