package io.github.jhipster.application.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BilanCheck.
 */
@Entity
@Table(name = "bilan_check")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BilanCheck implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private Instant date;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "bilan_check_bilan_type",
               joinColumns = @JoinColumn(name = "bilan_check_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "bilan_type_id", referencedColumnName = "id"))
    private Set<BilanType> bilanTypes = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "bilan_check_criteria",
               joinColumns = @JoinColumn(name = "bilan_check_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "criteria_id", referencedColumnName = "id"))
    private Set<Criteria> criteria = new HashSet<>();

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

    public BilanCheck date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Set<BilanType> getBilanTypes() {
        return bilanTypes;
    }

    public BilanCheck bilanTypes(Set<BilanType> bilanTypes) {
        this.bilanTypes = bilanTypes;
        return this;
    }

    public BilanCheck addBilanType(BilanType bilanType) {
        this.bilanTypes.add(bilanType);
        bilanType.getBilanChecks().add(this);
        return this;
    }

    public BilanCheck removeBilanType(BilanType bilanType) {
        this.bilanTypes.remove(bilanType);
        bilanType.getBilanChecks().remove(this);
        return this;
    }

    public void setBilanTypes(Set<BilanType> bilanTypes) {
        this.bilanTypes = bilanTypes;
    }

    public Set<Criteria> getCriteria() {
        return criteria;
    }

    public BilanCheck criteria(Set<Criteria> criteria) {
        this.criteria = criteria;
        return this;
    }

    public BilanCheck addCriteria(Criteria criteria) {
        this.criteria.add(criteria);
        criteria.getBilanChecks().add(this);
        return this;
    }

    public BilanCheck removeCriteria(Criteria criteria) {
        this.criteria.remove(criteria);
        criteria.getBilanChecks().remove(this);
        return this;
    }

    public void setCriteria(Set<Criteria> criteria) {
        this.criteria = criteria;
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
        BilanCheck bilanCheck = (BilanCheck) o;
        if (bilanCheck.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bilanCheck.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BilanCheck{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
