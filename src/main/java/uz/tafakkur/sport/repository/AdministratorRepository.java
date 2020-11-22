package uz.tafakkur.sport.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.tafakkur.sport.domain.Administrator;

/**
 * Spring Data  repository for the Administrator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {}
