package com.github.svarcf.football.service.mapper;

import com.github.svarcf.football.domain.*;
import com.github.svarcf.football.service.dto.FoiFootballPlayerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FoiFootballPlayer} and its DTO {@link FoiFootballPlayerDTO}.
 */
@Mapper(componentModel = "spring", uses = {FoiFootballPositionMapper.class, FoiFootballTeamMapper.class})
public interface FoiFootballPlayerMapper extends EntityMapper<FoiFootballPlayerDTO, FoiFootballPlayer> {

    @Mapping(source = "position.id", target = "positionId")
    @Mapping(source = "team.id", target = "teamId")
    FoiFootballPlayerDTO toDto(FoiFootballPlayer foiFootballPlayer);

    @Mapping(source = "positionId", target = "position")
    @Mapping(source = "teamId", target = "team")
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
