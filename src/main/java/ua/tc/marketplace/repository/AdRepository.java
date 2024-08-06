package ua.tc.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.tc.marketplace.model.entity.Ad;

/**
 * Repository interface for managing advertisements.
 *
 * <p>This repository extends JpaRepository to provide CRUD operations for the Ad entity. It allows
 * querying and managing Ad entities in the database using Spring Data JPA.
 */
public interface AdRepository extends JpaRepository<Ad, Long> {}
