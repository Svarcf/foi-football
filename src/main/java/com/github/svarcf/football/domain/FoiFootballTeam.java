package com.github.svarcf.football.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A FoiFootballTeam.
 */
@Entity
@Table(name = "foi_football_team")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FoiFootballTeam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "logo")
    private String logo;

    @NotNull
    @Column(name = "venue_name", nullable = false)
    private String venueName;

    @NotNull
    @Column(name = "venue_city", nullable = false)
    private String venueCity;

    @OneToMany(mappedBy = "team")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FoiFootballPlayer> players = new HashSet<>();

    @OneToMany(mappedBy = "homeTeam")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FoiFootballFixture> fixturesHomes = new HashSet<>();

    @OneToMany(mappedBy = "awayTeam")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FoiFootballFixture> fixturesAways = new HashSet<>();

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

    public FoiFootballTeam name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public FoiFootballTeam logo(String logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getVenueName() {
        return venueName;
    }

    public FoiFootballTeam venueName(String venueName) {
        this.venueName = venueName;
        return this;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueCity() {
        return venueCity;
    }

    public FoiFootballTeam venueCity(String venueCity) {
        this.venueCity = venueCity;
        return this;
    }

    public void setVenueCity(String venueCity) {
        this.venueCity = venueCity;
    }

    public Set<FoiFootballPlayer> getPlayers() {
        return players;
    }

    public FoiFootballTeam players(Set<FoiFootballPlayer> foiFootballPlayers) {
        this.players = foiFootballPlayers;
        return this;
    }

    public FoiFootballTeam addPlayers(FoiFootballPlayer foiFootballPlayer) {
        this.players.add(foiFootballPlayer);
        foiFootballPlayer.setTeam(this);
        return this;
    }

    public FoiFootballTeam removePlayers(FoiFootballPlayer foiFootballPlayer) {
        this.players.remove(foiFootballPlayer);
        foiFootballPlayer.setTeam(null);
        return this;
    }

    public void setPlayers(Set<FoiFootballPlayer> foiFootballPlayers) {
        this.players = foiFootballPlayers;
    }

    public Set<FoiFootballFixture> getFixturesHomes() {
        return fixturesHomes;
    }

    public FoiFootballTeam fixturesHomes(Set<FoiFootballFixture> foiFootballFixtures) {
        this.fixturesHomes = foiFootballFixtures;
        return this;
    }

    public FoiFootballTeam addFixturesHome(FoiFootballFixture foiFootballFixture) {
        this.fixturesHomes.add(foiFootballFixture);
        foiFootballFixture.setHomeTeam(this);
        return this;
    }

    public FoiFootballTeam removeFixturesHome(FoiFootballFixture foiFootballFixture) {
        this.fixturesHomes.remove(foiFootballFixture);
        foiFootballFixture.setHomeTeam(null);
        return this;
    }

    public void setFixturesHomes(Set<FoiFootballFixture> foiFootballFixtures) {
        this.fixturesHomes = foiFootballFixtures;
    }

    public Set<FoiFootballFixture> getFixturesAways() {
        return fixturesAways;
    }

    public FoiFootballTeam fixturesAways(Set<FoiFootballFixture> foiFootballFixtures) {
        this.fixturesAways = foiFootballFixtures;
        return this;
    }

    public FoiFootballTeam addFixturesAway(FoiFootballFixture foiFootballFixture) {
        this.fixturesAways.add(foiFootballFixture);
        foiFootballFixture.setAwayTeam(this);
        return this;
    }

    public FoiFootballTeam removeFixturesAway(FoiFootballFixture foiFootballFixture) {
        this.fixturesAways.remove(foiFootballFixture);
        foiFootballFixture.setAwayTeam(null);
        return this;
    }

    public void setFixturesAways(Set<FoiFootballFixture> foiFootballFixtures) {
        this.fixturesAways = foiFootballFixtures;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FoiFootballTeam)) {
            return false;
        }
        return id != null && id.equals(((FoiFootballTeam) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FoiFootballTeam{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", logo='" + getLogo() + "'" +
            ", venueName='" + getVenueName() + "'" +
            ", venueCity='" + getVenueCity() + "'" +
            "}";
    }
}
