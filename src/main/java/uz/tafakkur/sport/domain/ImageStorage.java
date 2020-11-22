package uz.tafakkur.sport.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ImageStorage.
 */
@Entity
@Table(name = "image_storage")
public class ImageStorage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Column(name = "image", nullable = false)
    private byte[] image;

    @Column(name = "image_content_type", nullable = false)
    private String imageContentType;

    @Column(name = "sports_hall_id")
    private Long sportsHallId;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public ImageStorage image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public ImageStorage imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Long getSportsHallId() {
        return sportsHallId;
    }

    public ImageStorage sportsHallId(Long sportsHallId) {
        this.sportsHallId = sportsHallId;
        return this;
    }

    public void setSportsHallId(Long sportsHallId) {
        this.sportsHallId = sportsHallId;
    }

    public Boolean isIsPrimary() {
        return isPrimary;
    }

    public ImageStorage isPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
        return this;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageStorage)) {
            return false;
        }
        return id != null && id.equals(((ImageStorage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImageStorage{" +
            "id=" + getId() +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", sportsHallId=" + getSportsHallId() +
            ", isPrimary='" + isIsPrimary() + "'" +
            "}";
    }
}
