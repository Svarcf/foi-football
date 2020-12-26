package com.github.svarcf.football.service;

import com.github.svarcf.football.domain.FoiFootballFixture;
import com.github.svarcf.football.repository.FoiFootballFixtureRepository;
import com.github.svarcf.football.service.dto.FoiFootballFixtureDTO;
import com.github.svarcf.football.service.mapper.FoiFootballFixtureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link FoiFootballFixture}.
 */
@Service
@Transactional
public class FoiFootballFixtureService {

    private final Logger log = LoggerFactory.getLogger(FoiFootballFixtureService.class);

    private final FoiFootballFixtureRepository foiFootballFixtureRepository;

    private final FoiFootballFixtureMapper foiFootballFixtureMapper;

    public FoiFootballFixtureService(FoiFootballFixtureRepository foiFootballFixtureRepository, FoiFootballFixtureMapper foiFootballFixtureMapper) {
        this.foiFootballFixtureRepository = foiFootballFixtureRepository;
        this.foiFootballFixtureMapper = foiFootballFixtureMapper;
    }

    /**
     * Save a foiFootballFixture.
     *
     * @param foiFootballFixtureDTO the entity to save.
     * @return the persisted entity.
     */
    public FoiFootballFixtureDTO save(FoiFootballFixtureDTO foiFootballFixtureDTO) {
        log.debug("Request to save FoiFootballFixture : {}", foiFootballFixtureDTO);
        FoiFootballFixture foiFootballFixture = foiFootballFixtureMapper.toEntity(foiFootballFixtureDTO);
        foiFootballFixture = foiFootballFixtureRepository.save(foiFootballFixture);
        return foiFootballFixtureMapper.toDto(foiFootballFixture);
    }

    /**
     * Get all the foiFootballFixtures.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FoiFootballFixtureDTO> findAll() {
        log.debug("Request to get all FoiFootballFixtures");
        return foiFootballFixtureRepository.findAll().stream()
            .map(foiFootballFixtureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one foiFootballFixture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FoiFootballFixtureDTO> findOne(Long id) {
        log.debug("Request to get FoiFootballFixture : {}", id);
        return foiFootballFixtureRepository.findById(id)
            .map(foiFootballFixtureMapper::toDto);
    }

    /**
     * Delete the foiFootballFixture by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FoiFootballFixture : {}", id);
        foiFootballFixtureRepository.deleteById(id);
    }
}
