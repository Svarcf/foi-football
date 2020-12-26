package com.github.svarcf.football.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A FoiFootballPlayer.
 */
@Entity
@Table(name = "foi_football_player")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FoiFootballPlayer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "number", nullable = false)
    private Integer number;

    @ManyToOne
    @JsonIgnoreProperties("players")
    private FoiFootballPosition position;

    @ManyToOne
    @JsonIgnoreProperties("players")
    private FoiFootballTeam team;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public FoiFootballPlayer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public FoiFootballPlayer number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public FoiFootballPosition getPosition() {
        return position;
    }

    public FoiFootballPlayer position(FoiFootballPosition foiFootballPosition) {
        this.position = foiFootballPosition;
        return this;
    }

    public void setPosition(FoiFootballPosition foiFootballPosition) {
        this.position = foiFootballPosition;
    }

    public FoiFootballTeam getTeam() {
        return team;
    }

    public FoiFootballPlayer team(FoiFootballTeam foiFootballTeam) {
        this.team = foiFootballTeam;
        return this;
    }

    public void setTeam(FoiFootballTeam foiFootballTeam) {
        this.team = foiFootballTeam;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FoiFootballPlayer)) {
            return false;
        }
        return id != null && id.equals(((FoiFootballPlayer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FoiFootballPlayer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", number=" + getNumber() +
            "}";
    }
}
