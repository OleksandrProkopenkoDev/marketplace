package ua.tc.marketplace.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ua.tc.marketplace.model.enums.UserRole;

/**
 * Entity class representing a user of the application.
 *
 * <p>This class defines the structure of a user stored in the database. It includes properties such
 * as ID, email, password, user role, first name, last name, profile picture, contact information,
 * favorite ads, creation timestamp, and update timestamp.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;

  private String password;

  @Enumerated(EnumType.STRING)
  private UserRole userRole;

  private String firstName;

  private String lastName; // optional

  @OneToOne(cascade = CascadeType.ALL)
  private Photo profilePicture;

  @OneToOne(cascade = CascadeType.ALL)
  private ContactInfo contactInfo;

  @ManyToMany
  @JoinTable(
      name = "user_favorites",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "ad_id"))
  private List<Ad> favorites;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @Override
  public String toString() {
    return "User{"
        + "id="
        + id
        + ", email='"
        + email
        + '\''
        + ", password='"
        + password
        + '\''
        + ", userRole="
        + userRole
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", profilePicture="
        + profilePicture
        + ", contactInfo="
        + contactInfo
        + ", favorites="
        + (favorites != null ? favorites.stream().map(Ad::getId).toList() : null)
        + ", createdAt="
        + createdAt
        + ", updatedAt="
        + updatedAt
        + '}';
  }
}
