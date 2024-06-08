package ua.tc.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.tc.marketplace.model.entity.Demo;

public interface DemoRepository extends JpaRepository<Demo, Integer> {

}
