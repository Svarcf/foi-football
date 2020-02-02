package com.github.svarcf.football.service;

import com.github.svarcf.football.domain.FoiFootballPlayer;
import com.github.svarcf.football.repository.FoiFootballPlayerRepository;
import com.github.svarcf.football.service.dto.FoiFootballPlayerDTO;
import com.github.svarcf.football.service.mapper.FoiFootballPlayerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link FoiFootballPlayer}.
 */
@Service
@Transactional
public class FoiFootballPlayerService {

    private final Logger log = LoggerFactory.getLogger(FoiFootballPlayerService.class);

    private final FoiFootballPlayerRepository foiFootballPlayerRepository;

    private final FoiFootballPlayerMapper foiFootballPlayerMapper;

    public FoiFootballPlayerService(FoiFootballPlayerRepository foiFootballPlayerRepository, FoiFootballPlayerMapper foiFootballPlayerMapper) {
        this.foiFootballPlayerRepository = foiFootballPlayerRepository;
        this.foiFootballPlayerMapper = foiFootballPlayerMapper;
    }

    /**
     * Save a foiFootballPlayer.
     *
     * @param foiFootballPlayerDTO the entity to save.
     * @return the persisted entity.
     */
    public FoiFootballPlayerDTO save(FoiFootballPlayerDTO foiFootballPlayerDTO) {
        log.debug("Request to save FoiFootballPlayer : {}", foiFootballPlayerDTO);
        FoiFootballPlayer foiFootballPlayer = foiFootballPlayerMapper.toEntity(foiFootballPlayerDTO);
        foiFootballPlayer = foiFootballPlayerRepository.save(foiFootballPlayer);
        return foiFootballPlayerMapper.toDto(foiFootballPlayer);
    }

    /**
     * Get all the foiFootballPlayers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FoiFootballPlayerDTO> findAll() {
        log.debug("Request to get all FoiFootballPlayers");
        return foiFootballPlayerRepository.findAll().stream()
            .map(foiFootballPlayerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one foiFootballPlayer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FoiFootballPlayerDTO> findOne(Long id) {
        log.debug("Request to get FoiFootballPlayer : {}", id);
        return foiFootballPlayerRepository.findById(id)
            .map(foiFootballPlayerMapper::toDto);
    }

    /**
     * Delete the foiFootballPlayer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FoiFootballPlayer : {}", id);
        foiFootballPlayerRepository.deleteById(id);
    }
}
