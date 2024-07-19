package ua.tc.marketplace.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a demo entity.
 *
 * <p>This class defines the structure of a demo entity stored in the database. It includes
 * properties such as ID and name.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Demo {

  @Id @GeneratedValue private Long id;
  private String name;
}
