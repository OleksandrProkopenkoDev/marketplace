package ua.tc.marketplace.model.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "author_id", nullable = false)
  private User author;

  @ManyToOne
  @JoinColumn(name = "shelter_id", nullable = false)
  private User shelter;

  @Column(nullable = false)
  private String text;

  @Column(nullable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;
}
