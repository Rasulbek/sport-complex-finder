package uz.tafakkur.sport.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.tafakkur.sport.domain.SportsHall;
import uz.tafakkur.sport.repository.SportsHallRepository;
import uz.tafakkur.sport.service.dto.SportsHallDTO;
import uz.tafakkur.sport.service.mapper.SportsHallMapper;

/**
 * Service Implementation for managing {@link SportsHall}.
 */
@Service
@Transactional
public class SportsHallService {
    private final Logger log = LoggerFactory.getLogger(SportsHallService.class);

    private final SportsHallRepository sportsHallRepository;

    private final SportsHallMapper sportsHallMapper;

    public SportsHallService(SportsHallRepository sportsHallRepository, SportsHallMapper sportsHallMapper) {
        this.sportsHallRepository = sportsHallRepository;
        this.sportsHallMapper = sportsHallMapper;
    }

    /**
     * Save a sportsHall.
     *
     * @param sportsHallDTO the entity to save.
     * @return the persisted entity.
     */
    public SportsHallDTO save(SportsHallDTO sportsHallDTO) {
        log.debug("Request to save SportsHall : {}", sportsHallDTO);
        SportsHall sportsHall = sportsHallMapper.toEntity(sportsHallDTO);
        sportsHall = sportsHallRepository.save(sportsHall);
        return sportsHallMapper.toDto(sportsHall);
    }

    /**
     * Get all the sportsHalls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SportsHallDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SportsHalls");
        return sportsHallRepository.findAll(pageable).map(sportsHallMapper::toDto);
    }

    /**
     * Get one sportsHall by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SportsHallDTO> findOne(Long id) {
        log.debug("Request to get SportsHall : {}", id);
        return sportsHallRepository.findById(id).map(sportsHallMapper::toDto);
    }

    /**
     * Delete the sportsHall by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SportsHall : {}", id);

        sportsHallRepository.deleteById(id);
    }
}
