package ua.tc.marketplace.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.tc.marketplace.model.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
  Optional<Location> findByStreetAndCityAndCountryAndZipcode(
      String street, String city, String country, String zipcode);
}
