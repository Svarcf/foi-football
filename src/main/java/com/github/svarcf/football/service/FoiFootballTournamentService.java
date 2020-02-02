package com.github.svarcf.football.service;

import com.github.svarcf.football.domain.FoiFootballTournament;
import com.github.svarcf.football.repository.FoiFootballTournamentRepository;
import com.github.svarcf.football.service.dto.FoiFootballTournamentDTO;
import com.github.svarcf.football.service.mapper.FoiFootballTournamentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link FoiFootballTournament}.
 */
@Service
@Transactional
public class FoiFootballTournamentService {

    private final Logger log = LoggerFactory.getLogger(FoiFootballTournamentService.class);

    private final FoiFootballTournamentRepository foiFootballTournamentRepository;

    private final FoiFootballTournamentMapper foiFootballTournamentMapper;

    public FoiFootballTournamentService(FoiFootballTournamentRepository foiFootballTournamentRepository, FoiFootballTournamentMapper foiFootballTournamentMapper) {
        this.foiFootballTournamentRepository = foiFootballTournamentRepository;
        this.foiFootballTournamentMapper = foiFootballTournamentMapper;
    }

    /**
     * Save a foiFootballTournament.
     *
     * @param foiFootballTournamentDTO the entity to save.
     * @return the persisted entity.
     */
    public FoiFootballTournamentDTO save(FoiFootballTournamentDTO foiFootballTournamentDTO) {
        log.debug("Request to save FoiFootballTournament : {}", foiFootballTournamentDTO);
        FoiFootballTournament foiFootballTournament = foiFootballTournamentMapper.toEntity(foiFootballTournamentDTO);
        foiFootballTournament = foiFootballTournamentRepository.save(foiFootballTournament);
        return foiFootballTournamentMapper.toDto(foiFootballTournament);
    }

    /**
     * Get all the foiFootballTournaments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FoiFootballTournamentDTO> findAll() {
        log.debug("Request to get all FoiFootballTournaments");
        return foiFootballTournamentRepository.findAll().stream()
            .map(foiFootballTournamentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one foiFootballTournament by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FoiFootballTournamentDTO> findOne(Long id) {
        log.debug("Request to get FoiFootballTournament : {}", id);
        return foiFootballTournamentRepository.findById(id)
            .map(foiFootballTournamentMapper::toDto);
    }

    /**
     * Delete the foiFootballTournament by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FoiFootballTournament : {}", id);
        foiFootballTournamentRepository.deleteById(id);
    }
}
