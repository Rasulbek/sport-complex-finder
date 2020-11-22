package uz.tafakkur.sport.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;
import uz.tafakkur.sport.domain.enumeration.FacilityStatus;

/**
 * A DTO for the {@link uz.tafakkur.sport.domain.Category} entity.
 */
public class CategoryDTO implements Serializable {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private FacilityStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FacilityStatus getStatus() {
        return status;
    }

    public void setStatus(FacilityStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryDTO)) {
            return false;
        }

        return id != null && id.equals(((CategoryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
