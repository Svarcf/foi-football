package com.github.svarcf.football.service.mapper;

import com.github.svarcf.football.domain.*;
import com.github.svarcf.football.service.dto.FoiFootballPlayerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FoiFootballPlayer} and its DTO {@link FoiFootballPlayerDTO}.
 */
@Mapper(componentModel = "spring", uses = {FoiFootballPositionMapper.class, FoiFootballTeamMapper.class})
public interface FoiFootballPlayerMapper extends EntityMapper<FoiFootballPlayerDTO, FoiFootballPlayer> {

    @Mapping(source = "foiFootballPosition.id", target = "foiFootballPositionId")
    @Mapping(source = "foiFootballPosition.name", target = "foiFootballPositionName")
    @Mapping(source = "foiFootballTeam.id", target = "foiFootballTeamId")
    @Mapping(source = "foiFootballTeam.name", target = "foiFootballTeamName")
    FoiFootballPlayerDTO toDto(FoiFootballPlayer foiFootballPlayer);

    @Mapping(source = "foiFootballPositionId", target = "foiFootballPosition")
    @Mapping(source = "foiFootballTeamId", target = "foiFootballTeam")
    FoiFootballPlayer toEntity(FoiFootballPlayerDTO foiFootballPlayerDTO);

    default FoiFootballPlayer fromId(Long id) {
        if (id == null) {
            return null;
        }
        FoiFootballPlayer foiFootballPlayer = new FoiFootballPlayer();
        foiFootballPlayer.setId(id);
        return foiFootballPlayer;
    }
}
