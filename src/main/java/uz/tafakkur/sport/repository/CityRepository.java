package uz.tafakkur.sport.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.tafakkur.sport.domain.City;

/**
 * Spring Data  repository for the City entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CityRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {}
