package com.github.svarcf.football.repository;
import com.github.svarcf.football.domain.FoiFootballPosition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FoiFootballPosition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FoiFootballPositionRepository extends JpaRepository<FoiFootballPosition, Long> {

}
