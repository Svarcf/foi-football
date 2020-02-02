package com.github.svarcf.football.service;

import com.github.svarcf.football.domain.FoiFootballPosition;
import com.github.svarcf.football.repository.FoiFootballPositionRepository;
import com.github.svarcf.football.service.dto.FoiFootballPositionDTO;
import com.github.svarcf.football.service.mapper.FoiFootballPositionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link FoiFootballPosition}.
 */
@Service
@Transactional
public class FoiFootballPositionService {

    private final Logger log = LoggerFactory.getLogger(FoiFootballPositionService.class);

    private final FoiFootballPositionRepository foiFootballPositionRepository;

    private final FoiFootballPositionMapper foiFootballPositionMapper;

    public FoiFootballPositionService(FoiFootballPositionRepository foiFootballPositionRepository, FoiFootballPositionMapper foiFootballPositionMapper) {
        this.foiFootballPositionRepository = foiFootballPositionRepository;
        this.foiFootballPositionMapper = foiFootballPositionMapper;
    }

    /**
     * Save a foiFootballPosition.
     *
     * @param foiFootballPositionDTO the entity to save.
     * @return the persisted entity.
     */
    public FoiFootballPositionDTO save(FoiFootballPositionDTO foiFootballPositionDTO) {
        log.debug("Request to save FoiFootballPosition : {}", foiFootballPositionDTO);
        FoiFootballPosition foiFootballPosition = foiFootballPositionMapper.toEntity(foiFootballPositionDTO);
        foiFootballPosition = foiFootballPositionRepository.save(foiFootballPosition);
        return foiFootballPositionMapper.toDto(foiFootballPosition);
    }

    /**
     * Get all the foiFootballPositions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FoiFootballPositionDTO> findAll() {
        log.debug("Request to get all FoiFootballPositions");
        return foiFootballPositionRepository.findAll().stream()
            .map(foiFootballPositionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one foiFootballPosition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FoiFootballPositionDTO> findOne(Long id) {
        log.debug("Request to get FoiFootballPosition : {}", id);
        return foiFootballPositionRepository.findById(id)
            .map(foiFootballPositionMapper::toDto);
    }

    /**
     * Delete the foiFootballPosition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FoiFootballPosition : {}", id);
        foiFootballPositionRepository.deleteById(id);
    }
}
