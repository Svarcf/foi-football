package com.github.svarcf.football.service;

import com.github.svarcf.football.domain.FoiFootballTeam;
import com.github.svarcf.football.repository.FoiFootballTeamRepository;
import com.github.svarcf.football.service.dto.FoiFootballTeamDTO;
import com.github.svarcf.football.service.mapper.FoiFootballTeamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link FoiFootballTeam}.
 */
@Service
@Transactional
public class FoiFootballTeamService {

    private final Logger log = LoggerFactory.getLogger(FoiFootballTeamService.class);

    private final FoiFootballTeamRepository foiFootballTeamRepository;

    private final FoiFootballTeamMapper foiFootballTeamMapper;

    public FoiFootballTeamService(FoiFootballTeamRepository foiFootballTeamRepository, FoiFootballTeamMapper foiFootballTeamMapper) {
        this.foiFootballTeamRepository = foiFootballTeamRepository;
        this.foiFootballTeamMapper = foiFootballTeamMapper;
    }

    /**
     * Save a foiFootballTeam.
     *
     * @param foiFootballTeamDTO the entity to save.
     * @return the persisted entity.
     */
    public FoiFootballTeamDTO save(FoiFootballTeamDTO foiFootballTeamDTO) {
        log.debug("Request to save FoiFootballTeam : {}", foiFootballTeamDTO);
        FoiFootballTeam foiFootballTeam = foiFootballTeamMapper.toEntity(foiFootballTeamDTO);
        foiFootballTeam = foiFootballTeamRepository.save(foiFootballTeam);
        return foiFootballTeamMapper.toDto(foiFootballTeam);
    }

    /**
     * Get all the foiFootballTeams.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FoiFootballTeamDTO> findAll() {
        log.debug("Request to get all FoiFootballTeams");
        return foiFootballTeamRepository.findAll().stream()
            .map(foiFootballTeamMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one foiFootballTeam by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FoiFootballTeamDTO> findOne(Long id) {
        log.debug("Request to get FoiFootballTeam : {}", id);
        return foiFootballTeamRepository.findById(id)
            .map(foiFootballTeamMapper::toDto);
    }

    /**
     * Delete the foiFootballTeam by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FoiFootballTeam : {}", id);
        foiFootballTeamRepository.deleteById(id);
    }
}
