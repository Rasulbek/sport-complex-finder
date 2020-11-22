package uz.tafakkur.sport.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import uz.tafakkur.sport.SportComplexFinderApp;
import uz.tafakkur.sport.domain.ImageStorage;
import uz.tafakkur.sport.repository.ImageStorageRepository;
import uz.tafakkur.sport.service.ImageStorageQueryService;
import uz.tafakkur.sport.service.ImageStorageService;
import uz.tafakkur.sport.service.dto.ImageStorageCriteria;
import uz.tafakkur.sport.service.dto.ImageStorageDTO;
import uz.tafakkur.sport.service.mapper.ImageStorageMapper;

/**
 * Integration tests for the {@link ImageStorageResource} REST controller.
 */
@SpringBootTest(classes = SportComplexFinderApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ImageStorageResourceIT {
    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Long DEFAULT_SPORTS_HALL_ID = 1L;
    private static final Long UPDATED_SPORTS_HALL_ID = 2L;
    private static final Long SMALLER_SPORTS_HALL_ID = 1L - 1L;

    private static final Boolean DEFAULT_IS_PRIMARY = false;
    private static final Boolean UPDATED_IS_PRIMARY = true;

    @Autowired
    private ImageStorageRepository imageStorageRepository;

    @Autowired
    private ImageStorageMapper imageStorageMapper;

    @Autowired
    private ImageStorageService imageStorageService;

    @Autowired
    private ImageStorageQueryService imageStorageQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImageStorageMockMvc;

    private ImageStorage imageStorage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImageStorage createEntity(EntityManager em) {
        ImageStorage imageStorage = new ImageStorage()
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .sportsHallId(DEFAULT_SPORTS_HALL_ID)
            .isPrimary(DEFAULT_IS_PRIMARY);
        return imageStorage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImageStorage createUpdatedEntity(EntityManager em) {
        ImageStorage imageStorage = new ImageStorage()
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .sportsHallId(UPDATED_SPORTS_HALL_ID)
            .isPrimary(UPDATED_IS_PRIMARY);
        return imageStorage;
    }

    @BeforeEach
    public void initTest() {
        imageStorage = createEntity(em);
    }

    @Test
    @Transactional
    public void createImageStorage() throws Exception {
        int databaseSizeBeforeCreate = imageStorageRepository.findAll().size();
        // Create the ImageStorage
        ImageStorageDTO imageStorageDTO = imageStorageMapper.toDto(imageStorage);
        restImageStorageMockMvc
            .perform(
                post("/api/image-storages")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imageStorageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ImageStorage in the database
        List<ImageStorage> imageStorageList = imageStorageRepository.findAll();
        assertThat(imageStorageList).hasSize(databaseSizeBeforeCreate + 1);
        ImageStorage testImageStorage = imageStorageList.get(imageStorageList.size() - 1);
        assertThat(testImageStorage.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testImageStorage.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testImageStorage.getSportsHallId()).isEqualTo(DEFAULT_SPORTS_HALL_ID);
        assertThat(testImageStorage.isIsPrimary()).isEqualTo(DEFAULT_IS_PRIMARY);
    }

    @Test
    @Transactional
    public void createImageStorageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imageStorageRepository.findAll().size();

        // Create the ImageStorage with an existing ID
        imageStorage.setId(1L);
        ImageStorageDTO imageStorageDTO = imageStorageMapper.toDto(imageStorage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImageStorageMockMvc
            .perform(
                post("/api/image-storages")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imageStorageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImageStorage in the database
        List<ImageStorage> imageStorageList = imageStorageRepository.findAll();
        assertThat(imageStorageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllImageStorages() throws Exception {
        // Initialize the database
        imageStorageRepository.saveAndFlush(imageStorage);

        // Get all the imageStorageList
        restImageStorageMockMvc
            .perform(get("/api/image-storages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imageStorage.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].sportsHallId").value(hasItem(DEFAULT_SPORTS_HALL_ID.intValue())))
            .andExpect(jsonPath("$.[*].isPrimary").value(hasItem(DEFAULT_IS_PRIMARY.booleanValue())));
    }

    @Test
    @Transactional
    public void getImageStorage() throws Exception {
        // Initialize the database
        imageStorageRepository.saveAndFlush(imageStorage);

        // Get the imageStorage
        restImageStorageMockMvc
            .perform(get("/api/image-storages/{id}", imageStorage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(imageStorage.getId().intValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.sportsHallId").value(DEFAULT_SPORTS_HALL_ID.intValue()))
            .andExpect(jsonPath("$.isPrimary").value(DEFAULT_IS_PRIMARY.booleanValue()));
    }

    @Test
    @Transactional
    public void getImageStoragesByIdFiltering() throws Exception {
        // Initialize the database
        imageStorageRepository.saveAndFlush(imageStorage);

        Long id = imageStorage.getId();

        defaultImageStorageShouldBeFound("id.equals=" + id);
        defaultImageStorageShouldNotBeFound("id.notEquals=" + id);

        defaultImageStorageShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultImageStorageShouldNotBeFound("id.greaterThan=" + id);

        defaultImageStorageShouldBeFound("id.lessThanOrEqual=" + id);
        defaultImageStorageShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllImageStoragesBySportsHallIdIsEqualToSomething() throws Exception {
        // Initialize the database
        imageStorageRepository.saveAndFlush(imageStorage);

        // Get all the imageStorageList where sportsHallId equals to DEFAULT_SPORTS_HALL_ID
        defaultImageStorageShouldBeFound("sportsHallId.equals=" + DEFAULT_SPORTS_HALL_ID);

        // Get all the imageStorageList where sportsHallId equals to UPDATED_SPORTS_HALL_ID
        defaultImageStorageShouldNotBeFound("sportsHallId.equals=" + UPDATED_SPORTS_HALL_ID);
    }

    @Test
    @Transactional
    public void getAllImageStoragesBySportsHallIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        imageStorageRepository.saveAndFlush(imageStorage);

        // Get all the imageStorageList where sportsHallId not equals to DEFAULT_SPORTS_HALL_ID
        defaultImageStorageShouldNotBeFound("sportsHallId.notEquals=" + DEFAULT_SPORTS_HALL_ID);

        // Get all the imageStorageList where sportsHallId not equals to UPDATED_SPORTS_HALL_ID
        defaultImageStorageShouldBeFound("sportsHallId.notEquals=" + UPDATED_SPORTS_HALL_ID);
    }

    @Test
    @Transactional
    public void getAllImageStoragesBySportsHallIdIsInShouldWork() throws Exception {
        // Initialize the database
        imageStorageRepository.saveAndFlush(imageStorage);

        // Get all the imageStorageList where sportsHallId in DEFAULT_SPORTS_HALL_ID or UPDATED_SPORTS_HALL_ID
        defaultImageStorageShouldBeFound("sportsHallId.in=" + DEFAULT_SPORTS_HALL_ID + "," + UPDATED_SPORTS_HALL_ID);

        // Get all the imageStorageList where sportsHallId equals to UPDATED_SPORTS_HALL_ID
        defaultImageStorageShouldNotBeFound("sportsHallId.in=" + UPDATED_SPORTS_HALL_ID);
    }

    @Test
    @Transactional
    public void getAllImageStoragesBySportsHallIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        imageStorageRepository.saveAndFlush(imageStorage);

        // Get all the imageStorageList where sportsHallId is not null
        defaultImageStorageShouldBeFound("sportsHallId.specified=true");

        // Get all the imageStorageList where sportsHallId is null
        defaultImageStorageShouldNotBeFound("sportsHallId.specified=false");
    }

    @Test
    @Transactional
    public void getAllImageStoragesBySportsHallIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imageStorageRepository.saveAndFlush(imageStorage);

        // Get all the imageStorageList where sportsHallId is greater than or equal to DEFAULT_SPORTS_HALL_ID
        defaultImageStorageShouldBeFound("sportsHallId.greaterThanOrEqual=" + DEFAULT_SPORTS_HALL_ID);

        // Get all the imageStorageList where sportsHallId is greater than or equal to UPDATED_SPORTS_HALL_ID
        defaultImageStorageShouldNotBeFound("sportsHallId.greaterThanOrEqual=" + UPDATED_SPORTS_HALL_ID);
    }

    @Test
    @Transactional
    public void getAllImageStoragesBySportsHallIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imageStorageRepository.saveAndFlush(imageStorage);

        // Get all the imageStorageList where sportsHallId is less than or equal to DEFAULT_SPORTS_HALL_ID
        defaultImageStorageShouldBeFound("sportsHallId.lessThanOrEqual=" + DEFAULT_SPORTS_HALL_ID);

        // Get all the imageStorageList where sportsHallId is less than or equal to SMALLER_SPORTS_HALL_ID
        defaultImageStorageShouldNotBeFound("sportsHallId.lessThanOrEqual=" + SMALLER_SPORTS_HALL_ID);
    }

    @Test
    @Transactional
    public void getAllImageStoragesBySportsHallIdIsLessThanSomething() throws Exception {
        // Initialize the database
        imageStorageRepository.saveAndFlush(imageStorage);

        // Get all the imageStorageList where sportsHallId is less than DEFAULT_SPORTS_HALL_ID
        defaultImageStorageShouldNotBeFound("sportsHallId.lessThan=" + DEFAULT_SPORTS_HALL_ID);

        // Get all the imageStorageList where sportsHallId is less than UPDATED_SPORTS_HALL_ID
        defaultImageStorageShouldBeFound("sportsHallId.lessThan=" + UPDATED_SPORTS_HALL_ID);
    }

    @Test
    @Transactional
    public void getAllImageStoragesBySportsHallIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        imageStorageRepository.saveAndFlush(imageStorage);

        // Get all the imageStorageList where sportsHallId is greater than DEFAULT_SPORTS_HALL_ID
        defaultImageStorageShouldNotBeFound("sportsHallId.greaterThan=" + DEFAULT_SPORTS_HALL_ID);

        // Get all the imageStorageList where sportsHallId is greater than SMALLER_SPORTS_HALL_ID
        defaultImageStorageShouldBeFound("sportsHallId.greaterThan=" + SMALLER_SPORTS_HALL_ID);
    }

    @Test
    @Transactional
    public void getAllImageStoragesByIsPrimaryIsEqualToSomething() throws Exception {
        // Initialize the database
        imageStorageRepository.saveAndFlush(imageStorage);

        // Get all the imageStorageList where isPrimary equals to DEFAULT_IS_PRIMARY
        defaultImageStorageShouldBeFound("isPrimary.equals=" + DEFAULT_IS_PRIMARY);

        // Get all the imageStorageList where isPrimary equals to UPDATED_IS_PRIMARY
        defaultImageStorageShouldNotBeFound("isPrimary.equals=" + UPDATED_IS_PRIMARY);
    }

    @Test
    @Transactional
    public void getAllImageStoragesByIsPrimaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        imageStorageRepository.saveAndFlush(imageStorage);

        // Get all the imageStorageList where isPrimary not equals to DEFAULT_IS_PRIMARY
        defaultImageStorageShouldNotBeFound("isPrimary.notEquals=" + DEFAULT_IS_PRIMARY);

        // Get all the imageStorageList where isPrimary not equals to UPDATED_IS_PRIMARY
        defaultImageStorageShouldBeFound("isPrimary.notEquals=" + UPDATED_IS_PRIMARY);
    }

    @Test
    @Transactional
    public void getAllImageStoragesByIsPrimaryIsInShouldWork() throws Exception {
        // Initialize the database
        imageStorageRepository.saveAndFlush(imageStorage);

        // Get all the imageStorageList where isPrimary in DEFAULT_IS_PRIMARY or UPDATED_IS_PRIMARY
        defaultImageStorageShouldBeFound("isPrimary.in=" + DEFAULT_IS_PRIMARY + "," + UPDATED_IS_PRIMARY);

        // Get all the imageStorageList where isPrimary equals to UPDATED_IS_PRIMARY
        defaultImageStorageShouldNotBeFound("isPrimary.in=" + UPDATED_IS_PRIMARY);
    }

    @Test
    @Transactional
    public void getAllImageStoragesByIsPrimaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        imageStorageRepository.saveAndFlush(imageStorage);

        // Get all the imageStorageList where isPrimary is not null
        defaultImageStorageShouldBeFound("isPrimary.specified=true");

        // Get all the imageStorageList where isPrimary is null
        defaultImageStorageShouldNotBeFound("isPrimary.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultImageStorageShouldBeFound(String filter) throws Exception {
        restImageStorageMockMvc
            .perform(get("/api/image-storages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imageStorage.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].sportsHallId").value(hasItem(DEFAULT_SPORTS_HALL_ID.intValue())))
            .andExpect(jsonPath("$.[*].isPrimary").value(hasItem(DEFAULT_IS_PRIMARY.booleanValue())));

        // Check, that the count call also returns 1
        restImageStorageMockMvc
            .perform(get("/api/image-storages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultImageStorageShouldNotBeFound(String filter) throws Exception {
        restImageStorageMockMvc
            .perform(get("/api/image-storages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restImageStorageMockMvc
            .perform(get("/api/image-storages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingImageStorage() throws Exception {
        // Get the imageStorage
        restImageStorageMockMvc.perform(get("/api/image-storages/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImageStorage() throws Exception {
        // Initialize the database
        imageStorageRepository.saveAndFlush(imageStorage);

        int databaseSizeBeforeUpdate = imageStorageRepository.findAll().size();

        // Update the imageStorage
        ImageStorage updatedImageStorage = imageStorageRepository.findById(imageStorage.getId()).get();
        // Disconnect from session so that the updates on updatedImageStorage are not directly saved in db
        em.detach(updatedImageStorage);
        updatedImageStorage
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .sportsHallId(UPDATED_SPORTS_HALL_ID)
            .isPrimary(UPDATED_IS_PRIMARY);
        ImageStorageDTO imageStorageDTO = imageStorageMapper.toDto(updatedImageStorage);

        restImageStorageMockMvc
            .perform(
                put("/api/image-storages")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imageStorageDTO))
            )
            .andExpect(status().isOk());

        // Validate the ImageStorage in the database
        List<ImageStorage> imageStorageList = imageStorageRepository.findAll();
        assertThat(imageStorageList).hasSize(databaseSizeBeforeUpdate);
        ImageStorage testImageStorage = imageStorageList.get(imageStorageList.size() - 1);
        assertThat(testImageStorage.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testImageStorage.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testImageStorage.getSportsHallId()).isEqualTo(UPDATED_SPORTS_HALL_ID);
        assertThat(testImageStorage.isIsPrimary()).isEqualTo(UPDATED_IS_PRIMARY);
    }

    @Test
    @Transactional
    public void updateNonExistingImageStorage() throws Exception {
        int databaseSizeBeforeUpdate = imageStorageRepository.findAll().size();

        // Create the ImageStorage
        ImageStorageDTO imageStorageDTO = imageStorageMapper.toDto(imageStorage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImageStorageMockMvc
            .perform(
                put("/api/image-storages")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imageStorageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImageStorage in the database
        List<ImageStorage> imageStorageList = imageStorageRepository.findAll();
        assertThat(imageStorageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImageStorage() throws Exception {
        // Initialize the database
        imageStorageRepository.saveAndFlush(imageStorage);

        int databaseSizeBeforeDelete = imageStorageRepository.findAll().size();

        // Delete the imageStorage
        restImageStorageMockMvc
            .perform(delete("/api/image-storages/{id}", imageStorage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ImageStorage> imageStorageList = imageStorageRepository.findAll();
        assertThat(imageStorageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
