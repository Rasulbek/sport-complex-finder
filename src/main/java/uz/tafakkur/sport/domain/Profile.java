package uz.tafakkur.sport.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import uz.tafakkur.sport.domain.enumeration.ProfileStatus;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
public class Profile implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 15)
    @Column(name = "phone", length = 15)
    private String phone;

    @Size(max = 100)
    @Column(name = "user_name", length = 100)
    private String userName;

    @Column(name = "full_name")
    private String fullName;

    @Size(max = 2)
    @Column(name = "chosen_lang", length = 2)
    private String chosenLang;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProfileStatus status;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "profiles", allowSetters = true)
    private City city;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public Profile phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public Profile userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public Profile fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getChosenLang() {
        return chosenLang;
    }

    public Profile chosenLang(String chosenLang) {
        this.chosenLang = chosenLang;
        return this;
    }

    public void setChosenLang(String chosenLang) {
        this.chosenLang = chosenLang;
    }

    public ProfileStatus getStatus() {
        return status;
    }

    public Profile status(ProfileStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ProfileStatus status) {
        this.status = status;
    }

    public City getCity() {
        return city;
    }

    public Profile city(City city) {
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
        if (!(o instanceof Profile)) {
            return false;
        }
        return id != null && id.equals(((Profile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", phone='" + getPhone() + "'" +
            ", userName='" + getUserName() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", chosenLang='" + getChosenLang() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
