package uz.tafakkur.sport.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import uz.tafakkur.sport.domain.enumeration.FacilityStatus;

/**
 * A SportsHall.
 */
@Entity
@Table(name = "hall")
public class SportsHall implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 400)
    @Column(name = "description", length = 400)
    private String description;

    @NotNull
    @Column(name = "contact_person", nullable = false)
    private String contactPerson;

    @Column(name = "phone")
    private String phone;

    @Column(name = "telegram_nick")
    private String telegramNick;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FacilityStatus status;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "address")
    private String address;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "owner_telegram_id")
    private String ownerTelegramId;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "sportsHalls", allowSetters = true)
    private Category category;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "sportsHalls", allowSetters = true)
    private City city;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SportsHall name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public SportsHall description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public SportsHall contactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
        return this;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhone() {
        return phone;
    }

    public SportsHall phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTelegramNick() {
        return telegramNick;
    }

    public SportsHall telegramNick(String telegramNick) {
        this.telegramNick = telegramNick;
        return this;
    }

    public void setTelegramNick(String telegramNick) {
        this.telegramNick = telegramNick;
    }

    public Integer getPrice() {
        return price;
    }

    public SportsHall price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public FacilityStatus getStatus() {
        return status;
    }

    public SportsHall status(FacilityStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(FacilityStatus status) {
        this.status = status;
    }

    public Double getLatitude() {
        return latitude;
    }

    public SportsHall latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public SportsHall longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public SportsHall address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public SportsHall landmark(String landmark) {
        this.landmark = landmark;
        return this;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getOwnerTelegramId() {
        return ownerTelegramId;
    }

    public SportsHall ownerTelegramId(String ownerTelegramId) {
        this.ownerTelegramId = ownerTelegramId;
        return this;
    }

    public void setOwnerTelegramId(String ownerTelegramId) {
        this.ownerTelegramId = ownerTelegramId;
    }

    public Category getCategory() {
        return category;
    }

    public SportsHall category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public City getCity() {
        return city;
    }

    public SportsHall city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SportsHall)) {
            return false;
        }
        return id != null && id.equals(((SportsHall) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SportsHall{" +
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
            "}";
    }
}
