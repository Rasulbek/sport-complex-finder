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
import uz.tafakkur.sport.domain.Administrator;
import uz.tafakkur.sport.domain.enumeration.AdminRole;
import uz.tafakkur.sport.repository.AdministratorRepository;
import uz.tafakkur.sport.service.AdministratorService;
import uz.tafakkur.sport.service.dto.AdministratorDTO;
import uz.tafakkur.sport.service.mapper.AdministratorMapper;

/**
 * Integration tests for the {@link AdministratorResource} REST controller.
 */
@SpringBootTest(classes = SportComplexFinderApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AdministratorResourceIT {
    private static final String DEFAULT_CHAT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CHAT_ID = "BBBBBBBBBB";

    private static final AdminRole DEFAULT_ROLE = AdminRole.ADMIN;
    private static final AdminRole UPDATED_ROLE = AdminRole.MODERATOR;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private AdministratorMapper administratorMapper;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdministratorMockMvc;

    private Administrator administrator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Administrator createEntity(EntityManager em) {
        Administrator administrator = new Administrator().chatId(DEFAULT_CHAT_ID).role(DEFAULT_ROLE);
        return administrator;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Administrator createUpdatedEntity(EntityManager em) {
        Administrator administrator = new Administrator().chatId(UPDATED_CHAT_ID).role(UPDATED_ROLE);
        return administrator;
    }

    @BeforeEach
    public void initTest() {
        administrator = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdministrator() throws Exception {
        int databaseSizeBeforeCreate = administratorRepository.findAll().size();
        // Create the Administrator
        AdministratorDTO administratorDTO = administratorMapper.toDto(administrator);
        restAdministratorMockMvc
            .perform(
                post("/api/administrators")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administratorDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Administrator in the database
        List<Administrator> administratorList = administratorRepository.findAll();
        assertThat(administratorList).hasSize(databaseSizeBeforeCreate + 1);
        Administrator testAdministrator = administratorList.get(administratorList.size() - 1);
        assertThat(testAdministrator.getChatId()).isEqualTo(DEFAULT_CHAT_ID);
        assertThat(testAdministrator.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    public void createAdministratorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = administratorRepository.findAll().size();

        // Create the Administrator with an existing ID
        administrator.setId(1L);
        AdministratorDTO administratorDTO = administratorMapper.toDto(administrator);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdministratorMockMvc
            .perform(
                post("/api/administrators")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administratorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Administrator in the database
        List<Administrator> administratorList = administratorRepository.findAll();
        assertThat(administratorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkChatIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = administratorRepository.findAll().size();
        // set the field null
        administrator.setChatId(null);

        // Create the Administrator, which fails.
        AdministratorDTO administratorDTO = administratorMapper.toDto(administrator);

        restAdministratorMockMvc
            .perform(
                post("/api/administrators")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administratorDTO))
            )
            .andExpect(status().isBadRequest());

        List<Administrator> administratorList = administratorRepository.findAll();
        assertThat(administratorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdministrators() throws Exception {
        // Initialize the database
        administratorRepository.saveAndFlush(administrator);

        // Get all the administratorList
        restAdministratorMockMvc
            .perform(get("/api/administrators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(administrator.getId().intValue())))
            .andExpect(jsonPath("$.[*].chatId").value(hasItem(DEFAULT_CHAT_ID)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())));
    }

    @Test
    @Transactional
    public void getAdministrator() throws Exception {
        // Initialize the database
        administratorRepository.saveAndFlush(administrator);

        // Get the administrator
        restAdministratorMockMvc
            .perform(get("/api/administrators/{id}", administrator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(administrator.getId().intValue()))
            .andExpect(jsonPath("$.chatId").value(DEFAULT_CHAT_ID))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAdministrator() throws Exception {
        // Get the administrator
        restAdministratorMockMvc.perform(get("/api/administrators/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdministrator() throws Exception {
        // Initialize the database
        administratorRepository.saveAndFlush(administrator);

        int databaseSizeBeforeUpdate = administratorRepository.findAll().size();

        // Update the administrator
        Administrator updatedAdministrator = administratorRepository.findById(administrator.getId()).get();
        // Disconnect from session so that the updates on updatedAdministrator are not directly saved in db
        em.detach(updatedAdministrator);
        updatedAdministrator.chatId(UPDATED_CHAT_ID).role(UPDATED_ROLE);
        AdministratorDTO administratorDTO = administratorMapper.toDto(updatedAdministrator);

        restAdministratorMockMvc
            .perform(
                put("/api/administrators")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administratorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Administrator in the database
        List<Administrator> administratorList = administratorRepository.findAll();
        assertThat(administratorList).hasSize(databaseSizeBeforeUpdate);
        Administrator testAdministrator = administratorList.get(administratorList.size() - 1);
        assertThat(testAdministrator.getChatId()).isEqualTo(UPDATED_CHAT_ID);
        assertThat(testAdministrator.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    public void updateNonExistingAdministrator() throws Exception {
        int databaseSizeBeforeUpdate = administratorRepository.findAll().size();

        // Create the Administrator
        AdministratorDTO administratorDTO = administratorMapper.toDto(administrator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdministratorMockMvc
            .perform(
                put("/api/administrators")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administratorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Administrator in the database
        List<Administrator> administratorList = administratorRepository.findAll();
        assertThat(administratorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdministrator() throws Exception {
        // Initialize the database
        administratorRepository.saveAndFlush(administrator);

        int databaseSizeBeforeDelete = administratorRepository.findAll().size();

        // Delete the administrator
        restAdministratorMockMvc
            .perform(delete("/api/administrators/{id}", administrator.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Administrator> administratorList = administratorRepository.findAll();
        assertThat(administratorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
