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
import uz.tafakkur.sport.SportComplexFinderApp;
import uz.tafakkur.sport.domain.Category;
import uz.tafakkur.sport.domain.City;
import uz.tafakkur.sport.domain.SportsHall;
import uz.tafakkur.sport.domain.enumeration.FacilityStatus;
import uz.tafakkur.sport.repository.SportsHallRepository;
import uz.tafakkur.sport.service.SportsHallQueryService;
import uz.tafakkur.sport.service.SportsHallService;
import uz.tafakkur.sport.service.dto.SportsHallCriteria;
import uz.tafakkur.sport.service.dto.SportsHallDTO;
import uz.tafakkur.sport.service.mapper.SportsHallMapper;

/**
 * Integration tests for the {@link SportsHallResource} REST controller.
 */
@SpringBootTest(classes = SportComplexFinderApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SportsHallResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_PERSON = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PERSON = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEGRAM_NICK = "AAAAAAAAAA";
    private static final String UPDATED_TELEGRAM_NICK = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;
    private static final Integer SMALLER_PRICE = 1 - 1;

    private static final FacilityStatus DEFAULT_STATUS = FacilityStatus.ENABLED;
    private static final FacilityStatus UPDATED_STATUS = FacilityStatus.DISABLED;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;
    private static final Double SMALLER_LATITUDE = 1D - 1D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;
    private static final Double SMALLER_LONGITUDE = 1D - 1D;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_LANDMARK = "AAAAAAAAAA";
    private static final String UPDATED_LANDMARK = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER_TELEGRAM_ID = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_TELEGRAM_ID = "BBBBBBBBBB";

    @Autowired
    private SportsHallRepository sportsHallRepository;

    @Autowired
    private SportsHallMapper sportsHallMapper;

    @Autowired
    private SportsHallService sportsHallService;

    @Autowired
    private SportsHallQueryService sportsHallQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSportsHallMockMvc;

    private SportsHall sportsHall;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SportsHall createEntity(EntityManager em) {
        SportsHall sportsHall = new SportsHall()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .contactPerson(DEFAULT_CONTACT_PERSON)
            .phone(DEFAULT_PHONE)
            .telegramNick(DEFAULT_TELEGRAM_NICK)
            .price(DEFAULT_PRICE)
            .status(DEFAULT_STATUS)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .address(DEFAULT_ADDRESS)
            .landmark(DEFAULT_LANDMARK)
            .ownerTelegramId(DEFAULT_OWNER_TELEGRAM_ID);
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        sportsHall.setCategory(category);
        // Add required entity
        City city;
        if (TestUtil.findAll(em, City.class).isEmpty()) {
            city = CityResourceIT.createEntity(em);
            em.persist(city);
            em.flush();
        } else {
            city = TestUtil.findAll(em, City.class).get(0);
        }
        sportsHall.setCity(city);
        return sportsHall;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SportsHall createUpdatedEntity(EntityManager em) {
        SportsHall sportsHall = new SportsHall()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .contactPerson(UPDATED_CONTACT_PERSON)
            .phone(UPDATED_PHONE)
            .telegramNick(UPDATED_TELEGRAM_NICK)
            .price(UPDATED_PRICE)
            .status(UPDATED_STATUS)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .address(UPDATED_ADDRESS)
            .landmark(UPDATED_LANDMARK)
            .ownerTelegramId(UPDATED_OWNER_TELEGRAM_ID);
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createUpdatedEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        sportsHall.setCategory(category);
        // Add required entity
        City city;
        if (TestUtil.findAll(em, City.class).isEmpty()) {
            city = CityResourceIT.createUpdatedEntity(em);
            em.persist(city);
            em.flush();
        } else {
            city = TestUtil.findAll(em, City.class).get(0);
        }
        sportsHall.setCity(city);
        return sportsHall;
    }

    @BeforeEach
    public void initTest() {
        sportsHall = createEntity(em);
    }

    @Test
    @Transactional
    public void createSportsHall() throws Exception {
        int databaseSizeBeforeCreate = sportsHallRepository.findAll().size();
        // Create the SportsHall
        SportsHallDTO sportsHallDTO = sportsHallMapper.toDto(sportsHall);
        restSportsHallMockMvc
            .perform(
                post("/api/sports-halls").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sportsHallDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SportsHall in the database
        List<SportsHall> sportsHallList = sportsHallRepository.findAll();
        assertThat(sportsHallList).hasSize(databaseSizeBeforeCreate + 1);
        SportsHall testSportsHall = sportsHallList.get(sportsHallList.size() - 1);
        assertThat(testSportsHall.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSportsHall.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSportsHall.getContactPerson()).isEqualTo(DEFAULT_CONTACT_PERSON);
        assertThat(testSportsHall.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testSportsHall.getTelegramNick()).isEqualTo(DEFAULT_TELEGRAM_NICK);
        assertThat(testSportsHall.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testSportsHall.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSportsHall.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testSportsHall.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testSportsHall.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testSportsHall.getLandmark()).isEqualTo(DEFAULT_LANDMARK);
        assertThat(testSportsHall.getOwnerTelegramId()).isEqualTo(DEFAULT_OWNER_TELEGRAM_ID);
    }

    @Test
    @Transactional
    public void createSportsHallWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sportsHallRepository.findAll().size();

        // Create the SportsHall with an existing ID
        sportsHall.setId(1L);
        SportsHallDTO sportsHallDTO = sportsHallMapper.toDto(sportsHall);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSportsHallMockMvc
            .perform(
                post("/api/sports-halls").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sportsHallDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SportsHall in the database
        List<SportsHall> sportsHallList = sportsHallRepository.findAll();
        assertThat(sportsHallList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sportsHallRepository.findAll().size();
        // set the field null
        sportsHall.setName(null);

        // Create the SportsHall, which fails.
        SportsHallDTO sportsHallDTO = sportsHallMapper.toDto(sportsHall);

        restSportsHallMockMvc
            .perform(
                post("/api/sports-halls").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sportsHallDTO))
            )
            .andExpect(status().isBadRequest());

        List<SportsHall> sportsHallList = sportsHallRepository.findAll();
        assertThat(sportsHallList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactPersonIsRequired() throws Exception {
        int databaseSizeBeforeTest = sportsHallRepository.findAll().size();
        // set the field null
        sportsHall.setContactPerson(null);

        // Create the SportsHall, which fails.
        SportsHallDTO sportsHallDTO = sportsHallMapper.toDto(sportsHall);

        restSportsHallMockMvc
            .perform(
                post("/api/sports-halls").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sportsHallDTO))
            )
            .andExpect(status().isBadRequest());

        List<SportsHall> sportsHallList = sportsHallRepository.findAll();
        assertThat(sportsHallList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = sportsHallRepository.findAll().size();
        // set the field null
        sportsHall.setPrice(null);

        // Create the SportsHall, which fails.
        SportsHallDTO sportsHallDTO = sportsHallMapper.toDto(sportsHall);

        restSportsHallMockMvc
            .perform(
                post("/api/sports-halls").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sportsHallDTO))
            )
            .andExpect(status().isBadRequest());

        List<SportsHall> sportsHallList = sportsHallRepository.findAll();
        assertThat(sportsHallList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = sportsHallRepository.findAll().size();
        // set the field null
        sportsHall.setStatus(null);

        // Create the SportsHall, which fails.
        SportsHallDTO sportsHallDTO = sportsHallMapper.toDto(sportsHall);

        restSportsHallMockMvc
            .perform(
                post("/api/sports-halls").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sportsHallDTO))
            )
            .andExpect(status().isBadRequest());

        List<SportsHall> sportsHallList = sportsHallRepository.findAll();
        assertThat(sportsHallList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSportsHalls() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList
        restSportsHallMockMvc
            .perform(get("/api/sports-halls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sportsHall.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].contactPerson").value(hasItem(DEFAULT_CONTACT_PERSON)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].telegramNick").value(hasItem(DEFAULT_TELEGRAM_NICK)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].landmark").value(hasItem(DEFAULT_LANDMARK)))
            .andExpect(jsonPath("$.[*].ownerTelegramId").value(hasItem(DEFAULT_OWNER_TELEGRAM_ID)));
    }

    @Test
    @Transactional
    public void getSportsHall() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get the sportsHall
        restSportsHallMockMvc
            .perform(get("/api/sports-halls/{id}", sportsHall.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sportsHall.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.contactPerson").value(DEFAULT_CONTACT_PERSON))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.telegramNick").value(DEFAULT_TELEGRAM_NICK))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.landmark").value(DEFAULT_LANDMARK))
            .andExpect(jsonPath("$.ownerTelegramId").value(DEFAULT_OWNER_TELEGRAM_ID));
    }

    @Test
    @Transactional
    public void getSportsHallsByIdFiltering() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        Long id = sportsHall.getId();

        defaultSportsHallShouldBeFound("id.equals=" + id);
        defaultSportsHallShouldNotBeFound("id.notEquals=" + id);

        defaultSportsHallShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSportsHallShouldNotBeFound("id.greaterThan=" + id);

        defaultSportsHallShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSportsHallShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where name equals to DEFAULT_NAME
        defaultSportsHallShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the sportsHallList where name equals to UPDATED_NAME
        defaultSportsHallShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where name not equals to DEFAULT_NAME
        defaultSportsHallShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the sportsHallList where name not equals to UPDATED_NAME
        defaultSportsHallShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSportsHallShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the sportsHallList where name equals to UPDATED_NAME
        defaultSportsHallShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where name is not null
        defaultSportsHallShouldBeFound("name.specified=true");

        // Get all the sportsHallList where name is null
        defaultSportsHallShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllSportsHallsByNameContainsSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where name contains DEFAULT_NAME
        defaultSportsHallShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the sportsHallList where name contains UPDATED_NAME
        defaultSportsHallShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where name does not contain DEFAULT_NAME
        defaultSportsHallShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the sportsHallList where name does not contain UPDATED_NAME
        defaultSportsHallShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where description equals to DEFAULT_DESCRIPTION
        defaultSportsHallShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the sportsHallList where description equals to UPDATED_DESCRIPTION
        defaultSportsHallShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where description not equals to DEFAULT_DESCRIPTION
        defaultSportsHallShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the sportsHallList where description not equals to UPDATED_DESCRIPTION
        defaultSportsHallShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultSportsHallShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the sportsHallList where description equals to UPDATED_DESCRIPTION
        defaultSportsHallShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where description is not null
        defaultSportsHallShouldBeFound("description.specified=true");

        // Get all the sportsHallList where description is null
        defaultSportsHallShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllSportsHallsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where description contains DEFAULT_DESCRIPTION
        defaultSportsHallShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the sportsHallList where description contains UPDATED_DESCRIPTION
        defaultSportsHallShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where description does not contain DEFAULT_DESCRIPTION
        defaultSportsHallShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the sportsHallList where description does not contain UPDATED_DESCRIPTION
        defaultSportsHallShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByContactPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where contactPerson equals to DEFAULT_CONTACT_PERSON
        defaultSportsHallShouldBeFound("contactPerson.equals=" + DEFAULT_CONTACT_PERSON);

        // Get all the sportsHallList where contactPerson equals to UPDATED_CONTACT_PERSON
        defaultSportsHallShouldNotBeFound("contactPerson.equals=" + UPDATED_CONTACT_PERSON);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByContactPersonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where contactPerson not equals to DEFAULT_CONTACT_PERSON
        defaultSportsHallShouldNotBeFound("contactPerson.notEquals=" + DEFAULT_CONTACT_PERSON);

        // Get all the sportsHallList where contactPerson not equals to UPDATED_CONTACT_PERSON
        defaultSportsHallShouldBeFound("contactPerson.notEquals=" + UPDATED_CONTACT_PERSON);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByContactPersonIsInShouldWork() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where contactPerson in DEFAULT_CONTACT_PERSON or UPDATED_CONTACT_PERSON
        defaultSportsHallShouldBeFound("contactPerson.in=" + DEFAULT_CONTACT_PERSON + "," + UPDATED_CONTACT_PERSON);

        // Get all the sportsHallList where contactPerson equals to UPDATED_CONTACT_PERSON
        defaultSportsHallShouldNotBeFound("contactPerson.in=" + UPDATED_CONTACT_PERSON);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByContactPersonIsNullOrNotNull() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where contactPerson is not null
        defaultSportsHallShouldBeFound("contactPerson.specified=true");

        // Get all the sportsHallList where contactPerson is null
        defaultSportsHallShouldNotBeFound("contactPerson.specified=false");
    }

    @Test
    @Transactional
    public void getAllSportsHallsByContactPersonContainsSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where contactPerson contains DEFAULT_CONTACT_PERSON
        defaultSportsHallShouldBeFound("contactPerson.contains=" + DEFAULT_CONTACT_PERSON);

        // Get all the sportsHallList where contactPerson contains UPDATED_CONTACT_PERSON
        defaultSportsHallShouldNotBeFound("contactPerson.contains=" + UPDATED_CONTACT_PERSON);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByContactPersonNotContainsSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where contactPerson does not contain DEFAULT_CONTACT_PERSON
        defaultSportsHallShouldNotBeFound("contactPerson.doesNotContain=" + DEFAULT_CONTACT_PERSON);

        // Get all the sportsHallList where contactPerson does not contain UPDATED_CONTACT_PERSON
        defaultSportsHallShouldBeFound("contactPerson.doesNotContain=" + UPDATED_CONTACT_PERSON);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where phone equals to DEFAULT_PHONE
        defaultSportsHallShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the sportsHallList where phone equals to UPDATED_PHONE
        defaultSportsHallShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where phone not equals to DEFAULT_PHONE
        defaultSportsHallShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the sportsHallList where phone not equals to UPDATED_PHONE
        defaultSportsHallShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultSportsHallShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the sportsHallList where phone equals to UPDATED_PHONE
        defaultSportsHallShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where phone is not null
        defaultSportsHallShouldBeFound("phone.specified=true");

        // Get all the sportsHallList where phone is null
        defaultSportsHallShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    public void getAllSportsHallsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where phone contains DEFAULT_PHONE
        defaultSportsHallShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the sportsHallList where phone contains UPDATED_PHONE
        defaultSportsHallShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where phone does not contain DEFAULT_PHONE
        defaultSportsHallShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the sportsHallList where phone does not contain UPDATED_PHONE
        defaultSportsHallShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByTelegramNickIsEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where telegramNick equals to DEFAULT_TELEGRAM_NICK
        defaultSportsHallShouldBeFound("telegramNick.equals=" + DEFAULT_TELEGRAM_NICK);

        // Get all the sportsHallList where telegramNick equals to UPDATED_TELEGRAM_NICK
        defaultSportsHallShouldNotBeFound("telegramNick.equals=" + UPDATED_TELEGRAM_NICK);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByTelegramNickIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where telegramNick not equals to DEFAULT_TELEGRAM_NICK
        defaultSportsHallShouldNotBeFound("telegramNick.notEquals=" + DEFAULT_TELEGRAM_NICK);

        // Get all the sportsHallList where telegramNick not equals to UPDATED_TELEGRAM_NICK
        defaultSportsHallShouldBeFound("telegramNick.notEquals=" + UPDATED_TELEGRAM_NICK);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByTelegramNickIsInShouldWork() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where telegramNick in DEFAULT_TELEGRAM_NICK or UPDATED_TELEGRAM_NICK
        defaultSportsHallShouldBeFound("telegramNick.in=" + DEFAULT_TELEGRAM_NICK + "," + UPDATED_TELEGRAM_NICK);

        // Get all the sportsHallList where telegramNick equals to UPDATED_TELEGRAM_NICK
        defaultSportsHallShouldNotBeFound("telegramNick.in=" + UPDATED_TELEGRAM_NICK);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByTelegramNickIsNullOrNotNull() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where telegramNick is not null
        defaultSportsHallShouldBeFound("telegramNick.specified=true");

        // Get all the sportsHallList where telegramNick is null
        defaultSportsHallShouldNotBeFound("telegramNick.specified=false");
    }

    @Test
    @Transactional
    public void getAllSportsHallsByTelegramNickContainsSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where telegramNick contains DEFAULT_TELEGRAM_NICK
        defaultSportsHallShouldBeFound("telegramNick.contains=" + DEFAULT_TELEGRAM_NICK);

        // Get all the sportsHallList where telegramNick contains UPDATED_TELEGRAM_NICK
        defaultSportsHallShouldNotBeFound("telegramNick.contains=" + UPDATED_TELEGRAM_NICK);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByTelegramNickNotContainsSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where telegramNick does not contain DEFAULT_TELEGRAM_NICK
        defaultSportsHallShouldNotBeFound("telegramNick.doesNotContain=" + DEFAULT_TELEGRAM_NICK);

        // Get all the sportsHallList where telegramNick does not contain UPDATED_TELEGRAM_NICK
        defaultSportsHallShouldBeFound("telegramNick.doesNotContain=" + UPDATED_TELEGRAM_NICK);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where price equals to DEFAULT_PRICE
        defaultSportsHallShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the sportsHallList where price equals to UPDATED_PRICE
        defaultSportsHallShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where price not equals to DEFAULT_PRICE
        defaultSportsHallShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the sportsHallList where price not equals to UPDATED_PRICE
        defaultSportsHallShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultSportsHallShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the sportsHallList where price equals to UPDATED_PRICE
        defaultSportsHallShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where price is not null
        defaultSportsHallShouldBeFound("price.specified=true");

        // Get all the sportsHallList where price is null
        defaultSportsHallShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllSportsHallsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where price is greater than or equal to DEFAULT_PRICE
        defaultSportsHallShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the sportsHallList where price is greater than or equal to UPDATED_PRICE
        defaultSportsHallShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where price is less than or equal to DEFAULT_PRICE
        defaultSportsHallShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the sportsHallList where price is less than or equal to SMALLER_PRICE
        defaultSportsHallShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where price is less than DEFAULT_PRICE
        defaultSportsHallShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the sportsHallList where price is less than UPDATED_PRICE
        defaultSportsHallShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where price is greater than DEFAULT_PRICE
        defaultSportsHallShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the sportsHallList where price is greater than SMALLER_PRICE
        defaultSportsHallShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where status equals to DEFAULT_STATUS
        defaultSportsHallShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the sportsHallList where status equals to UPDATED_STATUS
        defaultSportsHallShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where status not equals to DEFAULT_STATUS
        defaultSportsHallShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the sportsHallList where status not equals to UPDATED_STATUS
        defaultSportsHallShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultSportsHallShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the sportsHallList where status equals to UPDATED_STATUS
        defaultSportsHallShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where status is not null
        defaultSportsHallShouldBeFound("status.specified=true");

        // Get all the sportsHallList where status is null
        defaultSportsHallShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllSportsHallsByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where latitude equals to DEFAULT_LATITUDE
        defaultSportsHallShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the sportsHallList where latitude equals to UPDATED_LATITUDE
        defaultSportsHallShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByLatitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where latitude not equals to DEFAULT_LATITUDE
        defaultSportsHallShouldNotBeFound("latitude.notEquals=" + DEFAULT_LATITUDE);

        // Get all the sportsHallList where latitude not equals to UPDATED_LATITUDE
        defaultSportsHallShouldBeFound("latitude.notEquals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultSportsHallShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the sportsHallList where latitude equals to UPDATED_LATITUDE
        defaultSportsHallShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where latitude is not null
        defaultSportsHallShouldBeFound("latitude.specified=true");

        // Get all the sportsHallList where latitude is null
        defaultSportsHallShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllSportsHallsByLatitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where latitude is greater than or equal to DEFAULT_LATITUDE
        defaultSportsHallShouldBeFound("latitude.greaterThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the sportsHallList where latitude is greater than or equal to UPDATED_LATITUDE
        defaultSportsHallShouldNotBeFound("latitude.greaterThanOrEqual=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByLatitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where latitude is less than or equal to DEFAULT_LATITUDE
        defaultSportsHallShouldBeFound("latitude.lessThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the sportsHallList where latitude is less than or equal to SMALLER_LATITUDE
        defaultSportsHallShouldNotBeFound("latitude.lessThanOrEqual=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByLatitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where latitude is less than DEFAULT_LATITUDE
        defaultSportsHallShouldNotBeFound("latitude.lessThan=" + DEFAULT_LATITUDE);

        // Get all the sportsHallList where latitude is less than UPDATED_LATITUDE
        defaultSportsHallShouldBeFound("latitude.lessThan=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByLatitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where latitude is greater than DEFAULT_LATITUDE
        defaultSportsHallShouldNotBeFound("latitude.greaterThan=" + DEFAULT_LATITUDE);

        // Get all the sportsHallList where latitude is greater than SMALLER_LATITUDE
        defaultSportsHallShouldBeFound("latitude.greaterThan=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where longitude equals to DEFAULT_LONGITUDE
        defaultSportsHallShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the sportsHallList where longitude equals to UPDATED_LONGITUDE
        defaultSportsHallShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where longitude not equals to DEFAULT_LONGITUDE
        defaultSportsHallShouldNotBeFound("longitude.notEquals=" + DEFAULT_LONGITUDE);

        // Get all the sportsHallList where longitude not equals to UPDATED_LONGITUDE
        defaultSportsHallShouldBeFound("longitude.notEquals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultSportsHallShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the sportsHallList where longitude equals to UPDATED_LONGITUDE
        defaultSportsHallShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where longitude is not null
        defaultSportsHallShouldBeFound("longitude.specified=true");

        // Get all the sportsHallList where longitude is null
        defaultSportsHallShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllSportsHallsByLongitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where longitude is greater than or equal to DEFAULT_LONGITUDE
        defaultSportsHallShouldBeFound("longitude.greaterThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the sportsHallList where longitude is greater than or equal to UPDATED_LONGITUDE
        defaultSportsHallShouldNotBeFound("longitude.greaterThanOrEqual=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByLongitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where longitude is less than or equal to DEFAULT_LONGITUDE
        defaultSportsHallShouldBeFound("longitude.lessThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the sportsHallList where longitude is less than or equal to SMALLER_LONGITUDE
        defaultSportsHallShouldNotBeFound("longitude.lessThanOrEqual=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByLongitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where longitude is less than DEFAULT_LONGITUDE
        defaultSportsHallShouldNotBeFound("longitude.lessThan=" + DEFAULT_LONGITUDE);

        // Get all the sportsHallList where longitude is less than UPDATED_LONGITUDE
        defaultSportsHallShouldBeFound("longitude.lessThan=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByLongitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where longitude is greater than DEFAULT_LONGITUDE
        defaultSportsHallShouldNotBeFound("longitude.greaterThan=" + DEFAULT_LONGITUDE);

        // Get all the sportsHallList where longitude is greater than SMALLER_LONGITUDE
        defaultSportsHallShouldBeFound("longitude.greaterThan=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where address equals to DEFAULT_ADDRESS
        defaultSportsHallShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the sportsHallList where address equals to UPDATED_ADDRESS
        defaultSportsHallShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where address not equals to DEFAULT_ADDRESS
        defaultSportsHallShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the sportsHallList where address not equals to UPDATED_ADDRESS
        defaultSportsHallShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultSportsHallShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the sportsHallList where address equals to UPDATED_ADDRESS
        defaultSportsHallShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where address is not null
        defaultSportsHallShouldBeFound("address.specified=true");

        // Get all the sportsHallList where address is null
        defaultSportsHallShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    public void getAllSportsHallsByAddressContainsSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where address contains DEFAULT_ADDRESS
        defaultSportsHallShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the sportsHallList where address contains UPDATED_ADDRESS
        defaultSportsHallShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where address does not contain DEFAULT_ADDRESS
        defaultSportsHallShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the sportsHallList where address does not contain UPDATED_ADDRESS
        defaultSportsHallShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByLandmarkIsEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where landmark equals to DEFAULT_LANDMARK
        defaultSportsHallShouldBeFound("landmark.equals=" + DEFAULT_LANDMARK);

        // Get all the sportsHallList where landmark equals to UPDATED_LANDMARK
        defaultSportsHallShouldNotBeFound("landmark.equals=" + UPDATED_LANDMARK);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByLandmarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where landmark not equals to DEFAULT_LANDMARK
        defaultSportsHallShouldNotBeFound("landmark.notEquals=" + DEFAULT_LANDMARK);

        // Get all the sportsHallList where landmark not equals to UPDATED_LANDMARK
        defaultSportsHallShouldBeFound("landmark.notEquals=" + UPDATED_LANDMARK);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByLandmarkIsInShouldWork() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where landmark in DEFAULT_LANDMARK or UPDATED_LANDMARK
        defaultSportsHallShouldBeFound("landmark.in=" + DEFAULT_LANDMARK + "," + UPDATED_LANDMARK);

        // Get all the sportsHallList where landmark equals to UPDATED_LANDMARK
        defaultSportsHallShouldNotBeFound("landmark.in=" + UPDATED_LANDMARK);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByLandmarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where landmark is not null
        defaultSportsHallShouldBeFound("landmark.specified=true");

        // Get all the sportsHallList where landmark is null
        defaultSportsHallShouldNotBeFound("landmark.specified=false");
    }

    @Test
    @Transactional
    public void getAllSportsHallsByLandmarkContainsSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where landmark contains DEFAULT_LANDMARK
        defaultSportsHallShouldBeFound("landmark.contains=" + DEFAULT_LANDMARK);

        // Get all the sportsHallList where landmark contains UPDATED_LANDMARK
        defaultSportsHallShouldNotBeFound("landmark.contains=" + UPDATED_LANDMARK);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByLandmarkNotContainsSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where landmark does not contain DEFAULT_LANDMARK
        defaultSportsHallShouldNotBeFound("landmark.doesNotContain=" + DEFAULT_LANDMARK);

        // Get all the sportsHallList where landmark does not contain UPDATED_LANDMARK
        defaultSportsHallShouldBeFound("landmark.doesNotContain=" + UPDATED_LANDMARK);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByOwnerTelegramIdIsEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where ownerTelegramId equals to DEFAULT_OWNER_TELEGRAM_ID
        defaultSportsHallShouldBeFound("ownerTelegramId.equals=" + DEFAULT_OWNER_TELEGRAM_ID);

        // Get all the sportsHallList where ownerTelegramId equals to UPDATED_OWNER_TELEGRAM_ID
        defaultSportsHallShouldNotBeFound("ownerTelegramId.equals=" + UPDATED_OWNER_TELEGRAM_ID);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByOwnerTelegramIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where ownerTelegramId not equals to DEFAULT_OWNER_TELEGRAM_ID
        defaultSportsHallShouldNotBeFound("ownerTelegramId.notEquals=" + DEFAULT_OWNER_TELEGRAM_ID);

        // Get all the sportsHallList where ownerTelegramId not equals to UPDATED_OWNER_TELEGRAM_ID
        defaultSportsHallShouldBeFound("ownerTelegramId.notEquals=" + UPDATED_OWNER_TELEGRAM_ID);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByOwnerTelegramIdIsInShouldWork() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where ownerTelegramId in DEFAULT_OWNER_TELEGRAM_ID or UPDATED_OWNER_TELEGRAM_ID
        defaultSportsHallShouldBeFound("ownerTelegramId.in=" + DEFAULT_OWNER_TELEGRAM_ID + "," + UPDATED_OWNER_TELEGRAM_ID);

        // Get all the sportsHallList where ownerTelegramId equals to UPDATED_OWNER_TELEGRAM_ID
        defaultSportsHallShouldNotBeFound("ownerTelegramId.in=" + UPDATED_OWNER_TELEGRAM_ID);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByOwnerTelegramIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where ownerTelegramId is not null
        defaultSportsHallShouldBeFound("ownerTelegramId.specified=true");

        // Get all the sportsHallList where ownerTelegramId is null
        defaultSportsHallShouldNotBeFound("ownerTelegramId.specified=false");
    }

    @Test
    @Transactional
    public void getAllSportsHallsByOwnerTelegramIdContainsSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where ownerTelegramId contains DEFAULT_OWNER_TELEGRAM_ID
        defaultSportsHallShouldBeFound("ownerTelegramId.contains=" + DEFAULT_OWNER_TELEGRAM_ID);

        // Get all the sportsHallList where ownerTelegramId contains UPDATED_OWNER_TELEGRAM_ID
        defaultSportsHallShouldNotBeFound("ownerTelegramId.contains=" + UPDATED_OWNER_TELEGRAM_ID);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByOwnerTelegramIdNotContainsSomething() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        // Get all the sportsHallList where ownerTelegramId does not contain DEFAULT_OWNER_TELEGRAM_ID
        defaultSportsHallShouldNotBeFound("ownerTelegramId.doesNotContain=" + DEFAULT_OWNER_TELEGRAM_ID);

        // Get all the sportsHallList where ownerTelegramId does not contain UPDATED_OWNER_TELEGRAM_ID
        defaultSportsHallShouldBeFound("ownerTelegramId.doesNotContain=" + UPDATED_OWNER_TELEGRAM_ID);
    }

    @Test
    @Transactional
    public void getAllSportsHallsByCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        Category category = sportsHall.getCategory();
        sportsHallRepository.saveAndFlush(sportsHall);
        Long categoryId = category.getId();

        // Get all the sportsHallList where category equals to categoryId
        defaultSportsHallShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the sportsHallList where category equals to categoryId + 1
        defaultSportsHallShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    @Test
    @Transactional
    public void getAllSportsHallsByCityIsEqualToSomething() throws Exception {
        // Get already existing entity
        City city = sportsHall.getCity();
        sportsHallRepository.saveAndFlush(sportsHall);
        Long cityId = city.getId();

        // Get all the sportsHallList where city equals to cityId
        defaultSportsHallShouldBeFound("cityId.equals=" + cityId);

        // Get all the sportsHallList where city equals to cityId + 1
        defaultSportsHallShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSportsHallShouldBeFound(String filter) throws Exception {
        restSportsHallMockMvc
            .perform(get("/api/sports-halls?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sportsHall.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].contactPerson").value(hasItem(DEFAULT_CONTACT_PERSON)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].telegramNick").value(hasItem(DEFAULT_TELEGRAM_NICK)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].landmark").value(hasItem(DEFAULT_LANDMARK)))
            .andExpect(jsonPath("$.[*].ownerTelegramId").value(hasItem(DEFAULT_OWNER_TELEGRAM_ID)));

        // Check, that the count call also returns 1
        restSportsHallMockMvc
            .perform(get("/api/sports-halls/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSportsHallShouldNotBeFound(String filter) throws Exception {
        restSportsHallMockMvc
            .perform(get("/api/sports-halls?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSportsHallMockMvc
            .perform(get("/api/sports-halls/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSportsHall() throws Exception {
        // Get the sportsHall
        restSportsHallMockMvc.perform(get("/api/sports-halls/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSportsHall() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        int databaseSizeBeforeUpdate = sportsHallRepository.findAll().size();

        // Update the sportsHall
        SportsHall updatedSportsHall = sportsHallRepository.findById(sportsHall.getId()).get();
        // Disconnect from session so that the updates on updatedSportsHall are not directly saved in db
        em.detach(updatedSportsHall);
        updatedSportsHall
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .contactPerson(UPDATED_CONTACT_PERSON)
            .phone(UPDATED_PHONE)
            .telegramNick(UPDATED_TELEGRAM_NICK)
            .price(UPDATED_PRICE)
            .status(UPDATED_STATUS)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .address(UPDATED_ADDRESS)
            .landmark(UPDATED_LANDMARK)
            .ownerTelegramId(UPDATED_OWNER_TELEGRAM_ID);
        SportsHallDTO sportsHallDTO = sportsHallMapper.toDto(updatedSportsHall);

        restSportsHallMockMvc
            .perform(
                put("/api/sports-halls").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sportsHallDTO))
            )
            .andExpect(status().isOk());

        // Validate the SportsHall in the database
        List<SportsHall> sportsHallList = sportsHallRepository.findAll();
        assertThat(sportsHallList).hasSize(databaseSizeBeforeUpdate);
        SportsHall testSportsHall = sportsHallList.get(sportsHallList.size() - 1);
        assertThat(testSportsHall.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSportsHall.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSportsHall.getContactPerson()).isEqualTo(UPDATED_CONTACT_PERSON);
        assertThat(testSportsHall.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testSportsHall.getTelegramNick()).isEqualTo(UPDATED_TELEGRAM_NICK);
        assertThat(testSportsHall.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testSportsHall.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSportsHall.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testSportsHall.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testSportsHall.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testSportsHall.getLandmark()).isEqualTo(UPDATED_LANDMARK);
        assertThat(testSportsHall.getOwnerTelegramId()).isEqualTo(UPDATED_OWNER_TELEGRAM_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingSportsHall() throws Exception {
        int databaseSizeBeforeUpdate = sportsHallRepository.findAll().size();

        // Create the SportsHall
        SportsHallDTO sportsHallDTO = sportsHallMapper.toDto(sportsHall);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSportsHallMockMvc
            .perform(
                put("/api/sports-halls").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sportsHallDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SportsHall in the database
        List<SportsHall> sportsHallList = sportsHallRepository.findAll();
        assertThat(sportsHallList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSportsHall() throws Exception {
        // Initialize the database
        sportsHallRepository.saveAndFlush(sportsHall);

        int databaseSizeBeforeDelete = sportsHallRepository.findAll().size();

        // Delete the sportsHall
        restSportsHallMockMvc
            .perform(delete("/api/sports-halls/{id}", sportsHall.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SportsHall> sportsHallList = sportsHallRepository.findAll();
        assertThat(sportsHallList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
