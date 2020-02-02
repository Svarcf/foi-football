package com.github.svarcf.football.web.rest;

import com.github.svarcf.football.service.FoiFootballFixtureService;
import com.github.svarcf.football.web.rest.errors.BadRequestAlertException;
import com.github.svarcf.football.service.dto.FoiFootballFixtureDTO;

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
 * REST controller for managing {@link com.github.svarcf.football.domain.FoiFootballFixture}.
 */
@RestController
@RequestMapping("/api")
public class FoiFootballFixtureResource {

    private final Logger log = LoggerFactory.getLogger(FoiFootballFixtureResource.class);

    private static final String ENTITY_NAME = "footballCrudFoiFootballFixture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FoiFootballFixtureService foiFootballFixtureService;

    public FoiFootballFixtureResource(FoiFootballFixtureService foiFootballFixtureService) {
        this.foiFootballFixtureService = foiFootballFixtureService;
    }

    /**
     * {@code POST  /foi-football-fixtures} : Create a new foiFootballFixture.
     *
     * @param foiFootballFixtureDTO the foiFootballFixtureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new foiFootballFixtureDTO, or with status {@code 400 (Bad Request)} if the foiFootballFixture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/foi-football-fixtures")
    public ResponseEntity<FoiFootballFixtureDTO> createFoiFootballFixture(@Valid @RequestBody FoiFootballFixtureDTO foiFootballFixtureDTO) throws URISyntaxException {
        log.debug("REST request to save FoiFootballFixture : {}", foiFootballFixtureDTO);
        if (foiFootballFixtureDTO.getId() != null) {
            throw new BadRequestAlertException("A new foiFootballFixture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FoiFootballFixtureDTO result = foiFootballFixtureService.save(foiFootballFixtureDTO);
        return ResponseEntity.created(new URI("/api/foi-football-fixtures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /foi-football-fixtures} : Updates an existing foiFootballFixture.
     *
     * @param foiFootballFixtureDTO the foiFootballFixtureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foiFootballFixtureDTO,
     * or with status {@code 400 (Bad Request)} if the foiFootballFixtureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the foiFootballFixtureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/foi-football-fixtures")
    public ResponseEntity<FoiFootballFixtureDTO> updateFoiFootballFixture(@Valid @RequestBody FoiFootballFixtureDTO foiFootballFixtureDTO) throws URISyntaxException {
        log.debug("REST request to update FoiFootballFixture : {}", foiFootballFixtureDTO);
        if (foiFootballFixtureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FoiFootballFixtureDTO result = foiFootballFixtureService.save(foiFootballFixtureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, foiFootballFixtureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /foi-football-fixtures} : get all the foiFootballFixtures.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of foiFootballFixtures in body.
     */
    @GetMapping("/foi-football-fixtures")
    public List<FoiFootballFixtureDTO> getAllFoiFootballFixtures() {
        log.debug("REST request to get all FoiFootballFixtures");
        return foiFootballFixtureService.findAll();
    }

    /**
     * {@code GET  /foi-football-fixtures/:id} : get the "id" foiFootballFixture.
     *
     * @param id the id of the foiFootballFixtureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the foiFootballFixtureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/foi-football-fixtures/{id}")
    public ResponseEntity<FoiFootballFixtureDTO> getFoiFootballFixture(@PathVariable Long id) {
        log.debug("REST request to get FoiFootballFixture : {}", id);
        Optional<FoiFootballFixtureDTO> foiFootballFixtureDTO = foiFootballFixtureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(foiFootballFixtureDTO);
    }

    /**
     * {@code DELETE  /foi-football-fixtures/:id} : delete the "id" foiFootballFixture.
     *
     * @param id the id of the foiFootballFixtureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/foi-football-fixtures/{id}")
    public ResponseEntity<Void> deleteFoiFootballFixture(@PathVariable Long id) {
        log.debug("REST request to delete FoiFootballFixture : {}", id);
        foiFootballFixtureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
