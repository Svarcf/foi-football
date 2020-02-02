package com.github.svarcf.football.repository;
import com.github.svarcf.football.domain.FoiFootballTeam;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FoiFootballTeam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FoiFootballTeamRepository extends JpaRepository<FoiFootballTeam, Long> {

}
