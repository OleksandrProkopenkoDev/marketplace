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

  private String country;

  @NotNull
  private String city;

  private String street;

  private String zipcode;
}

