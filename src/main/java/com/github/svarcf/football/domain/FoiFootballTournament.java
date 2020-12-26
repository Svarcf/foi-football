package com.github.svarcf.football.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A FoiFootballTournament.
 */
@Entity
@Table(name = "foi_football_tournament")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FoiFootballTournament implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "start", nullable = false)
    private LocalDate start;

    @NotNull
    @Column(name = "end", nullable = false)
    private LocalDate end;

    @OneToMany(mappedBy = "tournament")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FoiFootballFixture> fixtures = new HashSet<>();

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

    public FoiFootballTournament name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStart() {
        return start;
    }

    public FoiFootballTournament start(LocalDate start) {
        this.start = start;
        return this;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public FoiFootballTournament end(LocalDate end) {
        this.end = end;
        return this;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Set<FoiFootballFixture> getFixtures() {
        return fixtures;
    }

    public FoiFootballTournament fixtures(Set<FoiFootballFixture> foiFootballFixtures) {
        this.fixtures = foiFootballFixtures;
        return this;
    }

    public FoiFootballTournament addFixtures(FoiFootballFixture foiFootballFixture) {
        this.fixtures.add(foiFootballFixture);
        foiFootballFixture.setTournament(this);
        return this;
    }

    public FoiFootballTournament removeFixtures(FoiFootballFixture foiFootballFixture) {
        this.fixtures.remove(foiFootballFixture);
        foiFootballFixture.setTournament(null);
        return this;
    }

    public void setFixtures(Set<FoiFootballFixture> foiFootballFixtures) {
        this.fixtures = foiFootballFixtures;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FoiFootballTournament)) {
            return false;
        }
        return id != null && id.equals(((FoiFootballTournament) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FoiFootballTournament{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            "}";
    }
}
