package uz.tafakkur.sport.service;

import io.github.jhipster.service.QueryService;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.tafakkur.sport.domain.*; // for static metamodels
import uz.tafakkur.sport.domain.SportsHall;
import uz.tafakkur.sport.repository.SportsHallRepository;
import uz.tafakkur.sport.service.dto.SportsHallCriteria;
import uz.tafakkur.sport.service.dto.SportsHallDTO;
import uz.tafakkur.sport.service.mapper.SportsHallMapper;

/**
 * Service for executing complex queries for {@link SportsHall} entities in the database.
 * The main input is a {@link SportsHallCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SportsHallDTO} or a {@link Page} of {@link SportsHallDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SportsHallQueryService extends QueryService<SportsHall> {
    private final Logger log = LoggerFactory.getLogger(SportsHallQueryService.class);

    private final SportsHallRepository sportsHallRepository;

    private final SportsHallMapper sportsHallMapper;

    public SportsHallQueryService(SportsHallRepository sportsHallRepository, SportsHallMapper sportsHallMapper) {
        this.sportsHallRepository = sportsHallRepository;
        this.sportsHallMapper = sportsHallMapper;
    }

    /**
     * Return a {@link List} of {@link SportsHallDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SportsHallDTO> findByCriteria(SportsHallCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SportsHall> specification = createSpecification(criteria);
        return sportsHallMapper.toDto(sportsHallRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SportsHallDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SportsHallDTO> findByCriteria(SportsHallCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SportsHall> specification = createSpecification(criteria);
        return sportsHallRepository.findAll(specification, page).map(sportsHallMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SportsHallCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SportsHall> specification = createSpecification(criteria);
        return sportsHallRepository.count(specification);
    }

    /**
     * Function to convert {@link SportsHallCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SportsHall> createSpecification(SportsHallCriteria criteria) {
        Specification<SportsHall> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SportsHall_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SportsHall_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SportsHall_.description));
            }
            if (criteria.getContactPerson() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactPerson(), SportsHall_.contactPerson));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), SportsHall_.phone));
            }
            if (criteria.getTelegramNick() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelegramNick(), SportsHall_.telegramNick));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), SportsHall_.price));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), SportsHall_.status));
            }
            if (criteria.getLatitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatitude(), SportsHall_.latitude));
            }
            if (criteria.getLongitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLongitude(), SportsHall_.longitude));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), SportsHall_.address));
            }
            if (criteria.getLandmark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLandmark(), SportsHall_.landmark));
            }
            if (criteria.getOwnerTelegramId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOwnerTelegramId(), SportsHall_.ownerTelegramId));
            }
            if (criteria.getCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoryId(),
                            root -> root.join(SportsHall_.category, JoinType.LEFT).get(Category_.id)
                        )
                    );
            }
            if (criteria.getCityId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCityId(), root -> root.join(SportsHall_.city, JoinType.LEFT).get(City_.id))
                    );
            }
        }
        return specification;
    }
}
