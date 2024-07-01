package ua.tc.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.tc.marketplace.model.entity.Ad;

public interface AdRepository extends JpaRepository<Ad, Long> {}
