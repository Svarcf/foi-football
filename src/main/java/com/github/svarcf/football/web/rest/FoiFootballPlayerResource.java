package com.github.svarcf.football.web.rest;

import com.github.svarcf.football.service.FoiFootballPlayerService;
import com.github.svarcf.football.web.rest.errors.BadRequestAlertException;
import com.github.svarcf.football.service.dto.FoiFootballPlayerDTO;

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
 * REST controller for managing {@link com.github.svarcf.football.domain.FoiFootballPlayer}.
 */
@RestController
@RequestMapping("/api")
public class FoiFootballPlayerResource {

    private final Logger log = LoggerFactory.getLogger(FoiFootballPlayerResource.class);

    private static final String ENTITY_NAME = "footballCrudFoiFootballPlayer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FoiFootballPlayerService foiFootballPlayerService;

    public FoiFootballPlayerResource(FoiFootballPlayerService foiFootballPlayerService) {
        this.foiFootballPlayerService = foiFootballPlayerService;
    }

    /**
     * {@code POST  /foi-football-players} : Create a new foiFootballPlayer.
     *
     * @param foiFootballPlayerDTO the foiFootballPlayerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new foiFootballPlayerDTO, or with status {@code 400 (Bad Request)} if the foiFootballPlayer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/foi-football-players")
    public ResponseEntity<FoiFootballPlayerDTO> createFoiFootballPlayer(@Valid @RequestBody FoiFootballPlayerDTO foiFootballPlayerDTO) throws URISyntaxException {
        log.debug("REST request to save FoiFootballPlayer : {}", foiFootballPlayerDTO);
        if (foiFootballPlayerDTO.getId() != null) {
            throw new BadRequestAlertException("A new foiFootballPlayer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FoiFootballPlayerDTO result = foiFootballPlayerService.save(foiFootballPlayerDTO);
        return ResponseEntity.created(new URI("/api/foi-football-players/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /foi-football-players} : Updates an existing foiFootballPlayer.
     *
     * @param foiFootballPlayerDTO the foiFootballPlayerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foiFootballPlayerDTO,
     * or with status {@code 400 (Bad Request)} if the foiFootballPlayerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the foiFootballPlayerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/foi-football-players")
    public ResponseEntity<FoiFootballPlayerDTO> updateFoiFootballPlayer(@Valid @RequestBody FoiFootballPlayerDTO foiFootballPlayerDTO) throws URISyntaxException {
        log.debug("REST request to update FoiFootballPlayer : {}", foiFootballPlayerDTO);
        if (foiFootballPlayerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FoiFootballPlayerDTO result = foiFootballPlayerService.save(foiFootballPlayerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, foiFootballPlayerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /foi-football-players} : get all the foiFootballPlayers.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of foiFootballPlayers in body.
     */
    @GetMapping("/foi-football-players")
    public List<FoiFootballPlayerDTO> getAllFoiFootballPlayers() {
        log.debug("REST request to get all FoiFootballPlayers");
        return foiFootballPlayerService.findAll();
    }

    /**
     * {@code GET  /foi-football-players/:id} : get the "id" foiFootballPlayer.
     *
     * @param id the id of the foiFootballPlayerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the foiFootballPlayerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/foi-football-players/{id}")
    public ResponseEntity<FoiFootballPlayerDTO> getFoiFootballPlayer(@PathVariable Long id) {
        log.debug("REST request to get FoiFootballPlayer : {}", id);
        Optional<FoiFootballPlayerDTO> foiFootballPlayerDTO = foiFootballPlayerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(foiFootballPlayerDTO);
    }

    /**
     * {@code DELETE  /foi-football-players/:id} : delete the "id" foiFootballPlayer.
     *
     * @param id the id of the foiFootballPlayerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/foi-football-players/{id}")
    public ResponseEntity<Void> deleteFoiFootballPlayer(@PathVariable Long id) {
        log.debug("REST request to delete FoiFootballPlayer : {}", id);
        foiFootballPlayerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
