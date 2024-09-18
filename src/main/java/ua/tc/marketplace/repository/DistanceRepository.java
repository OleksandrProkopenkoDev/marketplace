package ua.tc.marketplace.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.tc.marketplace.model.entity.Distance;

public interface DistanceRepository extends JpaRepository<Distance, Long> {

  List<Distance> findByLocation1IdAndLocation2IdIn(Long location1Id, List<Long> location2Ids);
}
