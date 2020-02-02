package com.github.svarcf.football.service.mapper;

import com.github.svarcf.football.domain.*;
import com.github.svarcf.football.service.dto.FoiFootballFixtureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FoiFootballFixture} and its DTO {@link FoiFootballFixtureDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FoiFootballFixtureMapper extends EntityMapper<FoiFootballFixtureDTO, FoiFootballFixture> {



    default FoiFootballFixture fromId(Long id) {
        if (id == null) {
            return null;
        }
        FoiFootballFixture foiFootballFixture = new FoiFootballFixture();
        foiFootballFixture.setId(id);
        return foiFootballFixture;
    }
}
