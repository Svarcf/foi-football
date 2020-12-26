package com.github.svarcf.football.service;

import com.github.svarcf.football.domain.FoiFootballFixture;
import com.github.svarcf.football.domain.FoiFootballTeam;
import com.github.svarcf.football.repository.FoiFootballFixtureRepository;
import com.github.svarcf.football.service.dto.FoiFootballTableDTO;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class FoiFootballTableService {

    private final Logger log = LoggerFactory.getLogger(FoiFootballTableService.class);


    private final FoiFootballFixtureRepository foiFootballFixtureRepository;


    public FoiFootballTableService(FoiFootballFixtureRepository foiFootballFixtureRepository) {
        this.foiFootballFixtureRepository = foiFootballFixtureRepository;
    }

    /**
     * Get one foiFootballTable by tournament id.
     *
     * @param tournamentId the id of the tournament.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public List<FoiFootballTableDTO> findByTournamentId(Long tournamentId) {
        log.debug("Request to get FoiFootballTable : {}", tournamentId);
        Map<FoiFootballTeam, FoiFootballTableDTO> mappedValues = new HashMap<>();
        List<FoiFootballFixture> fixtures = foiFootballFixtureRepository.findAllByTournament_Id(tournamentId);
        for (FoiFootballFixture fixture : fixtures) {
            FoiFootballTableDTO homeTeamTable = mappedValues.computeIfAbsent(fixture.getHomeTeam(), team -> createEmptyTable(fixture.getHomeTeam().getName()));
            FoiFootballTableDTO awayTeamTable = mappedValues.computeIfAbsent(fixture.getAwayTeam(), team -> createEmptyTable(fixture.getAwayTeam().getName()));
            updateTournamentTable(homeTeamTable, awayTeamTable, fixture.getScore().split(":"));
        }

        return mappedValues.values().stream().sorted(Comparator.comparingInt(FoiFootballTableDTO::getPoints).thenComparingInt(FoiFootballTableDTO::getWins).reversed()).collect(Collectors.toList());
    }

    private void updateTournamentTable(FoiFootballTableDTO homeTeamTable, FoiFootballTableDTO awayTeamTable, String[] splitScore) {
        if (!(splitScore[0].equals("-") || splitScore[1].equals("-"))) {
            int homeScore = Integer.parseInt(splitScore[0]);
            int awayScore = Integer.parseInt(splitScore[1]);

            if (homeScore > awayScore) {
                updateWinner(homeTeamTable);
                updateLoser(awayTeamTable);
            } else if (homeScore < awayScore) {
                updateLoser(homeTeamTable);
                updateWinner(awayTeamTable);
            } else {
                updateDraw(homeTeamTable);
                updateDraw(awayTeamTable);
            }
        }
    }

    private FoiFootballTableDTO createEmptyTable(String team) {
        FoiFootballTableDTO table = new FoiFootballTableDTO();
        table.setId(RandomUtils.nextLong());
        table.setWins(0);
        table.setDraws(0);
        table.setLoses(0);
        table.setPoints(0);
        table.setTeam(team);
        return table;
    }

    private void updateWinner(FoiFootballTableDTO table) {
        table.setWins(table.getWins() + 1);
        table.setPoints(table.getPoints() + 3);
    }

    private void updateLoser(FoiFootballTableDTO table) {
        table.setLoses(table.getLoses() + 1);
    }

    private void updateDraw(FoiFootballTableDTO table) {
        table.setDraws(table.getDraws() + 1);
        table.setPoints(table.getPoints() + 1);
    }

}
