package ua.tc.marketplace.model.entity;

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

  private String city;

  private String street;

  private String zipcode;
}

