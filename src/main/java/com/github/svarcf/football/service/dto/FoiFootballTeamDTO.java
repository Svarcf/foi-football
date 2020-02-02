package com.github.svarcf.football.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.github.svarcf.football.domain.FoiFootballTeam} entity.
 */
public class FoiFootballTeamDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String logo;

    @NotNull
    private String venueName;

    @NotNull
    private String venueCity;


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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueCity() {
        return venueCity;
    }

    public void setVenueCity(String venueCity) {
        this.venueCity = venueCity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FoiFootballTeamDTO foiFootballTeamDTO = (FoiFootballTeamDTO) o;
        if (foiFootballTeamDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), foiFootballTeamDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FoiFootballTeamDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", logo='" + getLogo() + "'" +
            ", venueName='" + getVenueName() + "'" +
            ", venueCity='" + getVenueCity() + "'" +
            "}";
    }
}
