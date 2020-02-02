package com.github.svarcf.football.repository;
import com.github.svarcf.football.domain.FoiFootballPlayer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FoiFootballPlayer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FoiFootballPlayerRepository extends JpaRepository<FoiFootballPlayer, Long> {

}
