package com.github.svarcf.football.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A FoiFootballFixture.
 */
@Entity
@Table(name = "foi_football_fixture")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FoiFootballFixture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @Column(name = "round")
    private String round;

    @Column(name = "venue")
    private String venue;

    @Column(name = "score")
    private String score;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public FoiFootballFixture eventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getRound() {
        return round;
    }

    public FoiFootballFixture round(String round) {
        this.round = round;
        return this;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getVenue() {
        return venue;
    }

    public FoiFootballFixture venue(String venue) {
        this.venue = venue;
        return this;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getScore() {
        return score;
    }

    public FoiFootballFixture score(String score) {
        this.score = score;
        return this;
    }

    public void setScore(String score) {
        this.score = score;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FoiFootballFixture)) {
            return false;
        }
        return id != null && id.equals(((FoiFootballFixture) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FoiFootballFixture{" +
            "id=" + getId() +
            ", eventDate='" + getEventDate() + "'" +
            ", round='" + getRound() + "'" +
            ", venue='" + getVenue() + "'" +
            ", score='" + getScore() + "'" +
            "}";
    }
}
