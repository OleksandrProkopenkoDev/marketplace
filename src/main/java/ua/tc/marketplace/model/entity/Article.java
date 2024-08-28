package ua.tc.marketplace.model.entity;

import java.util.ArrayList;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ua.tc.marketplace.model.enums.ArticleStatus;

@Entity
@Table(name = "article")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  @ManyToOne
  @JoinColumn(name = "author_id", nullable = false)
  private User author;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @Builder.Default
  @ManyToMany
  @JoinTable(
      name = "article_photo",
      joinColumns = @JoinColumn(name = "article_id"),
      inverseJoinColumns = @JoinColumn(name = "photo_id")
  )
  private List<Photo> photos = new ArrayList<>();

  @Builder.Default
  @ManyToMany
  @JoinTable(
      name = "article_tag",
      joinColumns = @JoinColumn(name = "article_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id")
  )
  private List<Tag> tags = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  private String metaDescription;
  private String structuredData;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ArticleStatus status;

  @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
  private Boolean isFeatured = false;

  @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
  private Integer likes = 0;

  private String slug;
}
