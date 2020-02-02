package com.github.svarcf.football.web.rest;

import com.github.svarcf.football.service.FoiFootballTeamService;
import com.github.svarcf.football.web.rest.errors.BadRequestAlertException;
import com.github.svarcf.football.service.dto.FoiFootballTeamDTO;

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
 * REST controller for managing {@link com.github.svarcf.football.domain.FoiFootballTeam}.
 */
@RestController
@RequestMapping("/api")
public class FoiFootballTeamResource {

    private final Logger log = LoggerFactory.getLogger(FoiFootballTeamResource.class);

    private static final String ENTITY_NAME = "footballCrudFoiFootballTeam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FoiFootballTeamService foiFootballTeamService;

    public FoiFootballTeamResource(FoiFootballTeamService foiFootballTeamService) {
        this.foiFootballTeamService = foiFootballTeamService;
    }

    /**
     * {@code POST  /foi-football-teams} : Create a new foiFootballTeam.
     *
     * @param foiFootballTeamDTO the foiFootballTeamDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new foiFootballTeamDTO, or with status {@code 400 (Bad Request)} if the foiFootballTeam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/foi-football-teams")
    public ResponseEntity<FoiFootballTeamDTO> createFoiFootballTeam(@Valid @RequestBody FoiFootballTeamDTO foiFootballTeamDTO) throws URISyntaxException {
        log.debug("REST request to save FoiFootballTeam : {}", foiFootballTeamDTO);
        if (foiFootballTeamDTO.getId() != null) {
            throw new BadRequestAlertException("A new foiFootballTeam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FoiFootballTeamDTO result = foiFootballTeamService.save(foiFootballTeamDTO);
        return ResponseEntity.created(new URI("/api/foi-football-teams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /foi-football-teams} : Updates an existing foiFootballTeam.
     *
     * @param foiFootballTeamDTO the foiFootballTeamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foiFootballTeamDTO,
     * or with status {@code 400 (Bad Request)} if the foiFootballTeamDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the foiFootballTeamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/foi-football-teams")
    public ResponseEntity<FoiFootballTeamDTO> updateFoiFootballTeam(@Valid @RequestBody FoiFootballTeamDTO foiFootballTeamDTO) throws URISyntaxException {
        log.debug("REST request to update FoiFootballTeam : {}", foiFootballTeamDTO);
        if (foiFootballTeamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FoiFootballTeamDTO result = foiFootballTeamService.save(foiFootballTeamDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, foiFootballTeamDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /foi-football-teams} : get all the foiFootballTeams.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of foiFootballTeams in body.
     */
    @GetMapping("/foi-football-teams")
    public List<FoiFootballTeamDTO> getAllFoiFootballTeams() {
        log.debug("REST request to get all FoiFootballTeams");
        return foiFootballTeamService.findAll();
    }

    /**
     * {@code GET  /foi-football-teams/:id} : get the "id" foiFootballTeam.
     *
     * @param id the id of the foiFootballTeamDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the foiFootballTeamDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/foi-football-teams/{id}")
    public ResponseEntity<FoiFootballTeamDTO> getFoiFootballTeam(@PathVariable Long id) {
        log.debug("REST request to get FoiFootballTeam : {}", id);
        Optional<FoiFootballTeamDTO> foiFootballTeamDTO = foiFootballTeamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(foiFootballTeamDTO);
    }

    /**
     * {@code DELETE  /foi-football-teams/:id} : delete the "id" foiFootballTeam.
     *
     * @param id the id of the foiFootballTeamDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/foi-football-teams/{id}")
    public ResponseEntity<Void> deleteFoiFootballTeam(@PathVariable Long id) {
        log.debug("REST request to delete FoiFootballTeam : {}", id);
        foiFootballTeamService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
