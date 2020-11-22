package uz.tafakkur.sport.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;
import uz.tafakkur.sport.domain.enumeration.FacilityStatus;

/**
 * A DTO for the {@link uz.tafakkur.sport.domain.SportsHall} entity.
 */
public class SportsHallDTO implements Serializable {
    private Long id;

    @NotNull
    private String name;

    @Size(max = 400)
    private String description;

    @NotNull
    private String contactPerson;

    private String phone;

    private String telegramNick;

    @NotNull
    private Integer price;

    @NotNull
    private FacilityStatus status;

    private Double latitude;

    private Double longitude;

    private String address;

    private String landmark;

    private String ownerTelegramId;

    private Long categoryId;

    private String categoryName;

    private Long cityId;

    private String cityName;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTelegramNick() {
        return telegramNick;
    }

    public void setTelegramNick(String telegramNick) {
        this.telegramNick = telegramNick;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public FacilityStatus getStatus() {
        return status;
    }

    public void setStatus(FacilityStatus status) {
        this.status = status;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getOwnerTelegramId() {
        return ownerTelegramId;
    }

    public void setOwnerTelegramId(String ownerTelegramId) {
        this.ownerTelegramId = ownerTelegramId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SportsHallDTO)) {
            return false;
        }

        return id != null && id.equals(((SportsHallDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SportsHallDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", contactPerson='" + getContactPerson() + "'" +
            ", phone='" + getPhone() + "'" +
            ", telegramNick='" + getTelegramNick() + "'" +
            ", price=" + getPrice() +
            ", status='" + getStatus() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", address='" + getAddress() + "'" +
            ", landmark='" + getLandmark() + "'" +
            ", ownerTelegramId='" + getOwnerTelegramId() + "'" +
            ", categoryId=" + getCategoryId() +
            ", categoryName='" + getCategoryName() + "'" +
            ", cityId=" + getCityId() +
            ", cityName='" + getCityName() + "'" +
            "}";
    }
}
