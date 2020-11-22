package uz.tafakkur.sport.service.dto;

import java.io.Serializable;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link uz.tafakkur.sport.domain.ImageStorage} entity.
 */
public class ImageStorageDTO implements Serializable {
    private Long id;

    @Lob
    private byte[] image;

    private String imageContentType;
    private Long sportsHallId;

    private Boolean isPrimary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Long getSportsHallId() {
        return sportsHallId;
    }

    public void setSportsHallId(Long sportsHallId) {
        this.sportsHallId = sportsHallId;
    }

    public Boolean isIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageStorageDTO)) {
            return false;
        }

        return id != null && id.equals(((ImageStorageDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImageStorageDTO{" +
            "id=" + getId() +
            ", image='" + getImage() + "'" +
            ", sportsHallId=" + getSportsHallId() +
            ", isPrimary='" + isIsPrimary() + "'" +
            "}";
    }
}
