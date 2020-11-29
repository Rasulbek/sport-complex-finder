package uz.tafakkur.sport.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;
import uz.tafakkur.sport.domain.enumeration.ProfileStatus;

/**
 * A DTO for the {@link uz.tafakkur.sport.domain.Profile} entity.
 */
public class ProfileDTO implements Serializable {
    private Long id;

    @Size(max = 15)
    private String phone;

    private Long chatId;

    @Size(max = 100)
    private String userName;

    private String fullName;

    @Size(max = 2)
    private String chosenLang;

    @NotNull
    private ProfileStatus status;

    private Long cityId;

    private String cityName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getChosenLang() {
        return chosenLang;
    }

    public void setChosenLang(String chosenLang) {
        this.chosenLang = chosenLang;
    }

    public ProfileStatus getStatus() {
        return status;
    }

    public void setStatus(ProfileStatus status) {
        this.status = status;
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
        if (!(o instanceof ProfileDTO)) {
            return false;
        }

        return id != null && id.equals(((ProfileDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfileDTO{" +
            "id=" + getId() +
            ", phone='" + getPhone() + "'" +
            ", chatId=" + getChatId() +
            ", userName='" + getUserName() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", chosenLang='" + getChosenLang() + "'" +
            ", status='" + getStatus() + "'" +
            ", cityId=" + getCityId() +
            ", cityName='" + getCityName() + "'" +
            "}";
    }
}
