package ua.tc.marketplace.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.tc.marketplace.model.enums.ValueType;

/**
 * Entity class representing a classification attribute.
 *
 * <p>This class defines the structure of a classification attribute stored in the database. It
 * includes properties such as ID, name, and value type.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "classification_attribute")
@Entity
public class ClassificationAttribute {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Enumerated(EnumType.STRING)
  private ValueType valueType;
}
