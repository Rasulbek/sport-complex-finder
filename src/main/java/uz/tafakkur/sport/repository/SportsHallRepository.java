package uz.tafakkur.sport.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.tafakkur.sport.domain.SportsHall;

/**
 * Spring Data  repository for the SportsHall entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SportsHallRepository extends JpaRepository<SportsHall, Long>, JpaSpecificationExecutor<SportsHall> {}
