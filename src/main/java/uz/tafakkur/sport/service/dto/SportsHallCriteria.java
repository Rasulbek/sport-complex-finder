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
import uz.tafakkur.sport.domain.enumeration.FacilityStatus;

/**
 * Criteria class for the {@link uz.tafakkur.sport.domain.SportsHall} entity. This class is used
 * in {@link uz.tafakkur.sport.web.rest.SportsHallResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sports-halls?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SportsHallCriteria implements Serializable, Criteria {

    /**
     * Class for filtering FacilityStatus
     */
    public static class FacilityStatusFilter extends Filter<FacilityStatus> {

        public FacilityStatusFilter() {}

        public FacilityStatusFilter(FacilityStatusFilter filter) {
            super(filter);
        }

        @Override
        public FacilityStatusFilter copy() {
            return new FacilityStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter contactPerson;

    private StringFilter phone;

    private StringFilter telegramNick;

    private IntegerFilter price;

    private FacilityStatusFilter status;

    private DoubleFilter latitude;

    private DoubleFilter longitude;

    private StringFilter address;

    private StringFilter landmark;

    private StringFilter ownerTelegramId;

    private LongFilter categoryId;

    private LongFilter cityId;

    public SportsHallCriteria() {}

    public SportsHallCriteria(SportsHallCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.contactPerson = other.contactPerson == null ? null : other.contactPerson.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.telegramNick = other.telegramNick == null ? null : other.telegramNick.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.latitude = other.latitude == null ? null : other.latitude.copy();
        this.longitude = other.longitude == null ? null : other.longitude.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.landmark = other.landmark == null ? null : other.landmark.copy();
        this.ownerTelegramId = other.ownerTelegramId == null ? null : other.ownerTelegramId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.cityId = other.cityId == null ? null : other.cityId.copy();
    }

    @Override
    public SportsHallCriteria copy() {
        return new SportsHallCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(StringFilter contactPerson) {
        this.contactPerson = contactPerson;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getTelegramNick() {
        return telegramNick;
    }

    public void setTelegramNick(StringFilter telegramNick) {
        this.telegramNick = telegramNick;
    }

    public IntegerFilter getPrice() {
        return price;
    }

    public void setPrice(IntegerFilter price) {
        this.price = price;
    }

    public FacilityStatusFilter getStatus() {
        return status;
    }

    public void setStatus(FacilityStatusFilter status) {
        this.status = status;
    }

    public DoubleFilter getLatitude() {
        return latitude;
    }

    public void setLatitude(DoubleFilter latitude) {
        this.latitude = latitude;
    }

    public DoubleFilter getLongitude() {
        return longitude;
    }

    public void setLongitude(DoubleFilter longitude) {
        this.longitude = longitude;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getLandmark() {
        return landmark;
    }

    public void setLandmark(StringFilter landmark) {
        this.landmark = landmark;
    }

    public StringFilter getOwnerTelegramId() {
        return ownerTelegramId;
    }

    public void setOwnerTelegramId(StringFilter ownerTelegramId) {
        this.ownerTelegramId = ownerTelegramId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public LongFilter getCityId() {
        return cityId;
    }

    public void setCityId(LongFilter cityId) {
        this.cityId = cityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SportsHallCriteria that = (SportsHallCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(contactPerson, that.contactPerson) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(telegramNick, that.telegramNick) &&
            Objects.equals(price, that.price) &&
            Objects.equals(status, that.status) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(address, that.address) &&
            Objects.equals(landmark, that.landmark) &&
            Objects.equals(ownerTelegramId, that.ownerTelegramId) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(cityId, that.cityId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            description,
            contactPerson,
            phone,
            telegramNick,
            price,
            status,
            latitude,
            longitude,
            address,
            landmark,
            ownerTelegramId,
            categoryId,
            cityId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SportsHallCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (contactPerson != null ? "contactPerson=" + contactPerson + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (telegramNick != null ? "telegramNick=" + telegramNick + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (latitude != null ? "latitude=" + latitude + ", " : "") +
                (longitude != null ? "longitude=" + longitude + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (landmark != null ? "landmark=" + landmark + ", " : "") +
                (ownerTelegramId != null ? "ownerTelegramId=" + ownerTelegramId + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
                (cityId != null ? "cityId=" + cityId + ", " : "") +
            "}";
    }
}
