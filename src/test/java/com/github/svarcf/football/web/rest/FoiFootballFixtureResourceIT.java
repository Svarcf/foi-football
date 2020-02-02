package com.github.svarcf.football.web.rest;

import com.github.svarcf.football.FootballCrudApp;
import com.github.svarcf.football.config.SecurityBeanOverrideConfiguration;
import com.github.svarcf.football.domain.FoiFootballFixture;
import com.github.svarcf.football.repository.FoiFootballFixtureRepository;
import com.github.svarcf.football.service.FoiFootballFixtureService;
import com.github.svarcf.football.service.dto.FoiFootballFixtureDTO;
import com.github.svarcf.football.service.mapper.FoiFootballFixtureMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.github.svarcf.football.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FoiFootballFixtureResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, FootballCrudApp.class})
public class FoiFootballFixtureResourceIT {

    private static final LocalDate DEFAULT_EVENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EVENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ROUND = "AAAAAAAAAA";
    private static final String UPDATED_ROUND = "BBBBBBBBBB";

    private static final String DEFAULT_VENUE = "AAAAAAAAAA";
    private static final String UPDATED_VENUE = "BBBBBBBBBB";

    private static final String DEFAULT_SCORE = "AAAAAAAAAA";
    private static final String UPDATED_SCORE = "BBBBBBBBBB";

    @Autowired
    private FoiFootballFixtureRepository foiFootballFixtureRepository;

    @Autowired
    private FoiFootballFixtureMapper foiFootballFixtureMapper;

    @Autowired
    private FoiFootballFixtureService foiFootballFixtureService;

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

    private MockMvc restFoiFootballFixtureMockMvc;

    private FoiFootballFixture foiFootballFixture;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FoiFootballFixtureResource foiFootballFixtureResource = new FoiFootballFixtureResource(foiFootballFixtureService);
        this.restFoiFootballFixtureMockMvc = MockMvcBuilders.standaloneSetup(foiFootballFixtureResource)
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
    public static FoiFootballFixture createEntity(EntityManager em) {
        FoiFootballFixture foiFootballFixture = new FoiFootballFixture()
            .eventDate(DEFAULT_EVENT_DATE)
            .round(DEFAULT_ROUND)
            .venue(DEFAULT_VENUE)
            .score(DEFAULT_SCORE);
        return foiFootballFixture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoiFootballFixture createUpdatedEntity(EntityManager em) {
        FoiFootballFixture foiFootballFixture = new FoiFootballFixture()
            .eventDate(UPDATED_EVENT_DATE)
            .round(UPDATED_ROUND)
            .venue(UPDATED_VENUE)
            .score(UPDATED_SCORE);
        return foiFootballFixture;
    }

    @BeforeEach
    public void initTest() {
        foiFootballFixture = createEntity(em);
    }

    @Test
    @Transactional
    public void createFoiFootballFixture() throws Exception {
        int databaseSizeBeforeCreate = foiFootballFixtureRepository.findAll().size();

        // Create the FoiFootballFixture
        FoiFootballFixtureDTO foiFootballFixtureDTO = foiFootballFixtureMapper.toDto(foiFootballFixture);
        restFoiFootballFixtureMockMvc.perform(post("/api/foi-football-fixtures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballFixtureDTO)))
            .andExpect(status().isCreated());

        // Validate the FoiFootballFixture in the database
        List<FoiFootballFixture> foiFootballFixtureList = foiFootballFixtureRepository.findAll();
        assertThat(foiFootballFixtureList).hasSize(databaseSizeBeforeCreate + 1);
        FoiFootballFixture testFoiFootballFixture = foiFootballFixtureList.get(foiFootballFixtureList.size() - 1);
        assertThat(testFoiFootballFixture.getEventDate()).isEqualTo(DEFAULT_EVENT_DATE);
        assertThat(testFoiFootballFixture.getRound()).isEqualTo(DEFAULT_ROUND);
        assertThat(testFoiFootballFixture.getVenue()).isEqualTo(DEFAULT_VENUE);
        assertThat(testFoiFootballFixture.getScore()).isEqualTo(DEFAULT_SCORE);
    }

    @Test
    @Transactional
    public void createFoiFootballFixtureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = foiFootballFixtureRepository.findAll().size();

        // Create the FoiFootballFixture with an existing ID
        foiFootballFixture.setId(1L);
        FoiFootballFixtureDTO foiFootballFixtureDTO = foiFootballFixtureMapper.toDto(foiFootballFixture);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoiFootballFixtureMockMvc.perform(post("/api/foi-football-fixtures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballFixtureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FoiFootballFixture in the database
        List<FoiFootballFixture> foiFootballFixtureList = foiFootballFixtureRepository.findAll();
        assertThat(foiFootballFixtureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEventDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = foiFootballFixtureRepository.findAll().size();
        // set the field null
        foiFootballFixture.setEventDate(null);

        // Create the FoiFootballFixture, which fails.
        FoiFootballFixtureDTO foiFootballFixtureDTO = foiFootballFixtureMapper.toDto(foiFootballFixture);

        restFoiFootballFixtureMockMvc.perform(post("/api/foi-football-fixtures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballFixtureDTO)))
            .andExpect(status().isBadRequest());

        List<FoiFootballFixture> foiFootballFixtureList = foiFootballFixtureRepository.findAll();
        assertThat(foiFootballFixtureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFoiFootballFixtures() throws Exception {
        // Initialize the database
        foiFootballFixtureRepository.saveAndFlush(foiFootballFixture);

        // Get all the foiFootballFixtureList
        restFoiFootballFixtureMockMvc.perform(get("/api/foi-football-fixtures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foiFootballFixture.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventDate").value(hasItem(DEFAULT_EVENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].round").value(hasItem(DEFAULT_ROUND)))
            .andExpect(jsonPath("$.[*].venue").value(hasItem(DEFAULT_VENUE)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)));
    }
    
    @Test
    @Transactional
    public void getFoiFootballFixture() throws Exception {
        // Initialize the database
        foiFootballFixtureRepository.saveAndFlush(foiFootballFixture);

        // Get the foiFootballFixture
        restFoiFootballFixtureMockMvc.perform(get("/api/foi-football-fixtures/{id}", foiFootballFixture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(foiFootballFixture.getId().intValue()))
            .andExpect(jsonPath("$.eventDate").value(DEFAULT_EVENT_DATE.toString()))
            .andExpect(jsonPath("$.round").value(DEFAULT_ROUND))
            .andExpect(jsonPath("$.venue").value(DEFAULT_VENUE))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE));
    }

    @Test
    @Transactional
    public void getNonExistingFoiFootballFixture() throws Exception {
        // Get the foiFootballFixture
        restFoiFootballFixtureMockMvc.perform(get("/api/foi-football-fixtures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFoiFootballFixture() throws Exception {
        // Initialize the database
        foiFootballFixtureRepository.saveAndFlush(foiFootballFixture);

        int databaseSizeBeforeUpdate = foiFootballFixtureRepository.findAll().size();

        // Update the foiFootballFixture
        FoiFootballFixture updatedFoiFootballFixture = foiFootballFixtureRepository.findById(foiFootballFixture.getId()).get();
        // Disconnect from session so that the updates on updatedFoiFootballFixture are not directly saved in db
        em.detach(updatedFoiFootballFixture);
        updatedFoiFootballFixture
            .eventDate(UPDATED_EVENT_DATE)
            .round(UPDATED_ROUND)
            .venue(UPDATED_VENUE)
            .score(UPDATED_SCORE);
        FoiFootballFixtureDTO foiFootballFixtureDTO = foiFootballFixtureMapper.toDto(updatedFoiFootballFixture);

        restFoiFootballFixtureMockMvc.perform(put("/api/foi-football-fixtures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballFixtureDTO)))
            .andExpect(status().isOk());

        // Validate the FoiFootballFixture in the database
        List<FoiFootballFixture> foiFootballFixtureList = foiFootballFixtureRepository.findAll();
        assertThat(foiFootballFixtureList).hasSize(databaseSizeBeforeUpdate);
        FoiFootballFixture testFoiFootballFixture = foiFootballFixtureList.get(foiFootballFixtureList.size() - 1);
        assertThat(testFoiFootballFixture.getEventDate()).isEqualTo(UPDATED_EVENT_DATE);
        assertThat(testFoiFootballFixture.getRound()).isEqualTo(UPDATED_ROUND);
        assertThat(testFoiFootballFixture.getVenue()).isEqualTo(UPDATED_VENUE);
        assertThat(testFoiFootballFixture.getScore()).isEqualTo(UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void updateNonExistingFoiFootballFixture() throws Exception {
        int databaseSizeBeforeUpdate = foiFootballFixtureRepository.findAll().size();

        // Create the FoiFootballFixture
        FoiFootballFixtureDTO foiFootballFixtureDTO = foiFootballFixtureMapper.toDto(foiFootballFixture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoiFootballFixtureMockMvc.perform(put("/api/foi-football-fixtures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballFixtureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FoiFootballFixture in the database
        List<FoiFootballFixture> foiFootballFixtureList = foiFootballFixtureRepository.findAll();
        assertThat(foiFootballFixtureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFoiFootballFixture() throws Exception {
        // Initialize the database
        foiFootballFixtureRepository.saveAndFlush(foiFootballFixture);

        int databaseSizeBeforeDelete = foiFootballFixtureRepository.findAll().size();

        // Delete the foiFootballFixture
        restFoiFootballFixtureMockMvc.perform(delete("/api/foi-football-fixtures/{id}", foiFootballFixture.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FoiFootballFixture> foiFootballFixtureList = foiFootballFixtureRepository.findAll();
        assertThat(foiFootballFixtureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoiFootballFixture.class);
        FoiFootballFixture foiFootballFixture1 = new FoiFootballFixture();
        foiFootballFixture1.setId(1L);
        FoiFootballFixture foiFootballFixture2 = new FoiFootballFixture();
        foiFootballFixture2.setId(foiFootballFixture1.getId());
        assertThat(foiFootballFixture1).isEqualTo(foiFootballFixture2);
        foiFootballFixture2.setId(2L);
        assertThat(foiFootballFixture1).isNotEqualTo(foiFootballFixture2);
        foiFootballFixture1.setId(null);
        assertThat(foiFootballFixture1).isNotEqualTo(foiFootballFixture2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoiFootballFixtureDTO.class);
        FoiFootballFixtureDTO foiFootballFixtureDTO1 = new FoiFootballFixtureDTO();
        foiFootballFixtureDTO1.setId(1L);
        FoiFootballFixtureDTO foiFootballFixtureDTO2 = new FoiFootballFixtureDTO();
        assertThat(foiFootballFixtureDTO1).isNotEqualTo(foiFootballFixtureDTO2);
        foiFootballFixtureDTO2.setId(foiFootballFixtureDTO1.getId());
        assertThat(foiFootballFixtureDTO1).isEqualTo(foiFootballFixtureDTO2);
        foiFootballFixtureDTO2.setId(2L);
        assertThat(foiFootballFixtureDTO1).isNotEqualTo(foiFootballFixtureDTO2);
        foiFootballFixtureDTO1.setId(null);
        assertThat(foiFootballFixtureDTO1).isNotEqualTo(foiFootballFixtureDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(foiFootballFixtureMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(foiFootballFixtureMapper.fromId(null)).isNull();
    }
}
