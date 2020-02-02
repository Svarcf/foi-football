package com.github.svarcf.football.web.rest;

import com.github.svarcf.football.FootballCrudApp;
import com.github.svarcf.football.config.SecurityBeanOverrideConfiguration;
import com.github.svarcf.football.domain.FoiFootballPlayer;
import com.github.svarcf.football.repository.FoiFootballPlayerRepository;
import com.github.svarcf.football.service.FoiFootballPlayerService;
import com.github.svarcf.football.service.dto.FoiFootballPlayerDTO;
import com.github.svarcf.football.service.mapper.FoiFootballPlayerMapper;
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
 * Integration tests for the {@link FoiFootballPlayerResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, FootballCrudApp.class})
public class FoiFootballPlayerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    @Autowired
    private FoiFootballPlayerRepository foiFootballPlayerRepository;

    @Autowired
    private FoiFootballPlayerMapper foiFootballPlayerMapper;

    @Autowired
    private FoiFootballPlayerService foiFootballPlayerService;

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

    private MockMvc restFoiFootballPlayerMockMvc;

    private FoiFootballPlayer foiFootballPlayer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FoiFootballPlayerResource foiFootballPlayerResource = new FoiFootballPlayerResource(foiFootballPlayerService);
        this.restFoiFootballPlayerMockMvc = MockMvcBuilders.standaloneSetup(foiFootballPlayerResource)
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
    public static FoiFootballPlayer createEntity(EntityManager em) {
        FoiFootballPlayer foiFootballPlayer = new FoiFootballPlayer()
            .name(DEFAULT_NAME)
            .number(DEFAULT_NUMBER);
        return foiFootballPlayer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoiFootballPlayer createUpdatedEntity(EntityManager em) {
        FoiFootballPlayer foiFootballPlayer = new FoiFootballPlayer()
            .name(UPDATED_NAME)
            .number(UPDATED_NUMBER);
        return foiFootballPlayer;
    }

    @BeforeEach
    public void initTest() {
        foiFootballPlayer = createEntity(em);
    }

    @Test
    @Transactional
    public void createFoiFootballPlayer() throws Exception {
        int databaseSizeBeforeCreate = foiFootballPlayerRepository.findAll().size();

        // Create the FoiFootballPlayer
        FoiFootballPlayerDTO foiFootballPlayerDTO = foiFootballPlayerMapper.toDto(foiFootballPlayer);
        restFoiFootballPlayerMockMvc.perform(post("/api/foi-football-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballPlayerDTO)))
            .andExpect(status().isCreated());

        // Validate the FoiFootballPlayer in the database
        List<FoiFootballPlayer> foiFootballPlayerList = foiFootballPlayerRepository.findAll();
        assertThat(foiFootballPlayerList).hasSize(databaseSizeBeforeCreate + 1);
        FoiFootballPlayer testFoiFootballPlayer = foiFootballPlayerList.get(foiFootballPlayerList.size() - 1);
        assertThat(testFoiFootballPlayer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFoiFootballPlayer.getNumber()).isEqualTo(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    public void createFoiFootballPlayerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = foiFootballPlayerRepository.findAll().size();

        // Create the FoiFootballPlayer with an existing ID
        foiFootballPlayer.setId(1L);
        FoiFootballPlayerDTO foiFootballPlayerDTO = foiFootballPlayerMapper.toDto(foiFootballPlayer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoiFootballPlayerMockMvc.perform(post("/api/foi-football-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballPlayerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FoiFootballPlayer in the database
        List<FoiFootballPlayer> foiFootballPlayerList = foiFootballPlayerRepository.findAll();
        assertThat(foiFootballPlayerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = foiFootballPlayerRepository.findAll().size();
        // set the field null
        foiFootballPlayer.setName(null);

        // Create the FoiFootballPlayer, which fails.
        FoiFootballPlayerDTO foiFootballPlayerDTO = foiFootballPlayerMapper.toDto(foiFootballPlayer);

        restFoiFootballPlayerMockMvc.perform(post("/api/foi-football-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballPlayerDTO)))
            .andExpect(status().isBadRequest());

        List<FoiFootballPlayer> foiFootballPlayerList = foiFootballPlayerRepository.findAll();
        assertThat(foiFootballPlayerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = foiFootballPlayerRepository.findAll().size();
        // set the field null
        foiFootballPlayer.setNumber(null);

        // Create the FoiFootballPlayer, which fails.
        FoiFootballPlayerDTO foiFootballPlayerDTO = foiFootballPlayerMapper.toDto(foiFootballPlayer);

        restFoiFootballPlayerMockMvc.perform(post("/api/foi-football-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballPlayerDTO)))
            .andExpect(status().isBadRequest());

        List<FoiFootballPlayer> foiFootballPlayerList = foiFootballPlayerRepository.findAll();
        assertThat(foiFootballPlayerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFoiFootballPlayers() throws Exception {
        // Initialize the database
        foiFootballPlayerRepository.saveAndFlush(foiFootballPlayer);

        // Get all the foiFootballPlayerList
        restFoiFootballPlayerMockMvc.perform(get("/api/foi-football-players?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foiFootballPlayer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getFoiFootballPlayer() throws Exception {
        // Initialize the database
        foiFootballPlayerRepository.saveAndFlush(foiFootballPlayer);

        // Get the foiFootballPlayer
        restFoiFootballPlayerMockMvc.perform(get("/api/foi-football-players/{id}", foiFootballPlayer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(foiFootballPlayer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingFoiFootballPlayer() throws Exception {
        // Get the foiFootballPlayer
        restFoiFootballPlayerMockMvc.perform(get("/api/foi-football-players/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFoiFootballPlayer() throws Exception {
        // Initialize the database
        foiFootballPlayerRepository.saveAndFlush(foiFootballPlayer);

        int databaseSizeBeforeUpdate = foiFootballPlayerRepository.findAll().size();

        // Update the foiFootballPlayer
        FoiFootballPlayer updatedFoiFootballPlayer = foiFootballPlayerRepository.findById(foiFootballPlayer.getId()).get();
        // Disconnect from session so that the updates on updatedFoiFootballPlayer are not directly saved in db
        em.detach(updatedFoiFootballPlayer);
        updatedFoiFootballPlayer
            .name(UPDATED_NAME)
            .number(UPDATED_NUMBER);
        FoiFootballPlayerDTO foiFootballPlayerDTO = foiFootballPlayerMapper.toDto(updatedFoiFootballPlayer);

        restFoiFootballPlayerMockMvc.perform(put("/api/foi-football-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballPlayerDTO)))
            .andExpect(status().isOk());

        // Validate the FoiFootballPlayer in the database
        List<FoiFootballPlayer> foiFootballPlayerList = foiFootballPlayerRepository.findAll();
        assertThat(foiFootballPlayerList).hasSize(databaseSizeBeforeUpdate);
        FoiFootballPlayer testFoiFootballPlayer = foiFootballPlayerList.get(foiFootballPlayerList.size() - 1);
        assertThat(testFoiFootballPlayer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFoiFootballPlayer.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingFoiFootballPlayer() throws Exception {
        int databaseSizeBeforeUpdate = foiFootballPlayerRepository.findAll().size();

        // Create the FoiFootballPlayer
        FoiFootballPlayerDTO foiFootballPlayerDTO = foiFootballPlayerMapper.toDto(foiFootballPlayer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoiFootballPlayerMockMvc.perform(put("/api/foi-football-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foiFootballPlayerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FoiFootballPlayer in the database
        List<FoiFootballPlayer> foiFootballPlayerList = foiFootballPlayerRepository.findAll();
        assertThat(foiFootballPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFoiFootballPlayer() throws Exception {
        // Initialize the database
        foiFootballPlayerRepository.saveAndFlush(foiFootballPlayer);

        int databaseSizeBeforeDelete = foiFootballPlayerRepository.findAll().size();

        // Delete the foiFootballPlayer
        restFoiFootballPlayerMockMvc.perform(delete("/api/foi-football-players/{id}", foiFootballPlayer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FoiFootballPlayer> foiFootballPlayerList = foiFootballPlayerRepository.findAll();
        assertThat(foiFootballPlayerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoiFootballPlayer.class);
        FoiFootballPlayer foiFootballPlayer1 = new FoiFootballPlayer();
        foiFootballPlayer1.setId(1L);
        FoiFootballPlayer foiFootballPlayer2 = new FoiFootballPlayer();
        foiFootballPlayer2.setId(foiFootballPlayer1.getId());
        assertThat(foiFootballPlayer1).isEqualTo(foiFootballPlayer2);
        foiFootballPlayer2.setId(2L);
        assertThat(foiFootballPlayer1).isNotEqualTo(foiFootballPlayer2);
        foiFootballPlayer1.setId(null);
        assertThat(foiFootballPlayer1).isNotEqualTo(foiFootballPlayer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoiFootballPlayerDTO.class);
        FoiFootballPlayerDTO foiFootballPlayerDTO1 = new FoiFootballPlayerDTO();
        foiFootballPlayerDTO1.setId(1L);
        FoiFootballPlayerDTO foiFootballPlayerDTO2 = new FoiFootballPlayerDTO();
        assertThat(foiFootballPlayerDTO1).isNotEqualTo(foiFootballPlayerDTO2);
        foiFootballPlayerDTO2.setId(foiFootballPlayerDTO1.getId());
        assertThat(foiFootballPlayerDTO1).isEqualTo(foiFootballPlayerDTO2);
        foiFootballPlayerDTO2.setId(2L);
        assertThat(foiFootballPlayerDTO1).isNotEqualTo(foiFootballPlayerDTO2);
        foiFootballPlayerDTO1.setId(null);
        assertThat(foiFootballPlayerDTO1).isNotEqualTo(foiFootballPlayerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(foiFootballPlayerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(foiFootballPlayerMapper.fromId(null)).isNull();
    }
}
