package uz.tafakkur.sport.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;
import uz.tafakkur.sport.domain.enumeration.AdminRole;

/**
 * A DTO for the {@link uz.tafakkur.sport.domain.Administrator} entity.
 */
public class AdministratorDTO implements Serializable {
    private Long id;

    @NotNull
    private String chatId;

    private AdminRole role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public AdminRole getRole() {
        return role;
    }

    public void setRole(AdminRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdministratorDTO)) {
            return false;
        }

        return id != null && id.equals(((AdministratorDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdministratorDTO{" +
            "id=" + getId() +
            ", chatId='" + getChatId() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
}
