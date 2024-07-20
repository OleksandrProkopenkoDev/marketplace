package ua.tc.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.tc.marketplace.model.entity.Attribute;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {}
