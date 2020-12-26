package com.github.svarcf.football.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.github.svarcf.football.domain.FoiFootballFixture} entity.
 */
public class FoiFootballFixtureDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate eventDate;

    private String round;

    private String venue;

    private String score;


    private Long tournamentId;

    private Long homeTeamId;

    private Long awayTeamId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long foiFootballTournamentId) {
        this.tournamentId = foiFootballTournamentId;
    }

    public Long getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(Long foiFootballTeamId) {
        this.homeTeamId = foiFootballTeamId;
    }

    public Long getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(Long foiFootballTeamId) {
        this.awayTeamId = foiFootballTeamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FoiFootballFixtureDTO foiFootballFixtureDTO = (FoiFootballFixtureDTO) o;
        if (foiFootballFixtureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), foiFootballFixtureDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FoiFootballFixtureDTO{" +
            "id=" + getId() +
            ", eventDate='" + getEventDate() + "'" +
            ", round='" + getRound() + "'" +
            ", venue='" + getVenue() + "'" +
            ", score='" + getScore() + "'" +
            ", tournament=" + getTournamentId() +
            ", homeTeam=" + getHomeTeamId() +
            ", awayTeam=" + getAwayTeamId() +
            "}";
    }
}
