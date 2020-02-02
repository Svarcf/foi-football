package com.github.svarcf.football.web.rest;

import com.github.svarcf.football.service.FoiFootballTournamentService;
import com.github.svarcf.football.web.rest.errors.BadRequestAlertException;
import com.github.svarcf.football.service.dto.FoiFootballTournamentDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.github.svarcf.football.domain.FoiFootballTournament}.
 */
@RestController
@RequestMapping("/api")
public class FoiFootballTournamentResource {

    private final Logger log = LoggerFactory.getLogger(FoiFootballTournamentResource.class);

    private static final String ENTITY_NAME = "footballCrudFoiFootballTournament";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FoiFootballTournamentService foiFootballTournamentService;

    public FoiFootballTournamentResource(FoiFootballTournamentService foiFootballTournamentService) {
        this.foiFootballTournamentService = foiFootballTournamentService;
    }

    /**
     * {@code POST  /foi-football-tournaments} : Create a new foiFootballTournament.
     *
     * @param foiFootballTournamentDTO the foiFootballTournamentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new foiFootballTournamentDTO, or with status {@code 400 (Bad Request)} if the foiFootballTournament has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/foi-football-tournaments")
    public ResponseEntity<FoiFootballTournamentDTO> createFoiFootballTournament(@Valid @RequestBody FoiFootballTournamentDTO foiFootballTournamentDTO) throws URISyntaxException {
        log.debug("REST request to save FoiFootballTournament : {}", foiFootballTournamentDTO);
        if (foiFootballTournamentDTO.getId() != null) {
            throw new BadRequestAlertException("A new foiFootballTournament cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FoiFootballTournamentDTO result = foiFootballTournamentService.save(foiFootballTournamentDTO);
        return ResponseEntity.created(new URI("/api/foi-football-tournaments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /foi-football-tournaments} : Updates an existing foiFootballTournament.
     *
     * @param foiFootballTournamentDTO the foiFootballTournamentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foiFootballTournamentDTO,
     * or with status {@code 400 (Bad Request)} if the foiFootballTournamentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the foiFootballTournamentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/foi-football-tournaments")
    public ResponseEntity<FoiFootballTournamentDTO> updateFoiFootballTournament(@Valid @RequestBody FoiFootballTournamentDTO foiFootballTournamentDTO) throws URISyntaxException {
        log.debug("REST request to update FoiFootballTournament : {}", foiFootballTournamentDTO);
        if (foiFootballTournamentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FoiFootballTournamentDTO result = foiFootballTournamentService.save(foiFootballTournamentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, foiFootballTournamentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /foi-football-tournaments} : get all the foiFootballTournaments.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of foiFootballTournaments in body.
     */
    @GetMapping("/foi-football-tournaments")
    public List<FoiFootballTournamentDTO> getAllFoiFootballTournaments() {
        log.debug("REST request to get all FoiFootballTournaments");
        return foiFootballTournamentService.findAll();
    }

    /**
     * {@code GET  /foi-football-tournaments/:id} : get the "id" foiFootballTournament.
     *
     * @param id the id of the foiFootballTournamentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the foiFootballTournamentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/foi-football-tournaments/{id}")
    public ResponseEntity<FoiFootballTournamentDTO> getFoiFootballTournament(@PathVariable Long id) {
        log.debug("REST request to get FoiFootballTournament : {}", id);
        Optional<FoiFootballTournamentDTO> foiFootballTournamentDTO = foiFootballTournamentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(foiFootballTournamentDTO);
    }

    /**
     * {@code DELETE  /foi-football-tournaments/:id} : delete the "id" foiFootballTournament.
     *
     * @param id the id of the foiFootballTournamentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/foi-football-tournaments/{id}")
    public ResponseEntity<Void> deleteFoiFootballTournament(@PathVariable Long id) {
        log.debug("REST request to delete FoiFootballTournament : {}", id);
        foiFootballTournamentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
