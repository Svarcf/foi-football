package com.github.svarcf.football.web.rest;

import com.github.svarcf.football.FootballCrudApp;
import com.github.svarcf.football.config.SecurityBeanOverrideConfiguration;
import com.github.svarcf.football.domain.FoiFootballPosition;
import com.github.svarcf.football.repository.FoiFootballPositionRepository;
import com.github.svarcf.football.service.FoiFootballPositionService;
import com.github.svarcf.football.service.dto.FoiFootballPositionDTO;
import com.github.svarcf.football.service.mapper.FoiFootballPositionMapper;
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
 * Integration tests for the {@link FoiFootballPositionResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, FootballCrudApp.class})
public class FoiFootballPositionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private FoiFootballPositionRepository foiFootballPositionRepository;

    @Autowired
    private FoiFootballPositionMapper foiFootballPositionMapper;

    @Autowired
    private FoiFootballPositionService foiFootballPositionService;

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

    private MockMvc restFoiFootballPositionMockMvc;

    private FoiFootballPosition foiFootballPosition;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FoiFootballPositionResource foiFootballPositionResource = new FoiFootballPositionResource(foiFootballPositionService);
        this.restFoiFootballPositionMockMvc = MockMvcBuilders.standaloneSetup(foiFootballPositionResource)
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
    public static FoiFootballPosition createEntity(EntityManager em) {
        FoiFootballPosition foiFootballPosition = new FoiFootballPosition()
            .name(DEFAULT_NAME);
        return foiFootballPosition;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoiFootballPosition createUpdatedEntity(EntityManager em) {
        FoiFootballPosition foiFootballPosition = new FoiFootballPosition()
            .name(UPDATED_NAME);
        return foiFootballPosition;
    }

    @BeforeEach
    public void initTest() {
        foiFootballPosition = createEntity(em);
    }

    @Test
    @Transactional
    public void createFoiFootballPosition() throws Exception {
        int databaseSizeBeforeCreate = foiFootballPositionRepository.findAll().size();

        // Create the FoiFootballPosition
        FoiFootballPositionDTO foiFootballPositionDTO = foiFootballPositionMapper.toDto(foiFootballPosition);
        restFoiFootballPositionMockMvc.perform(post("/api/foi-football-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballPositionDTO)))
            .andExpect(status().isCreated());

        // Validate the FoiFootballPosition in the database
        List<FoiFootballPosition> foiFootballPositionList = foiFootballPositionRepository.findAll();
        assertThat(foiFootballPositionList).hasSize(databaseSizeBeforeCreate + 1);
        FoiFootballPosition testFoiFootballPosition = foiFootballPositionList.get(foiFootballPositionList.size() - 1);
        assertThat(testFoiFootballPosition.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createFoiFootballPositionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = foiFootballPositionRepository.findAll().size();

        // Create the FoiFootballPosition with an existing ID
        foiFootballPosition.setId(1L);
        FoiFootballPositionDTO foiFootballPositionDTO = foiFootballPositionMapper.toDto(foiFootballPosition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoiFootballPositionMockMvc.perform(post("/api/foi-football-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballPositionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FoiFootballPosition in the database
        List<FoiFootballPosition> foiFootballPositionList = foiFootballPositionRepository.findAll();
        assertThat(foiFootballPositionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = foiFootballPositionRepository.findAll().size();
        // set the field null
        foiFootballPosition.setName(null);

        // Create the FoiFootballPosition, which fails.
        FoiFootballPositionDTO foiFootballPositionDTO = foiFootballPositionMapper.toDto(foiFootballPosition);

        restFoiFootballPositionMockMvc.perform(post("/api/foi-football-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballPositionDTO)))
            .andExpect(status().isBadRequest());

        List<FoiFootballPosition> foiFootballPositionList = foiFootballPositionRepository.findAll();
        assertThat(foiFootballPositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFoiFootballPositions() throws Exception {
        // Initialize the database
        foiFootballPositionRepository.saveAndFlush(foiFootballPosition);

        // Get all the foiFootballPositionList
        restFoiFootballPositionMockMvc.perform(get("/api/foi-football-positions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foiFootballPosition.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getFoiFootballPosition() throws Exception {
        // Initialize the database
        foiFootballPositionRepository.saveAndFlush(foiFootballPosition);

        // Get the foiFootballPosition
        restFoiFootballPositionMockMvc.perform(get("/api/foi-football-positions/{id}", foiFootballPosition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(foiFootballPosition.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingFoiFootballPosition() throws Exception {
        // Get the foiFootballPosition
        restFoiFootballPositionMockMvc.perform(get("/api/foi-football-positions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFoiFootballPosition() throws Exception {
        // Initialize the database
        foiFootballPositionRepository.saveAndFlush(foiFootballPosition);

        int databaseSizeBeforeUpdate = foiFootballPositionRepository.findAll().size();

        // Update the foiFootballPosition
        FoiFootballPosition updatedFoiFootballPosition = foiFootballPositionRepository.findById(foiFootballPosition.getId()).get();
        // Disconnect from session so that the updates on updatedFoiFootballPosition are not directly saved in db
        em.detach(updatedFoiFootballPosition);
        updatedFoiFootballPosition
            .name(UPDATED_NAME);
        FoiFootballPositionDTO foiFootballPositionDTO = foiFootballPositionMapper.toDto(updatedFoiFootballPosition);

        restFoiFootballPositionMockMvc.perform(put("/api/foi-football-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballPositionDTO)))
            .andExpect(status().isOk());

        // Validate the FoiFootballPosition in the database
        List<FoiFootballPosition> foiFootballPositionList = foiFootballPositionRepository.findAll();
        assertThat(foiFootballPositionList).hasSize(databaseSizeBeforeUpdate);
        FoiFootballPosition testFoiFootballPosition = foiFootballPositionList.get(foiFootballPositionList.size() - 1);
        assertThat(testFoiFootballPosition.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingFoiFootballPosition() throws Exception {
        int databaseSizeBeforeUpdate = foiFootballPositionRepository.findAll().size();

        // Create the FoiFootballPosition
        FoiFootballPositionDTO foiFootballPositionDTO = foiFootballPositionMapper.toDto(foiFootballPosition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoiFootballPositionMockMvc.perform(put("/api/foi-football-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballPositionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FoiFootballPosition in the database
        List<FoiFootballPosition> foiFootballPositionList = foiFootballPositionRepository.findAll();
        assertThat(foiFootballPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFoiFootballPosition() throws Exception {
        // Initialize the database
        foiFootballPositionRepository.saveAndFlush(foiFootballPosition);

        int databaseSizeBeforeDelete = foiFootballPositionRepository.findAll().size();

        // Delete the foiFootballPosition
        restFoiFootballPositionMockMvc.perform(delete("/api/foi-football-positions/{id}", foiFootballPosition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FoiFootballPosition> foiFootballPositionList = foiFootballPositionRepository.findAll();
        assertThat(foiFootballPositionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoiFootballPosition.class);
        FoiFootballPosition foiFootballPosition1 = new FoiFootballPosition();
        foiFootballPosition1.setId(1L);
        FoiFootballPosition foiFootballPosition2 = new FoiFootballPosition();
        foiFootballPosition2.setId(foiFootballPosition1.getId());
        assertThat(foiFootballPosition1).isEqualTo(foiFootballPosition2);
        foiFootballPosition2.setId(2L);
        assertThat(foiFootballPosition1).isNotEqualTo(foiFootballPosition2);
        foiFootballPosition1.setId(null);
        assertThat(foiFootballPosition1).isNotEqualTo(foiFootballPosition2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoiFootballPositionDTO.class);
        FoiFootballPositionDTO foiFootballPositionDTO1 = new FoiFootballPositionDTO();
        foiFootballPositionDTO1.setId(1L);
        FoiFootballPositionDTO foiFootballPositionDTO2 = new FoiFootballPositionDTO();
        assertThat(foiFootballPositionDTO1).isNotEqualTo(foiFootballPositionDTO2);
        foiFootballPositionDTO2.setId(foiFootballPositionDTO1.getId());
        assertThat(foiFootballPositionDTO1).isEqualTo(foiFootballPositionDTO2);
        foiFootballPositionDTO2.setId(2L);
        assertThat(foiFootballPositionDTO1).isNotEqualTo(foiFootballPositionDTO2);
        foiFootballPositionDTO1.setId(null);
        assertThat(foiFootballPositionDTO1).isNotEqualTo(foiFootballPositionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(foiFootballPositionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(foiFootballPositionMapper.fromId(null)).isNull();
    }
}
