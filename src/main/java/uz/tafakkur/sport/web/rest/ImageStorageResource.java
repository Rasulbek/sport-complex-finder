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
import uz.tafakkur.sport.service.ImageStorageQueryService;
import uz.tafakkur.sport.service.ImageStorageService;
import uz.tafakkur.sport.service.dto.ImageStorageCriteria;
import uz.tafakkur.sport.service.dto.ImageStorageDTO;
import uz.tafakkur.sport.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.tafakkur.sport.domain.ImageStorage}.
 */
@RestController
@RequestMapping("/api")
public class ImageStorageResource {
    private final Logger log = LoggerFactory.getLogger(ImageStorageResource.class);

    private static final String ENTITY_NAME = "imageStorage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImageStorageService imageStorageService;

    private final ImageStorageQueryService imageStorageQueryService;

    public ImageStorageResource(ImageStorageService imageStorageService, ImageStorageQueryService imageStorageQueryService) {
        this.imageStorageService = imageStorageService;
        this.imageStorageQueryService = imageStorageQueryService;
    }

    /**
     * {@code POST  /image-storages} : Create a new imageStorage.
     *
     * @param imageStorageDTO the imageStorageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new imageStorageDTO, or with status {@code 400 (Bad Request)} if the imageStorage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/image-storages")
    public ResponseEntity<ImageStorageDTO> createImageStorage(@Valid @RequestBody ImageStorageDTO imageStorageDTO)
        throws URISyntaxException {
        log.debug("REST request to save ImageStorage : {}", imageStorageDTO);
        if (imageStorageDTO.getId() != null) {
            throw new BadRequestAlertException("A new imageStorage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImageStorageDTO result = imageStorageService.save(imageStorageDTO);
        return ResponseEntity
            .created(new URI("/api/image-storages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /image-storages} : Updates an existing imageStorage.
     *
     * @param imageStorageDTO the imageStorageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imageStorageDTO,
     * or with status {@code 400 (Bad Request)} if the imageStorageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the imageStorageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/image-storages")
    public ResponseEntity<ImageStorageDTO> updateImageStorage(@Valid @RequestBody ImageStorageDTO imageStorageDTO)
        throws URISyntaxException {
        log.debug("REST request to update ImageStorage : {}", imageStorageDTO);
        if (imageStorageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ImageStorageDTO result = imageStorageService.save(imageStorageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imageStorageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /image-storages} : get all the imageStorages.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of imageStorages in body.
     */
    @GetMapping("/image-storages")
    public ResponseEntity<List<ImageStorageDTO>> getAllImageStorages(ImageStorageCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ImageStorages by criteria: {}", criteria);
        Page<ImageStorageDTO> page = imageStorageQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /image-storages/count} : count all the imageStorages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/image-storages/count")
    public ResponseEntity<Long> countImageStorages(ImageStorageCriteria criteria) {
        log.debug("REST request to count ImageStorages by criteria: {}", criteria);
        return ResponseEntity.ok().body(imageStorageQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /image-storages/:id} : get the "id" imageStorage.
     *
     * @param id the id of the imageStorageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imageStorageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/image-storages/{id}")
    public ResponseEntity<ImageStorageDTO> getImageStorage(@PathVariable Long id) {
        log.debug("REST request to get ImageStorage : {}", id);
        Optional<ImageStorageDTO> imageStorageDTO = imageStorageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imageStorageDTO);
    }

    /**
     * {@code DELETE  /image-storages/:id} : delete the "id" imageStorage.
     *
     * @param id the id of the imageStorageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/image-storages/{id}")
    public ResponseEntity<Void> deleteImageStorage(@PathVariable Long id) {
        log.debug("REST request to delete ImageStorage : {}", id);

        imageStorageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
