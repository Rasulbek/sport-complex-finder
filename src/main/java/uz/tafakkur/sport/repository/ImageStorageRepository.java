package uz.tafakkur.sport.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.tafakkur.sport.domain.ImageStorage;

/**
 * Spring Data  repository for the ImageStorage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImageStorageRepository extends JpaRepository<ImageStorage, Long>, JpaSpecificationExecutor<ImageStorage> {}
