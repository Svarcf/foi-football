package com.github.svarcf.football.repository;
import com.github.svarcf.football.domain.FoiFootballFixture;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FoiFootballFixture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FoiFootballFixtureRepository extends JpaRepository<FoiFootballFixture, Long> {

}
