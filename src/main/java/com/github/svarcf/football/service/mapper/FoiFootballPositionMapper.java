package com.github.svarcf.football.service.mapper;

import com.github.svarcf.football.domain.*;
import com.github.svarcf.football.service.dto.FoiFootballPositionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FoiFootballPosition} and its DTO {@link FoiFootballPositionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FoiFootballPositionMapper extends EntityMapper<FoiFootballPositionDTO, FoiFootballPosition> {


    @Mapping(target = "players", ignore = true)
    @Mapping(target = "removePlayers", ignore = true)
    FoiFootballPosition toEntity(FoiFootballPositionDTO foiFootballPositionDTO);

    default FoiFootballPosition fromId(Long id) {
        if (id == null) {
            return null;
        }
        FoiFootballPosition foiFootballPosition = new FoiFootballPosition();
        foiFootballPosition.setId(id);
        return foiFootballPosition;
    }
}
