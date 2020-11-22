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
import uz.tafakkur.sport.service.AdministratorService;
import uz.tafakkur.sport.service.dto.AdministratorDTO;
import uz.tafakkur.sport.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.tafakkur.sport.domain.Administrator}.
 */
@RestController
@RequestMapping("/api")
public class AdministratorResource {
    private final Logger log = LoggerFactory.getLogger(AdministratorResource.class);

    private static final String ENTITY_NAME = "administrator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdministratorService administratorService;

    public AdministratorResource(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    /**
     * {@code POST  /administrators} : Create a new administrator.
     *
     * @param administratorDTO the administratorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new administratorDTO, or with status {@code 400 (Bad Request)} if the administrator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/administrators")
    public ResponseEntity<AdministratorDTO> createAdministrator(@Valid @RequestBody AdministratorDTO administratorDTO)
        throws URISyntaxException {
        log.debug("REST request to save Administrator : {}", administratorDTO);
        if (administratorDTO.getId() != null) {
            throw new BadRequestAlertException("A new administrator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdministratorDTO result = administratorService.save(administratorDTO);
        return ResponseEntity
            .created(new URI("/api/administrators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /administrators} : Updates an existing administrator.
     *
     * @param administratorDTO the administratorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated administratorDTO,
     * or with status {@code 400 (Bad Request)} if the administratorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the administratorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/administrators")
    public ResponseEntity<AdministratorDTO> updateAdministrator(@Valid @RequestBody AdministratorDTO administratorDTO)
        throws URISyntaxException {
        log.debug("REST request to update Administrator : {}", administratorDTO);
        if (administratorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdministratorDTO result = administratorService.save(administratorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, administratorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /administrators} : get all the administrators.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of administrators in body.
     */
    @GetMapping("/administrators")
    public ResponseEntity<List<AdministratorDTO>> getAllAdministrators(Pageable pageable) {
        log.debug("REST request to get a page of Administrators");
        Page<AdministratorDTO> page = administratorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /administrators/:id} : get the "id" administrator.
     *
     * @param id the id of the administratorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the administratorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/administrators/{id}")
    public ResponseEntity<AdministratorDTO> getAdministrator(@PathVariable Long id) {
        log.debug("REST request to get Administrator : {}", id);
        Optional<AdministratorDTO> administratorDTO = administratorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(administratorDTO);
    }

    /**
     * {@code DELETE  /administrators/:id} : delete the "id" administrator.
     *
     * @param id the id of the administratorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/administrators/{id}")
    public ResponseEntity<Void> deleteAdministrator(@PathVariable Long id) {
        log.debug("REST request to delete Administrator : {}", id);

        administratorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
