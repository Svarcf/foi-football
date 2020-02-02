package com.github.svarcf.football.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A FoiFootballPosition.
 */
@Entity
@Table(name = "foi_football_position")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FoiFootballPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "foiFootballPosition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FoiFootballPlayer> players = new HashSet<>();

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

    public FoiFootballPosition name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<FoiFootballPlayer> getPlayers() {
        return players;
    }

    public FoiFootballPosition players(Set<FoiFootballPlayer> foiFootballPlayers) {
        this.players = foiFootballPlayers;
        return this;
    }

    public FoiFootballPosition addPlayers(FoiFootballPlayer foiFootballPlayer) {
        this.players.add(foiFootballPlayer);
        foiFootballPlayer.setFoiFootballPosition(this);
        return this;
    }

    public FoiFootballPosition removePlayers(FoiFootballPlayer foiFootballPlayer) {
        this.players.remove(foiFootballPlayer);
        foiFootballPlayer.setFoiFootballPosition(null);
        return this;
    }

    public void setPlayers(Set<FoiFootballPlayer> foiFootballPlayers) {
        this.players = foiFootballPlayers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FoiFootballPosition)) {
            return false;
        }
        return id != null && id.equals(((FoiFootballPosition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FoiFootballPosition{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
