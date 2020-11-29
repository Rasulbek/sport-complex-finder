package uz.tafakkur.sport.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.tafakkur.sport.domain.Profile;
import uz.tafakkur.sport.service.dto.ProfileDTO;

/**
 * Spring Data  repository for the Profile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>, JpaSpecificationExecutor<Profile> {
    Optional<Profile> findFirstByChatId(Long chatId);
}
