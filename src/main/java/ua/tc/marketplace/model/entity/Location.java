package ua.tc.marketplace.model.entity;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "location")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String address;
}

