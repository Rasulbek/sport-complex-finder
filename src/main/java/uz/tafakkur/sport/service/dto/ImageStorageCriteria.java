package uz.tafakkur.sport.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link uz.tafakkur.sport.domain.ImageStorage} entity. This class is used
 * in {@link uz.tafakkur.sport.web.rest.ImageStorageResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /image-storages?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ImageStorageCriteria implements Serializable, Criteria {
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter sportsHallId;

    private BooleanFilter isPrimary;

    public ImageStorageCriteria() {}

    public ImageStorageCriteria(ImageStorageCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.sportsHallId = other.sportsHallId == null ? null : other.sportsHallId.copy();
        this.isPrimary = other.isPrimary == null ? null : other.isPrimary.copy();
    }

    @Override
    public ImageStorageCriteria copy() {
        return new ImageStorageCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getSportsHallId() {
        return sportsHallId;
    }

    public void setSportsHallId(LongFilter sportsHallId) {
        this.sportsHallId = sportsHallId;
    }

    public BooleanFilter getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(BooleanFilter isPrimary) {
        this.isPrimary = isPrimary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ImageStorageCriteria that = (ImageStorageCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(sportsHallId, that.sportsHallId) && Objects.equals(isPrimary, that.isPrimary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sportsHallId, isPrimary);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImageStorageCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (sportsHallId != null ? "sportsHallId=" + sportsHallId + ", " : "") +
                (isPrimary != null ? "isPrimary=" + isPrimary + ", " : "") +
            "}";
    }
}
