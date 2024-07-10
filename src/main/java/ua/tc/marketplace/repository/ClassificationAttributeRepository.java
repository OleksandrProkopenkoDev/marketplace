package ua.tc.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.tc.marketplace.model.entity.ClassificationAttribute;

public interface ClassificationAttributeRepository extends JpaRepository<ClassificationAttribute, Long> {
}
