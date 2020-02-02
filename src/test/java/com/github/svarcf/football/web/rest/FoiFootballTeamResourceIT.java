package com.github.svarcf.football.web.rest;

import com.github.svarcf.football.FootballCrudApp;
import com.github.svarcf.football.config.SecurityBeanOverrideConfiguration;
import com.github.svarcf.football.domain.FoiFootballTeam;
import com.github.svarcf.football.repository.FoiFootballTeamRepository;
import com.github.svarcf.football.service.FoiFootballTeamService;
import com.github.svarcf.football.service.dto.FoiFootballTeamDTO;
import com.github.svarcf.football.service.mapper.FoiFootballTeamMapper;
import com.github.svarcf.football.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.github.svarcf.football.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FoiFootballTeamResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, FootballCrudApp.class})
public class FoiFootballTeamResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO = "BBBBBBBBBB";

    private static final String DEFAULT_VENUE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VENUE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VENUE_CITY = "AAAAAAAAAA";
    private static final String UPDATED_VENUE_CITY = "BBBBBBBBBB";

    @Autowired
    private FoiFootballTeamRepository foiFootballTeamRepository;

    @Autowired
    private FoiFootballTeamMapper foiFootballTeamMapper;

    @Autowired
    private FoiFootballTeamService foiFootballTeamService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restFoiFootballTeamMockMvc;

    private FoiFootballTeam foiFootballTeam;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FoiFootballTeamResource foiFootballTeamResource = new FoiFootballTeamResource(foiFootballTeamService);
        this.restFoiFootballTeamMockMvc = MockMvcBuilders.standaloneSetup(foiFootballTeamResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoiFootballTeam createEntity(EntityManager em) {
        FoiFootballTeam foiFootballTeam = new FoiFootballTeam()
            .name(DEFAULT_NAME)
            .logo(DEFAULT_LOGO)
            .venueName(DEFAULT_VENUE_NAME)
            .venueCity(DEFAULT_VENUE_CITY);
        return foiFootballTeam;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoiFootballTeam createUpdatedEntity(EntityManager em) {
        FoiFootballTeam foiFootballTeam = new FoiFootballTeam()
            .name(UPDATED_NAME)
            .logo(UPDATED_LOGO)
            .venueName(UPDATED_VENUE_NAME)
            .venueCity(UPDATED_VENUE_CITY);
        return foiFootballTeam;
    }

    @BeforeEach
    public void initTest() {
        foiFootballTeam = createEntity(em);
    }

    @Test
    @Transactional
    public void createFoiFootballTeam() throws Exception {
        int databaseSizeBeforeCreate = foiFootballTeamRepository.findAll().size();

        // Create the FoiFootballTeam
        FoiFootballTeamDTO foiFootballTeamDTO = foiFootballTeamMapper.toDto(foiFootballTeam);
        restFoiFootballTeamMockMvc.perform(post("/api/foi-football-teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballTeamDTO)))
            .andExpect(status().isCreated());

        // Validate the FoiFootballTeam in the database
        List<FoiFootballTeam> foiFootballTeamList = foiFootballTeamRepository.findAll();
        assertThat(foiFootballTeamList).hasSize(databaseSizeBeforeCreate + 1);
        FoiFootballTeam testFoiFootballTeam = foiFootballTeamList.get(foiFootballTeamList.size() - 1);
        assertThat(testFoiFootballTeam.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFoiFootballTeam.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testFoiFootballTeam.getVenueName()).isEqualTo(DEFAULT_VENUE_NAME);
        assertThat(testFoiFootballTeam.getVenueCity()).isEqualTo(DEFAULT_VENUE_CITY);
    }

    @Test
    @Transactional
    public void createFoiFootballTeamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = foiFootballTeamRepository.findAll().size();

        // Create the FoiFootballTeam with an existing ID
        foiFootballTeam.setId(1L);
        FoiFootballTeamDTO foiFootballTeamDTO = foiFootballTeamMapper.toDto(foiFootballTeam);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoiFootballTeamMockMvc.perform(post("/api/foi-football-teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballTeamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FoiFootballTeam in the database
        List<FoiFootballTeam> foiFootballTeamList = foiFootballTeamRepository.findAll();
        assertThat(foiFootballTeamList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = foiFootballTeamRepository.findAll().size();
        // set the field null
        foiFootballTeam.setName(null);

        // Create the FoiFootballTeam, which fails.
        FoiFootballTeamDTO foiFootballTeamDTO = foiFootballTeamMapper.toDto(foiFootballTeam);

        restFoiFootballTeamMockMvc.perform(post("/api/foi-football-teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballTeamDTO)))
            .andExpect(status().isBadRequest());

        List<FoiFootballTeam> foiFootballTeamList = foiFootballTeamRepository.findAll();
        assertThat(foiFootballTeamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVenueNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = foiFootballTeamRepository.findAll().size();
        // set the field null
        foiFootballTeam.setVenueName(null);

        // Create the FoiFootballTeam, which fails.
        FoiFootballTeamDTO foiFootballTeamDTO = foiFootballTeamMapper.toDto(foiFootballTeam);

        restFoiFootballTeamMockMvc.perform(post("/api/foi-football-teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballTeamDTO)))
            .andExpect(status().isBadRequest());

        List<FoiFootballTeam> foiFootballTeamList = foiFootballTeamRepository.findAll();
        assertThat(foiFootballTeamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVenueCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = foiFootballTeamRepository.findAll().size();
        // set the field null
        foiFootballTeam.setVenueCity(null);

        // Create the FoiFootballTeam, which fails.
        FoiFootballTeamDTO foiFootballTeamDTO = foiFootballTeamMapper.toDto(foiFootballTeam);

        restFoiFootballTeamMockMvc.perform(post("/api/foi-football-teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballTeamDTO)))
            .andExpect(status().isBadRequest());

        List<FoiFootballTeam> foiFootballTeamList = foiFootballTeamRepository.findAll();
        assertThat(foiFootballTeamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFoiFootballTeams() throws Exception {
        // Initialize the database
        foiFootballTeamRepository.saveAndFlush(foiFootballTeam);

        // Get all the foiFootballTeamList
        restFoiFootballTeamMockMvc.perform(get("/api/foi-football-teams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foiFootballTeam.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.[*].venueName").value(hasItem(DEFAULT_VENUE_NAME)))
            .andExpect(jsonPath("$.[*].venueCity").value(hasItem(DEFAULT_VENUE_CITY)));
    }
    
    @Test
    @Transactional
    public void getFoiFootballTeam() throws Exception {
        // Initialize the database
        foiFootballTeamRepository.saveAndFlush(foiFootballTeam);

        // Get the foiFootballTeam
        restFoiFootballTeamMockMvc.perform(get("/api/foi-football-teams/{id}", foiFootballTeam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(foiFootballTeam.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO))
            .andExpect(jsonPath("$.venueName").value(DEFAULT_VENUE_NAME))
            .andExpect(jsonPath("$.venueCity").value(DEFAULT_VENUE_CITY));
    }

    @Test
    @Transactional
    public void getNonExistingFoiFootballTeam() throws Exception {
        // Get the foiFootballTeam
        restFoiFootballTeamMockMvc.perform(get("/api/foi-football-teams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFoiFootballTeam() throws Exception {
        // Initialize the database
        foiFootballTeamRepository.saveAndFlush(foiFootballTeam);

        int databaseSizeBeforeUpdate = foiFootballTeamRepository.findAll().size();

        // Update the foiFootballTeam
        FoiFootballTeam updatedFoiFootballTeam = foiFootballTeamRepository.findById(foiFootballTeam.getId()).get();
        // Disconnect from session so that the updates on updatedFoiFootballTeam are not directly saved in db
        em.detach(updatedFoiFootballTeam);
        updatedFoiFootballTeam
            .name(UPDATED_NAME)
            .logo(UPDATED_LOGO)
            .venueName(UPDATED_VENUE_NAME)
            .venueCity(UPDATED_VENUE_CITY);
        FoiFootballTeamDTO foiFootballTeamDTO = foiFootballTeamMapper.toDto(updatedFoiFootballTeam);

        restFoiFootballTeamMockMvc.perform(put("/api/foi-football-teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballTeamDTO)))
            .andExpect(status().isOk());

        // Validate the FoiFootballTeam in the database
        List<FoiFootballTeam> foiFootballTeamList = foiFootballTeamRepository.findAll();
        assertThat(foiFootballTeamList).hasSize(databaseSizeBeforeUpdate);
        FoiFootballTeam testFoiFootballTeam = foiFootballTeamList.get(foiFootballTeamList.size() - 1);
        assertThat(testFoiFootballTeam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFoiFootballTeam.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testFoiFootballTeam.getVenueName()).isEqualTo(UPDATED_VENUE_NAME);
        assertThat(testFoiFootballTeam.getVenueCity()).isEqualTo(UPDATED_VENUE_CITY);
    }

    @Test
    @Transactional
    public void updateNonExistingFoiFootballTeam() throws Exception {
        int databaseSizeBeforeUpdate = foiFootballTeamRepository.findAll().size();

        // Create the FoiFootballTeam
        FoiFootballTeamDTO foiFootballTeamDTO = foiFootballTeamMapper.toDto(foiFootballTeam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoiFootballTeamMockMvc.perform(put("/api/foi-football-teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballTeamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FoiFootballTeam in the database
        List<FoiFootballTeam> foiFootballTeamList = foiFootballTeamRepository.findAll();
        assertThat(foiFootballTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFoiFootballTeam() throws Exception {
        // Initialize the database
        foiFootballTeamRepository.saveAndFlush(foiFootballTeam);

        int databaseSizeBeforeDelete = foiFootballTeamRepository.findAll().size();

        // Delete the foiFootballTeam
        restFoiFootballTeamMockMvc.perform(delete("/api/foi-football-teams/{id}", foiFootballTeam.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FoiFootballTeam> foiFootballTeamList = foiFootballTeamRepository.findAll();
        assertThat(foiFootballTeamList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoiFootballTeam.class);
        FoiFootballTeam foiFootballTeam1 = new FoiFootballTeam();
        foiFootballTeam1.setId(1L);
        FoiFootballTeam foiFootballTeam2 = new FoiFootballTeam();
        foiFootballTeam2.setId(foiFootballTeam1.getId());
        assertThat(foiFootballTeam1).isEqualTo(foiFootballTeam2);
        foiFootballTeam2.setId(2L);
        assertThat(foiFootballTeam1).isNotEqualTo(foiFootballTeam2);
        foiFootballTeam1.setId(null);
        assertThat(foiFootballTeam1).isNotEqualTo(foiFootballTeam2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoiFootballTeamDTO.class);
        FoiFootballTeamDTO foiFootballTeamDTO1 = new FoiFootballTeamDTO();
        foiFootballTeamDTO1.setId(1L);
        FoiFootballTeamDTO foiFootballTeamDTO2 = new FoiFootballTeamDTO();
        assertThat(foiFootballTeamDTO1).isNotEqualTo(foiFootballTeamDTO2);
        foiFootballTeamDTO2.setId(foiFootballTeamDTO1.getId());
        assertThat(foiFootballTeamDTO1).isEqualTo(foiFootballTeamDTO2);
        foiFootballTeamDTO2.setId(2L);
        assertThat(foiFootballTeamDTO1).isNotEqualTo(foiFootballTeamDTO2);
        foiFootballTeamDTO1.setId(null);
        assertThat(foiFootballTeamDTO1).isNotEqualTo(foiFootballTeamDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(foiFootballTeamMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(foiFootballTeamMapper.fromId(null)).isNull();
    }
}
