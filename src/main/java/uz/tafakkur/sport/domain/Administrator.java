package uz.tafakkur.sport.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import uz.tafakkur.sport.domain.enumeration.AdminRole;

/**
 * A Administrator.
 */
@Entity
@Table(name = "administrator")
public class Administrator implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "chat_id", nullable = false)
    private String chatId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private AdminRole role;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatId() {
        return chatId;
    }

    public Administrator chatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public AdminRole getRole() {
        return role;
    }

    public Administrator role(AdminRole role) {
        this.role = role;
        return this;
    }

    public void setRole(AdminRole role) {
        this.role = role;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Administrator)) {
            return false;
        }
        return id != null && id.equals(((Administrator) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Administrator{" +
            "id=" + getId() +
            ", chatId='" + getChatId() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
}
