package com.github.svarcf.football.web.rest;

import com.github.svarcf.football.service.FoiFootballPositionService;
import com.github.svarcf.football.web.rest.errors.BadRequestAlertException;
import com.github.svarcf.football.service.dto.FoiFootballPositionDTO;

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
 * REST controller for managing {@link com.github.svarcf.football.domain.FoiFootballPosition}.
 */
@RestController
@RequestMapping("/api")
public class FoiFootballPositionResource {

    private final Logger log = LoggerFactory.getLogger(FoiFootballPositionResource.class);

    private static final String ENTITY_NAME = "footballCrudFoiFootballPosition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FoiFootballPositionService foiFootballPositionService;

    public FoiFootballPositionResource(FoiFootballPositionService foiFootballPositionService) {
        this.foiFootballPositionService = foiFootballPositionService;
    }

    /**
     * {@code POST  /foi-football-positions} : Create a new foiFootballPosition.
     *
     * @param foiFootballPositionDTO the foiFootballPositionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new foiFootballPositionDTO, or with status {@code 400 (Bad Request)} if the foiFootballPosition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/foi-football-positions")
    public ResponseEntity<FoiFootballPositionDTO> createFoiFootballPosition(@Valid @RequestBody FoiFootballPositionDTO foiFootballPositionDTO) throws URISyntaxException {
        log.debug("REST request to save FoiFootballPosition : {}", foiFootballPositionDTO);
        if (foiFootballPositionDTO.getId() != null) {
            throw new BadRequestAlertException("A new foiFootballPosition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FoiFootballPositionDTO result = foiFootballPositionService.save(foiFootballPositionDTO);
        return ResponseEntity.created(new URI("/api/foi-football-positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /foi-football-positions} : Updates an existing foiFootballPosition.
     *
     * @param foiFootballPositionDTO the foiFootballPositionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foiFootballPositionDTO,
     * or with status {@code 400 (Bad Request)} if the foiFootballPositionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the foiFootballPositionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/foi-football-positions")
    public ResponseEntity<FoiFootballPositionDTO> updateFoiFootballPosition(@Valid @RequestBody FoiFootballPositionDTO foiFootballPositionDTO) throws URISyntaxException {
        log.debug("REST request to update FoiFootballPosition : {}", foiFootballPositionDTO);
        if (foiFootballPositionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FoiFootballPositionDTO result = foiFootballPositionService.save(foiFootballPositionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, foiFootballPositionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /foi-football-positions} : get all the foiFootballPositions.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of foiFootballPositions in body.
     */
    @GetMapping("/foi-football-positions")
    public List<FoiFootballPositionDTO> getAllFoiFootballPositions() {
        log.debug("REST request to get all FoiFootballPositions");
        return foiFootballPositionService.findAll();
    }

    /**
     * {@code GET  /foi-football-positions/:id} : get the "id" foiFootballPosition.
     *
     * @param id the id of the foiFootballPositionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the foiFootballPositionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/foi-football-positions/{id}")
    public ResponseEntity<FoiFootballPositionDTO> getFoiFootballPosition(@PathVariable Long id) {
        log.debug("REST request to get FoiFootballPosition : {}", id);
        Optional<FoiFootballPositionDTO> foiFootballPositionDTO = foiFootballPositionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(foiFootballPositionDTO);
    }

    /**
     * {@code DELETE  /foi-football-positions/:id} : delete the "id" foiFootballPosition.
     *
     * @param id the id of the foiFootballPositionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/foi-football-positions/{id}")
    public ResponseEntity<Void> deleteFoiFootballPosition(@PathVariable Long id) {
        log.debug("REST request to delete FoiFootballPosition : {}", id);
        foiFootballPositionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
