package ua.tc.marketplace.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ad")
@Entity
public class Ad {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private User author;

  private String title;

  private String description;

  private BigDecimal price;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "ad_id")
  private List<Photo> photos;

  @OneToOne(cascade = CascadeType.ALL)
  private Photo thumbnail;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
