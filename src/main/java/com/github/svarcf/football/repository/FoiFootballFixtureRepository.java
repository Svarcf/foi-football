package com.github.svarcf.football.repository;
import com.github.svarcf.football.domain.FoiFootballFixture;
import com.github.svarcf.football.domain.FoiFootballTournament;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the FoiFootballFixture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FoiFootballFixtureRepository extends JpaRepository<FoiFootballFixture, Long> {

    List<FoiFootballFixture> findAllByTournament_Id(Long id);
}
