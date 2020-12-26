package com.github.svarcf.football.web.rest;

import com.github.svarcf.football.service.FoiFootballTableService;
import com.github.svarcf.football.service.dto.FoiFootballTableDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing {@link com.github.svarcf.football.domain.FoiFootballTableDto}.
 */
@RestController
@RequestMapping("/api")
public class FoiFootballTableResource {

    private final Logger log = LoggerFactory.getLogger(FoiFootballTableResource.class);
    private final FoiFootballTableService foiFootballTableService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public FoiFootballTableResource(FoiFootballTableService foiFootballTableService) {
        this.foiFootballTableService = foiFootballTableService;
    }

    /**
     * {@code GET  /foi-football-tables/:id} : get the "id" foiFootballTable.
     *
     * @param id the id of the foiFootballTableDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the foiFootballTableDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/foi-football-tables/{id}")
    public List<FoiFootballTableDTO> getFoiFootballTable(@PathVariable Long id) {
        log.debug("REST request to get FoiFootballTable : {}", id);
        List<FoiFootballTableDTO> foiFootballTableDTO = foiFootballTableService.findByTournamentId(id);
        return foiFootballTableDTO;
    }


}
