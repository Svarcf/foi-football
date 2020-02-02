package com.github.svarcf.football.web.rest;

import com.github.svarcf.football.FootballCrudApp;
import com.github.svarcf.football.config.SecurityBeanOverrideConfiguration;
import com.github.svarcf.football.domain.FoiFootballTournament;
import com.github.svarcf.football.repository.FoiFootballTournamentRepository;
import com.github.svarcf.football.service.FoiFootballTournamentService;
import com.github.svarcf.football.service.dto.FoiFootballTournamentDTO;
import com.github.svarcf.football.service.mapper.FoiFootballTournamentMapper;
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
 * Integration tests for the {@link FoiFootballTournamentResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, FootballCrudApp.class})
public class FoiFootballTournamentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private FoiFootballTournamentRepository foiFootballTournamentRepository;

    @Autowired
    private FoiFootballTournamentMapper foiFootballTournamentMapper;

    @Autowired
    private FoiFootballTournamentService foiFootballTournamentService;

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

    private MockMvc restFoiFootballTournamentMockMvc;

    private FoiFootballTournament foiFootballTournament;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FoiFootballTournamentResource foiFootballTournamentResource = new FoiFootballTournamentResource(foiFootballTournamentService);
        this.restFoiFootballTournamentMockMvc = MockMvcBuilders.standaloneSetup(foiFootballTournamentResource)
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
    public static FoiFootballTournament createEntity(EntityManager em) {
        FoiFootballTournament foiFootballTournament = new FoiFootballTournament()
            .name(DEFAULT_NAME)
            .start(DEFAULT_START)
            .end(DEFAULT_END);
        return foiFootballTournament;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoiFootballTournament createUpdatedEntity(EntityManager em) {
        FoiFootballTournament foiFootballTournament = new FoiFootballTournament()
            .name(UPDATED_NAME)
            .start(UPDATED_START)
            .end(UPDATED_END);
        return foiFootballTournament;
    }

    @BeforeEach
    public void initTest() {
        foiFootballTournament = createEntity(em);
    }

    @Test
    @Transactional
    public void createFoiFootballTournament() throws Exception {
        int databaseSizeBeforeCreate = foiFootballTournamentRepository.findAll().size();

        // Create the FoiFootballTournament
        FoiFootballTournamentDTO foiFootballTournamentDTO = foiFootballTournamentMapper.toDto(foiFootballTournament);
        restFoiFootballTournamentMockMvc.perform(post("/api/foi-football-tournaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballTournamentDTO)))
            .andExpect(status().isCreated());

        // Validate the FoiFootballTournament in the database
        List<FoiFootballTournament> foiFootballTournamentList = foiFootballTournamentRepository.findAll();
        assertThat(foiFootballTournamentList).hasSize(databaseSizeBeforeCreate + 1);
        FoiFootballTournament testFoiFootballTournament = foiFootballTournamentList.get(foiFootballTournamentList.size() - 1);
        assertThat(testFoiFootballTournament.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFoiFootballTournament.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testFoiFootballTournament.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    public void createFoiFootballTournamentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = foiFootballTournamentRepository.findAll().size();

        // Create the FoiFootballTournament with an existing ID
        foiFootballTournament.setId(1L);
        FoiFootballTournamentDTO foiFootballTournamentDTO = foiFootballTournamentMapper.toDto(foiFootballTournament);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoiFootballTournamentMockMvc.perform(post("/api/foi-football-tournaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballTournamentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FoiFootballTournament in the database
        List<FoiFootballTournament> foiFootballTournamentList = foiFootballTournamentRepository.findAll();
        assertThat(foiFootballTournamentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = foiFootballTournamentRepository.findAll().size();
        // set the field null
        foiFootballTournament.setName(null);

        // Create the FoiFootballTournament, which fails.
        FoiFootballTournamentDTO foiFootballTournamentDTO = foiFootballTournamentMapper.toDto(foiFootballTournament);

        restFoiFootballTournamentMockMvc.perform(post("/api/foi-football-tournaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballTournamentDTO)))
            .andExpect(status().isBadRequest());

        List<FoiFootballTournament> foiFootballTournamentList = foiFootballTournamentRepository.findAll();
        assertThat(foiFootballTournamentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = foiFootballTournamentRepository.findAll().size();
        // set the field null
        foiFootballTournament.setStart(null);

        // Create the FoiFootballTournament, which fails.
        FoiFootballTournamentDTO foiFootballTournamentDTO = foiFootballTournamentMapper.toDto(foiFootballTournament);

        restFoiFootballTournamentMockMvc.perform(post("/api/foi-football-tournaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballTournamentDTO)))
            .andExpect(status().isBadRequest());

        List<FoiFootballTournament> foiFootballTournamentList = foiFootballTournamentRepository.findAll();
        assertThat(foiFootballTournamentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = foiFootballTournamentRepository.findAll().size();
        // set the field null
        foiFootballTournament.setEnd(null);

        // Create the FoiFootballTournament, which fails.
        FoiFootballTournamentDTO foiFootballTournamentDTO = foiFootballTournamentMapper.toDto(foiFootballTournament);

        restFoiFootballTournamentMockMvc.perform(post("/api/foi-football-tournaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballTournamentDTO)))
            .andExpect(status().isBadRequest());

        List<FoiFootballTournament> foiFootballTournamentList = foiFootballTournamentRepository.findAll();
        assertThat(foiFootballTournamentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFoiFootballTournaments() throws Exception {
        // Initialize the database
        foiFootballTournamentRepository.saveAndFlush(foiFootballTournament);

        // Get all the foiFootballTournamentList
        restFoiFootballTournamentMockMvc.perform(get("/api/foi-football-tournaments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foiFootballTournament.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())));
    }
    
    @Test
    @Transactional
    public void getFoiFootballTournament() throws Exception {
        // Initialize the database
        foiFootballTournamentRepository.saveAndFlush(foiFootballTournament);

        // Get the foiFootballTournament
        restFoiFootballTournamentMockMvc.perform(get("/api/foi-football-tournaments/{id}", foiFootballTournament.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(foiFootballTournament.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFoiFootballTournament() throws Exception {
        // Get the foiFootballTournament
        restFoiFootballTournamentMockMvc.perform(get("/api/foi-football-tournaments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFoiFootballTournament() throws Exception {
        // Initialize the database
        foiFootballTournamentRepository.saveAndFlush(foiFootballTournament);

        int databaseSizeBeforeUpdate = foiFootballTournamentRepository.findAll().size();

        // Update the foiFootballTournament
        FoiFootballTournament updatedFoiFootballTournament = foiFootballTournamentRepository.findById(foiFootballTournament.getId()).get();
        // Disconnect from session so that the updates on updatedFoiFootballTournament are not directly saved in db
        em.detach(updatedFoiFootballTournament);
        updatedFoiFootballTournament
            .name(UPDATED_NAME)
            .start(UPDATED_START)
            .end(UPDATED_END);
        FoiFootballTournamentDTO foiFootballTournamentDTO = foiFootballTournamentMapper.toDto(updatedFoiFootballTournament);

        restFoiFootballTournamentMockMvc.perform(put("/api/foi-football-tournaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballTournamentDTO)))
            .andExpect(status().isOk());

        // Validate the FoiFootballTournament in the database
        List<FoiFootballTournament> foiFootballTournamentList = foiFootballTournamentRepository.findAll();
        assertThat(foiFootballTournamentList).hasSize(databaseSizeBeforeUpdate);
        FoiFootballTournament testFoiFootballTournament = foiFootballTournamentList.get(foiFootballTournamentList.size() - 1);
        assertThat(testFoiFootballTournament.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFoiFootballTournament.getStart()).isEqualTo(UPDATED_START);
        assertThat(testFoiFootballTournament.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    public void updateNonExistingFoiFootballTournament() throws Exception {
        int databaseSizeBeforeUpdate = foiFootballTournamentRepository.findAll().size();

        // Create the FoiFootballTournament
        FoiFootballTournamentDTO foiFootballTournamentDTO = foiFootballTournamentMapper.toDto(foiFootballTournament);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoiFootballTournamentMockMvc.perform(put("/api/foi-football-tournaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballTournamentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FoiFootballTournament in the database
        List<FoiFootballTournament> foiFootballTournamentList = foiFootballTournamentRepository.findAll();
        assertThat(foiFootballTournamentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFoiFootballTournament() throws Exception {
        // Initialize the database
        foiFootballTournamentRepository.saveAndFlush(foiFootballTournament);

        int databaseSizeBeforeDelete = foiFootballTournamentRepository.findAll().size();

        // Delete the foiFootballTournament
        restFoiFootballTournamentMockMvc.perform(delete("/api/foi-football-tournaments/{id}", foiFootballTournament.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FoiFootballTournament> foiFootballTournamentList = foiFootballTournamentRepository.findAll();
        assertThat(foiFootballTournamentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoiFootballTournament.class);
        FoiFootballTournament foiFootballTournament1 = new FoiFootballTournament();
        foiFootballTournament1.setId(1L);
        FoiFootballTournament foiFootballTournament2 = new FoiFootballTournament();
        foiFootballTournament2.setId(foiFootballTournament1.getId());
        assertThat(foiFootballTournament1).isEqualTo(foiFootballTournament2);
        foiFootballTournament2.setId(2L);
        assertThat(foiFootballTournament1).isNotEqualTo(foiFootballTournament2);
        foiFootballTournament1.setId(null);
        assertThat(foiFootballTournament1).isNotEqualTo(foiFootballTournament2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoiFootballTournamentDTO.class);
        FoiFootballTournamentDTO foiFootballTournamentDTO1 = new FoiFootballTournamentDTO();
        foiFootballTournamentDTO1.setId(1L);
        FoiFootballTournamentDTO foiFootballTournamentDTO2 = new FoiFootballTournamentDTO();
        assertThat(foiFootballTournamentDTO1).isNotEqualTo(foiFootballTournamentDTO2);
        foiFootballTournamentDTO2.setId(foiFootballTournamentDTO1.getId());
        assertThat(foiFootballTournamentDTO1).isEqualTo(foiFootballTournamentDTO2);
        foiFootballTournamentDTO2.setId(2L);
        assertThat(foiFootballTournamentDTO1).isNotEqualTo(foiFootballTournamentDTO2);
        foiFootballTournamentDTO1.setId(null);
        assertThat(foiFootballTournamentDTO1).isNotEqualTo(foiFootballTournamentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(foiFootballTournamentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(foiFootballTournamentMapper.fromId(null)).isNull();
    }
}
