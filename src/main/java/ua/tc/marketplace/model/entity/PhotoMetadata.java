package ua.tc.marketplace.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing metadata associated with a photo.
 *
 * <p>This class defines the structure of metadata stored in the database for a photo. It includes
 * properties such as ID, width, height, extension, and size.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "photo_metadata")
@Entity
public class PhotoMetadata {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer width;

  private Integer height;

  private String extension;

  private Float size;
}
