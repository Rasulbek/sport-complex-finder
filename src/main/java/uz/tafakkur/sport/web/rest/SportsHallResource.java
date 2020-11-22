package uz.tafakkur.sport.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.tafakkur.sport.service.SportsHallQueryService;
import uz.tafakkur.sport.service.SportsHallService;
import uz.tafakkur.sport.service.dto.SportsHallCriteria;
import uz.tafakkur.sport.service.dto.SportsHallDTO;
import uz.tafakkur.sport.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.tafakkur.sport.domain.SportsHall}.
 */
@RestController
@RequestMapping("/api")
public class SportsHallResource {
    private final Logger log = LoggerFactory.getLogger(SportsHallResource.class);

    private static final String ENTITY_NAME = "sportsHall";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SportsHallService sportsHallService;

    private final SportsHallQueryService sportsHallQueryService;

    public SportsHallResource(SportsHallService sportsHallService, SportsHallQueryService sportsHallQueryService) {
        this.sportsHallService = sportsHallService;
        this.sportsHallQueryService = sportsHallQueryService;
    }

    /**
     * {@code POST  /sports-halls} : Create a new sportsHall.
     *
     * @param sportsHallDTO the sportsHallDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sportsHallDTO, or with status {@code 400 (Bad Request)} if the sportsHall has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sports-halls")
    public ResponseEntity<SportsHallDTO> createSportsHall(@Valid @RequestBody SportsHallDTO sportsHallDTO) throws URISyntaxException {
        log.debug("REST request to save SportsHall : {}", sportsHallDTO);
        if (sportsHallDTO.getId() != null) {
            throw new BadRequestAlertException("A new sportsHall cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SportsHallDTO result = sportsHallService.save(sportsHallDTO);
        return ResponseEntity
            .created(new URI("/api/sports-halls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sports-halls} : Updates an existing sportsHall.
     *
     * @param sportsHallDTO the sportsHallDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sportsHallDTO,
     * or with status {@code 400 (Bad Request)} if the sportsHallDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sportsHallDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sports-halls")
    public ResponseEntity<SportsHallDTO> updateSportsHall(@Valid @RequestBody SportsHallDTO sportsHallDTO) throws URISyntaxException {
        log.debug("REST request to update SportsHall : {}", sportsHallDTO);
        if (sportsHallDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SportsHallDTO result = sportsHallService.save(sportsHallDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sportsHallDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sports-halls} : get all the sportsHalls.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sportsHalls in body.
     */
    @GetMapping("/sports-halls")
    public ResponseEntity<List<SportsHallDTO>> getAllSportsHalls(SportsHallCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SportsHalls by criteria: {}", criteria);
        Page<SportsHallDTO> page = sportsHallQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sports-halls/count} : count all the sportsHalls.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sports-halls/count")
    public ResponseEntity<Long> countSportsHalls(SportsHallCriteria criteria) {
        log.debug("REST request to count SportsHalls by criteria: {}", criteria);
        return ResponseEntity.ok().body(sportsHallQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sports-halls/:id} : get the "id" sportsHall.
     *
     * @param id the id of the sportsHallDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sportsHallDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sports-halls/{id}")
    public ResponseEntity<SportsHallDTO> getSportsHall(@PathVariable Long id) {
        log.debug("REST request to get SportsHall : {}", id);
        Optional<SportsHallDTO> sportsHallDTO = sportsHallService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sportsHallDTO);
    }

    /**
     * {@code DELETE  /sports-halls/:id} : delete the "id" sportsHall.
     *
     * @param id the id of the sportsHallDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sports-halls/{id}")
    public ResponseEntity<Void> deleteSportsHall(@PathVariable Long id) {
        log.debug("REST request to delete SportsHall : {}", id);

        sportsHallService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
