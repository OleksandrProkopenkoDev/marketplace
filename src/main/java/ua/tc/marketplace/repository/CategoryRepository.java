package ua.tc.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.tc.marketplace.model.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
