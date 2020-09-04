package uz.tafakkur.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.tafakkur.sport.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
