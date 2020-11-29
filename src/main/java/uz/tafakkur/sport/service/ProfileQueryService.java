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
import uz.tafakkur.sport.domain.Profile;
import uz.tafakkur.sport.repository.ProfileRepository;
import uz.tafakkur.sport.service.dto.ProfileCriteria;
import uz.tafakkur.sport.service.dto.ProfileDTO;
import uz.tafakkur.sport.service.mapper.ProfileMapper;

/**
 * Service for executing complex queries for {@link Profile} entities in the database.
 * The main input is a {@link ProfileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProfileDTO} or a {@link Page} of {@link ProfileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProfileQueryService extends QueryService<Profile> {
    private final Logger log = LoggerFactory.getLogger(ProfileQueryService.class);

    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper;

    public ProfileQueryService(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    /**
     * Return a {@link List} of {@link ProfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProfileDTO> findByCriteria(ProfileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Profile> specification = createSpecification(criteria);
        return profileMapper.toDto(profileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProfileDTO> findByCriteria(ProfileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Profile> specification = createSpecification(criteria);
        return profileRepository.findAll(specification, page).map(profileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProfileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Profile> specification = createSpecification(criteria);
        return profileRepository.count(specification);
    }

    /**
     * Function to convert {@link ProfileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Profile> createSpecification(ProfileCriteria criteria) {
        Specification<Profile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Profile_.id));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Profile_.phone));
            }
            if (criteria.getChatId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getChatId(), Profile_.chatId));
            }
            if (criteria.getUserName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserName(), Profile_.userName));
            }
            if (criteria.getFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullName(), Profile_.fullName));
            }
            if (criteria.getChosenLang() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChosenLang(), Profile_.chosenLang));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Profile_.status));
            }
            if (criteria.getCityId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCityId(), root -> root.join(Profile_.city, JoinType.LEFT).get(City_.id))
                    );
            }
        }
        return specification;
    }
}
