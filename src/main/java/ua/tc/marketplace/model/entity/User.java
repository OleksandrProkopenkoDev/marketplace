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
import ua.tc.marketplace.model.enums.UserRole;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
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

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
