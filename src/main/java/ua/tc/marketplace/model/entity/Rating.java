package ua.tc.marketplace.model.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "rating")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Integer value;

  @ManyToOne
  @JoinColumn(name = "shelter_id", nullable = false)
  private User shelter;
}
