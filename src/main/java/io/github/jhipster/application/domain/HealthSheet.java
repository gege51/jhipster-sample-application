package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A HealthSheet.
 */
@Entity
@Table(name = "health_sheet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HealthSheet implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private Instant date;

    @Column(name = "reason")
    private String reason;

    @Column(name = "jhi_comment")
    private String comment;

    @ManyToOne
    @JsonIgnoreProperties("healthSheets")
    private Dog dog;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public HealthSheet date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public HealthSheet reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getComment() {
        return comment;
    }

    public HealthSheet comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Dog getDog() {
        return dog;
    }

    public HealthSheet dog(Dog dog) {
        this.dog = dog;
        return this;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HealthSheet healthSheet = (HealthSheet) o;
        if (healthSheet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), healthSheet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HealthSheet{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", reason='" + getReason() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
