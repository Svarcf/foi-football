package com.github.svarcf.football.repository;
import com.github.svarcf.football.domain.FoiFootballTournament;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FoiFootballTournament entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FoiFootballTournamentRepository extends JpaRepository<FoiFootballTournament, Long> {

}
