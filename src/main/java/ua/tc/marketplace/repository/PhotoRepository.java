package ua.tc.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.tc.marketplace.model.entity.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {}