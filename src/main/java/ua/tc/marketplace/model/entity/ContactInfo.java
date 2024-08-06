package ua.tc.marketplace.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing contact information.
 *
 * <p>This class defines the structure of contact information stored in the database. It includes
 * properties such as ID and phone number.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contact_info")
@Entity
public class ContactInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String phone;
}
