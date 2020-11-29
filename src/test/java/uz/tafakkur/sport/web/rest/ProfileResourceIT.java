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
import uz.tafakkur.sport.domain.City;
import uz.tafakkur.sport.domain.Profile;
import uz.tafakkur.sport.domain.enumeration.ProfileStatus;
import uz.tafakkur.sport.repository.ProfileRepository;
import uz.tafakkur.sport.service.ProfileQueryService;
import uz.tafakkur.sport.service.ProfileService;
import uz.tafakkur.sport.service.dto.ProfileCriteria;
import uz.tafakkur.sport.service.dto.ProfileDTO;
import uz.tafakkur.sport.service.mapper.ProfileMapper;

/**
 * Integration tests for the {@link ProfileResource} REST controller.
 */
@SpringBootTest(classes = SportComplexFinderApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProfileResourceIT {
    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final Long DEFAULT_CHAT_ID = 1L;
    private static final Long UPDATED_CHAT_ID = 2L;
    private static final Long SMALLER_CHAT_ID = 1L - 1L;

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CHOSEN_LANG = "AA";
    private static final String UPDATED_CHOSEN_LANG = "BB";

    private static final ProfileStatus DEFAULT_STATUS = ProfileStatus.ACTIVE;
    private static final ProfileStatus UPDATED_STATUS = ProfileStatus.BANNED;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileQueryService profileQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfileMockMvc;

    private Profile profile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createEntity(EntityManager em) {
        Profile profile = new Profile()
            .phone(DEFAULT_PHONE)
            .chatId(DEFAULT_CHAT_ID)
            .userName(DEFAULT_USER_NAME)
            .fullName(DEFAULT_FULL_NAME)
            .chosenLang(DEFAULT_CHOSEN_LANG)
            .status(DEFAULT_STATUS);
        return profile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createUpdatedEntity(EntityManager em) {
        Profile profile = new Profile()
            .phone(UPDATED_PHONE)
            .chatId(UPDATED_CHAT_ID)
            .userName(UPDATED_USER_NAME)
            .fullName(UPDATED_FULL_NAME)
            .chosenLang(UPDATED_CHOSEN_LANG)
            .status(UPDATED_STATUS);
        return profile;
    }

    @BeforeEach
    public void initTest() {
        profile = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();
        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);
        restProfileMockMvc
            .perform(post("/api/profiles").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testProfile.getChatId()).isEqualTo(DEFAULT_CHAT_ID);
        assertThat(testProfile.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testProfile.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testProfile.getChosenLang()).isEqualTo(DEFAULT_CHOSEN_LANG);
        assertThat(testProfile.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile with an existing ID
        profile.setId(1L);
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileMockMvc
            .perform(post("/api/profiles").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setStatus(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        restProfileMockMvc
            .perform(post("/api/profiles").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfiles() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList
        restProfileMockMvc
            .perform(get("/api/profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].chatId").value(hasItem(DEFAULT_CHAT_ID.intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].chosenLang").value(hasItem(DEFAULT_CHOSEN_LANG)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get the profile
        restProfileMockMvc
            .perform(get("/api/profiles/{id}", profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profile.getId().intValue()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.chatId").value(DEFAULT_CHAT_ID.intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.chosenLang").value(DEFAULT_CHOSEN_LANG))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getProfilesByIdFiltering() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        Long id = profile.getId();

        defaultProfileShouldBeFound("id.equals=" + id);
        defaultProfileShouldNotBeFound("id.notEquals=" + id);

        defaultProfileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProfileShouldNotBeFound("id.greaterThan=" + id);

        defaultProfileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProfileShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone equals to DEFAULT_PHONE
        defaultProfileShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the profileList where phone equals to UPDATED_PHONE
        defaultProfileShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone not equals to DEFAULT_PHONE
        defaultProfileShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the profileList where phone not equals to UPDATED_PHONE
        defaultProfileShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultProfileShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the profileList where phone equals to UPDATED_PHONE
        defaultProfileShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone is not null
        defaultProfileShouldBeFound("phone.specified=true");

        // Get all the profileList where phone is null
        defaultProfileShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneContainsSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone contains DEFAULT_PHONE
        defaultProfileShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the profileList where phone contains UPDATED_PHONE
        defaultProfileShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone does not contain DEFAULT_PHONE
        defaultProfileShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the profileList where phone does not contain UPDATED_PHONE
        defaultProfileShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllProfilesByChatIdIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where chatId equals to DEFAULT_CHAT_ID
        defaultProfileShouldBeFound("chatId.equals=" + DEFAULT_CHAT_ID);

        // Get all the profileList where chatId equals to UPDATED_CHAT_ID
        defaultProfileShouldNotBeFound("chatId.equals=" + UPDATED_CHAT_ID);
    }

    @Test
    @Transactional
    public void getAllProfilesByChatIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where chatId not equals to DEFAULT_CHAT_ID
        defaultProfileShouldNotBeFound("chatId.notEquals=" + DEFAULT_CHAT_ID);

        // Get all the profileList where chatId not equals to UPDATED_CHAT_ID
        defaultProfileShouldBeFound("chatId.notEquals=" + UPDATED_CHAT_ID);
    }

    @Test
    @Transactional
    public void getAllProfilesByChatIdIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where chatId in DEFAULT_CHAT_ID or UPDATED_CHAT_ID
        defaultProfileShouldBeFound("chatId.in=" + DEFAULT_CHAT_ID + "," + UPDATED_CHAT_ID);

        // Get all the profileList where chatId equals to UPDATED_CHAT_ID
        defaultProfileShouldNotBeFound("chatId.in=" + UPDATED_CHAT_ID);
    }

    @Test
    @Transactional
    public void getAllProfilesByChatIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where chatId is not null
        defaultProfileShouldBeFound("chatId.specified=true");

        // Get all the profileList where chatId is null
        defaultProfileShouldNotBeFound("chatId.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByChatIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where chatId is greater than or equal to DEFAULT_CHAT_ID
        defaultProfileShouldBeFound("chatId.greaterThanOrEqual=" + DEFAULT_CHAT_ID);

        // Get all the profileList where chatId is greater than or equal to UPDATED_CHAT_ID
        defaultProfileShouldNotBeFound("chatId.greaterThanOrEqual=" + UPDATED_CHAT_ID);
    }

    @Test
    @Transactional
    public void getAllProfilesByChatIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where chatId is less than or equal to DEFAULT_CHAT_ID
        defaultProfileShouldBeFound("chatId.lessThanOrEqual=" + DEFAULT_CHAT_ID);

        // Get all the profileList where chatId is less than or equal to SMALLER_CHAT_ID
        defaultProfileShouldNotBeFound("chatId.lessThanOrEqual=" + SMALLER_CHAT_ID);
    }

    @Test
    @Transactional
    public void getAllProfilesByChatIdIsLessThanSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where chatId is less than DEFAULT_CHAT_ID
        defaultProfileShouldNotBeFound("chatId.lessThan=" + DEFAULT_CHAT_ID);

        // Get all the profileList where chatId is less than UPDATED_CHAT_ID
        defaultProfileShouldBeFound("chatId.lessThan=" + UPDATED_CHAT_ID);
    }

    @Test
    @Transactional
    public void getAllProfilesByChatIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where chatId is greater than DEFAULT_CHAT_ID
        defaultProfileShouldNotBeFound("chatId.greaterThan=" + DEFAULT_CHAT_ID);

        // Get all the profileList where chatId is greater than SMALLER_CHAT_ID
        defaultProfileShouldBeFound("chatId.greaterThan=" + SMALLER_CHAT_ID);
    }

    @Test
    @Transactional
    public void getAllProfilesByUserNameIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where userName equals to DEFAULT_USER_NAME
        defaultProfileShouldBeFound("userName.equals=" + DEFAULT_USER_NAME);

        // Get all the profileList where userName equals to UPDATED_USER_NAME
        defaultProfileShouldNotBeFound("userName.equals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllProfilesByUserNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where userName not equals to DEFAULT_USER_NAME
        defaultProfileShouldNotBeFound("userName.notEquals=" + DEFAULT_USER_NAME);

        // Get all the profileList where userName not equals to UPDATED_USER_NAME
        defaultProfileShouldBeFound("userName.notEquals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllProfilesByUserNameIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where userName in DEFAULT_USER_NAME or UPDATED_USER_NAME
        defaultProfileShouldBeFound("userName.in=" + DEFAULT_USER_NAME + "," + UPDATED_USER_NAME);

        // Get all the profileList where userName equals to UPDATED_USER_NAME
        defaultProfileShouldNotBeFound("userName.in=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllProfilesByUserNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where userName is not null
        defaultProfileShouldBeFound("userName.specified=true");

        // Get all the profileList where userName is null
        defaultProfileShouldNotBeFound("userName.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByUserNameContainsSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where userName contains DEFAULT_USER_NAME
        defaultProfileShouldBeFound("userName.contains=" + DEFAULT_USER_NAME);

        // Get all the profileList where userName contains UPDATED_USER_NAME
        defaultProfileShouldNotBeFound("userName.contains=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllProfilesByUserNameNotContainsSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where userName does not contain DEFAULT_USER_NAME
        defaultProfileShouldNotBeFound("userName.doesNotContain=" + DEFAULT_USER_NAME);

        // Get all the profileList where userName does not contain UPDATED_USER_NAME
        defaultProfileShouldBeFound("userName.doesNotContain=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllProfilesByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where fullName equals to DEFAULT_FULL_NAME
        defaultProfileShouldBeFound("fullName.equals=" + DEFAULT_FULL_NAME);

        // Get all the profileList where fullName equals to UPDATED_FULL_NAME
        defaultProfileShouldNotBeFound("fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllProfilesByFullNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where fullName not equals to DEFAULT_FULL_NAME
        defaultProfileShouldNotBeFound("fullName.notEquals=" + DEFAULT_FULL_NAME);

        // Get all the profileList where fullName not equals to UPDATED_FULL_NAME
        defaultProfileShouldBeFound("fullName.notEquals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllProfilesByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where fullName in DEFAULT_FULL_NAME or UPDATED_FULL_NAME
        defaultProfileShouldBeFound("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME);

        // Get all the profileList where fullName equals to UPDATED_FULL_NAME
        defaultProfileShouldNotBeFound("fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllProfilesByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where fullName is not null
        defaultProfileShouldBeFound("fullName.specified=true");

        // Get all the profileList where fullName is null
        defaultProfileShouldNotBeFound("fullName.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByFullNameContainsSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where fullName contains DEFAULT_FULL_NAME
        defaultProfileShouldBeFound("fullName.contains=" + DEFAULT_FULL_NAME);

        // Get all the profileList where fullName contains UPDATED_FULL_NAME
        defaultProfileShouldNotBeFound("fullName.contains=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllProfilesByFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where fullName does not contain DEFAULT_FULL_NAME
        defaultProfileShouldNotBeFound("fullName.doesNotContain=" + DEFAULT_FULL_NAME);

        // Get all the profileList where fullName does not contain UPDATED_FULL_NAME
        defaultProfileShouldBeFound("fullName.doesNotContain=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllProfilesByChosenLangIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where chosenLang equals to DEFAULT_CHOSEN_LANG
        defaultProfileShouldBeFound("chosenLang.equals=" + DEFAULT_CHOSEN_LANG);

        // Get all the profileList where chosenLang equals to UPDATED_CHOSEN_LANG
        defaultProfileShouldNotBeFound("chosenLang.equals=" + UPDATED_CHOSEN_LANG);
    }

    @Test
    @Transactional
    public void getAllProfilesByChosenLangIsNotEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where chosenLang not equals to DEFAULT_CHOSEN_LANG
        defaultProfileShouldNotBeFound("chosenLang.notEquals=" + DEFAULT_CHOSEN_LANG);

        // Get all the profileList where chosenLang not equals to UPDATED_CHOSEN_LANG
        defaultProfileShouldBeFound("chosenLang.notEquals=" + UPDATED_CHOSEN_LANG);
    }

    @Test
    @Transactional
    public void getAllProfilesByChosenLangIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where chosenLang in DEFAULT_CHOSEN_LANG or UPDATED_CHOSEN_LANG
        defaultProfileShouldBeFound("chosenLang.in=" + DEFAULT_CHOSEN_LANG + "," + UPDATED_CHOSEN_LANG);

        // Get all the profileList where chosenLang equals to UPDATED_CHOSEN_LANG
        defaultProfileShouldNotBeFound("chosenLang.in=" + UPDATED_CHOSEN_LANG);
    }

    @Test
    @Transactional
    public void getAllProfilesByChosenLangIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where chosenLang is not null
        defaultProfileShouldBeFound("chosenLang.specified=true");

        // Get all the profileList where chosenLang is null
        defaultProfileShouldNotBeFound("chosenLang.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByChosenLangContainsSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where chosenLang contains DEFAULT_CHOSEN_LANG
        defaultProfileShouldBeFound("chosenLang.contains=" + DEFAULT_CHOSEN_LANG);

        // Get all the profileList where chosenLang contains UPDATED_CHOSEN_LANG
        defaultProfileShouldNotBeFound("chosenLang.contains=" + UPDATED_CHOSEN_LANG);
    }

    @Test
    @Transactional
    public void getAllProfilesByChosenLangNotContainsSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where chosenLang does not contain DEFAULT_CHOSEN_LANG
        defaultProfileShouldNotBeFound("chosenLang.doesNotContain=" + DEFAULT_CHOSEN_LANG);

        // Get all the profileList where chosenLang does not contain UPDATED_CHOSEN_LANG
        defaultProfileShouldBeFound("chosenLang.doesNotContain=" + UPDATED_CHOSEN_LANG);
    }

    @Test
    @Transactional
    public void getAllProfilesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where status equals to DEFAULT_STATUS
        defaultProfileShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the profileList where status equals to UPDATED_STATUS
        defaultProfileShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllProfilesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where status not equals to DEFAULT_STATUS
        defaultProfileShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the profileList where status not equals to UPDATED_STATUS
        defaultProfileShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllProfilesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultProfileShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the profileList where status equals to UPDATED_STATUS
        defaultProfileShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllProfilesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where status is not null
        defaultProfileShouldBeFound("status.specified=true");

        // Get all the profileList where status is null
        defaultProfileShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);
        City city = CityResourceIT.createEntity(em);
        em.persist(city);
        em.flush();
        profile.setCity(city);
        profileRepository.saveAndFlush(profile);
        Long cityId = city.getId();

        // Get all the profileList where city equals to cityId
        defaultProfileShouldBeFound("cityId.equals=" + cityId);

        // Get all the profileList where city equals to cityId + 1
        defaultProfileShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProfileShouldBeFound(String filter) throws Exception {
        restProfileMockMvc
            .perform(get("/api/profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].chatId").value(hasItem(DEFAULT_CHAT_ID.intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].chosenLang").value(hasItem(DEFAULT_CHOSEN_LANG)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restProfileMockMvc
            .perform(get("/api/profiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProfileShouldNotBeFound(String filter) throws Exception {
        restProfileMockMvc
            .perform(get("/api/profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProfileMockMvc
            .perform(get("/api/profiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        Profile updatedProfile = profileRepository.findById(profile.getId()).get();
        // Disconnect from session so that the updates on updatedProfile are not directly saved in db
        em.detach(updatedProfile);
        updatedProfile
            .phone(UPDATED_PHONE)
            .chatId(UPDATED_CHAT_ID)
            .userName(UPDATED_USER_NAME)
            .fullName(UPDATED_FULL_NAME)
            .chosenLang(UPDATED_CHOSEN_LANG)
            .status(UPDATED_STATUS);
        ProfileDTO profileDTO = profileMapper.toDto(updatedProfile);

        restProfileMockMvc
            .perform(put("/api/profiles").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testProfile.getChatId()).isEqualTo(UPDATED_CHAT_ID);
        assertThat(testProfile.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testProfile.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testProfile.getChosenLang()).isEqualTo(UPDATED_CHOSEN_LANG);
        assertThat(testProfile.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(put("/api/profiles").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Delete the profile
        restProfileMockMvc
            .perform(delete("/api/profiles/{id}", profile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
