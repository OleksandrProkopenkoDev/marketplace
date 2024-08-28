package ua.tc.marketplace.model.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;
}

