package com.github.svarcf.football.service.mapper;

import com.github.svarcf.football.domain.*;
import com.github.svarcf.football.service.dto.FoiFootballTeamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FoiFootballTeam} and its DTO {@link FoiFootballTeamDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FoiFootballTeamMapper extends EntityMapper<FoiFootballTeamDTO, FoiFootballTeam> {


    @Mapping(target = "players", ignore = true)
    @Mapping(target = "removePlayers", ignore = true)
    @Mapping(target = "fixturesHomes", ignore = true)
    @Mapping(target = "removeFixturesHome", ignore = true)
    @Mapping(target = "fixturesAways", ignore = true)
    @Mapping(target = "removeFixturesAway", ignore = true)
    FoiFootballTeam toEntity(FoiFootballTeamDTO foiFootballTeamDTO);

    default FoiFootballTeam fromId(Long id) {
        if (id == null) {
            return null;
        }
        FoiFootballTeam foiFootballTeam = new FoiFootballTeam();
        foiFootballTeam.setId(id);
        return foiFootballTeam;
    }
}
