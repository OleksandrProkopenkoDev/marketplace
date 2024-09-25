package ua.tc.marketplace.model.entity;

import lombok.*;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tag")
@Data
@NoArgsConstructor
//@RequiredArgsConstructor
public class Tag {
  public Tag(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @ManyToMany(mappedBy = "tags")
  private Set<Article> articles = new HashSet<>();
}

