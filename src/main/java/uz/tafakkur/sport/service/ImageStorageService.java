package uz.tafakkur.sport.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.tafakkur.sport.domain.ImageStorage;
import uz.tafakkur.sport.repository.ImageStorageRepository;
import uz.tafakkur.sport.service.dto.ImageStorageDTO;
import uz.tafakkur.sport.service.mapper.ImageStorageMapper;

/**
 * Service Implementation for managing {@link ImageStorage}.
 */
@Service
@Transactional
public class ImageStorageService {
    private final Logger log = LoggerFactory.getLogger(ImageStorageService.class);

    private final ImageStorageRepository imageStorageRepository;

    private final ImageStorageMapper imageStorageMapper;

    public ImageStorageService(ImageStorageRepository imageStorageRepository, ImageStorageMapper imageStorageMapper) {
        this.imageStorageRepository = imageStorageRepository;
        this.imageStorageMapper = imageStorageMapper;
    }

    /**
     * Save a imageStorage.
     *
     * @param imageStorageDTO the entity to save.
     * @return the persisted entity.
     */
    public ImageStorageDTO save(ImageStorageDTO imageStorageDTO) {
        log.debug("Request to save ImageStorage : {}", imageStorageDTO);
        ImageStorage imageStorage = imageStorageMapper.toEntity(imageStorageDTO);
        imageStorage = imageStorageRepository.save(imageStorage);
        return imageStorageMapper.toDto(imageStorage);
    }

    /**
     * Get all the imageStorages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ImageStorageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ImageStorages");
        return imageStorageRepository.findAll(pageable).map(imageStorageMapper::toDto);
    }

    /**
     * Get one imageStorage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ImageStorageDTO> findOne(Long id) {
        log.debug("Request to get ImageStorage : {}", id);
        return imageStorageRepository.findById(id).map(imageStorageMapper::toDto);
    }

    /**
     * Delete the imageStorage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ImageStorage : {}", id);

        imageStorageRepository.deleteById(id);
    }
}
