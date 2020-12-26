package com.github.svarcf.football.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.github.svarcf.football.domain.FoiFootballPlayer} entity.
 */
public class FoiFootballPlayerDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer number;

    private Long positionId;

    private Long teamId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long foiFootballPositionId) {
        this.positionId = foiFootballPositionId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long foiFootballTeamId) {
        this.teamId = foiFootballTeamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FoiFootballPlayerDTO foiFootballPlayerDTO = (FoiFootballPlayerDTO) o;
        if (foiFootballPlayerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), foiFootballPlayerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FoiFootballPlayerDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", number=" + getNumber() +
            ", position=" + getPositionId() +
            ", team=" + getTeamId() +
            "}";
    }
}
