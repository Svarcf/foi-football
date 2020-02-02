package com.github.svarcf.football.service.mapper;

import com.github.svarcf.football.domain.*;
import com.github.svarcf.football.service.dto.FoiFootballTournamentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FoiFootballTournament} and its DTO {@link FoiFootballTournamentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FoiFootballTournamentMapper extends EntityMapper<FoiFootballTournamentDTO, FoiFootballTournament> {



    default FoiFootballTournament fromId(Long id) {
        if (id == null) {
            return null;
        }
        FoiFootballTournament foiFootballTournament = new FoiFootballTournament();
        foiFootballTournament.setId(id);
        return foiFootballTournament;
    }
}
