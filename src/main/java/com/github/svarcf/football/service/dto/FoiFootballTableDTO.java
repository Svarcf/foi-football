package com.github.svarcf.football.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

public class FoiFootballTableDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer wins;

    @NotNull
    private Integer draws;

    @NotNull
    private Integer loses;

    @NotNull
    private Integer points;

    private String team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getDraws() {
        return draws;
    }

    public void setDraws(Integer draws) {
        this.draws = draws;
    }

    public Integer getLoses() {
        return loses;
    }

    public void setLoses(Integer loses) {
        this.loses = loses;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FoiFootballTableDTO foiFootballTableDTO = (FoiFootballTableDTO) o;
        if (foiFootballTableDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), foiFootballTableDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FoiFootballTableDTO{" +
            "id=" + getId() +
            ", wins=" + getWins() +
            ", draws=" + getDraws() +
            ", loses=" + getLoses() +
            ", points=" + getPoints() +
            ", team=" + getTeam() +
            "}";
    }
}
