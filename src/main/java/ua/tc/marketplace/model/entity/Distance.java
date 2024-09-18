package ua.tc.marketplace.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "distance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Distance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "location1_id", nullable = false)  // Column name in the DB
  private Location location1;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "location2_id", nullable = false)  // Column name in the DB
  private Location location2;

  private Double distanceInMeters;
}
