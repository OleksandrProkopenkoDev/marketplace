package ua.tc.marketplace.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.tc.marketplace.model.entity.Distance;

public interface DistanceRepository extends JpaRepository<Distance, Long> {

  Optional<Distance> findByLocation1IdAndLocation2Id(Long location1Id, Long location2Id);
}
