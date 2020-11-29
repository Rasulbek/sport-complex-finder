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
import uz.tafakkur.sport.domain.enumeration.ProfileStatus;

/**
 * Criteria class for the {@link uz.tafakkur.sport.domain.Profile} entity. This class is used
 * in {@link uz.tafakkur.sport.web.rest.ProfileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /profiles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProfileCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ProfileStatus
     */
    public static class ProfileStatusFilter extends Filter<ProfileStatus> {

        public ProfileStatusFilter() {}

        public ProfileStatusFilter(ProfileStatusFilter filter) {
            super(filter);
        }

        @Override
        public ProfileStatusFilter copy() {
            return new ProfileStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter phone;

    private LongFilter chatId;

    private StringFilter userName;

    private StringFilter fullName;

    private StringFilter chosenLang;

    private ProfileStatusFilter status;

    private LongFilter cityId;

    public ProfileCriteria() {}

    public ProfileCriteria(ProfileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.chatId = other.chatId == null ? null : other.chatId.copy();
        this.userName = other.userName == null ? null : other.userName.copy();
        this.fullName = other.fullName == null ? null : other.fullName.copy();
        this.chosenLang = other.chosenLang == null ? null : other.chosenLang.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.cityId = other.cityId == null ? null : other.cityId.copy();
    }

    @Override
    public ProfileCriteria copy() {
        return new ProfileCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public LongFilter getChatId() {
        return chatId;
    }

    public void setChatId(LongFilter chatId) {
        this.chatId = chatId;
    }

    public StringFilter getUserName() {
        return userName;
    }

    public void setUserName(StringFilter userName) {
        this.userName = userName;
    }

    public StringFilter getFullName() {
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public StringFilter getChosenLang() {
        return chosenLang;
    }

    public void setChosenLang(StringFilter chosenLang) {
        this.chosenLang = chosenLang;
    }

    public ProfileStatusFilter getStatus() {
        return status;
    }

    public void setStatus(ProfileStatusFilter status) {
        this.status = status;
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
        final ProfileCriteria that = (ProfileCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(chatId, that.chatId) &&
            Objects.equals(userName, that.userName) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(chosenLang, that.chosenLang) &&
            Objects.equals(status, that.status) &&
            Objects.equals(cityId, that.cityId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phone, chatId, userName, fullName, chosenLang, status, cityId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (chatId != null ? "chatId=" + chatId + ", " : "") +
                (userName != null ? "userName=" + userName + ", " : "") +
                (fullName != null ? "fullName=" + fullName + ", " : "") +
                (chosenLang != null ? "chosenLang=" + chosenLang + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (cityId != null ? "cityId=" + cityId + ", " : "") +
            "}";
    }
}
