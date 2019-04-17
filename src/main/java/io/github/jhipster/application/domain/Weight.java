package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Weight.
 */
@Entity
@Table(name = "weight")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Weight implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private Instant date;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "qty_ration")
    private Integer qtyRation;

    @Column(name = "nb_ration")
    private Integer nbRation;

    @Column(name = "observation")
    private String observation;

    @ManyToOne
    @JsonIgnoreProperties("weights")
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

    public Weight date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Float getWeight() {
        return weight;
    }

    public Weight weight(Float weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Integer getQtyRation() {
        return qtyRation;
    }

    public Weight qtyRation(Integer qtyRation) {
        this.qtyRation = qtyRation;
        return this;
    }

    public void setQtyRation(Integer qtyRation) {
        this.qtyRation = qtyRation;
    }

    public Integer getNbRation() {
        return nbRation;
    }

    public Weight nbRation(Integer nbRation) {
        this.nbRation = nbRation;
        return this;
    }

    public void setNbRation(Integer nbRation) {
        this.nbRation = nbRation;
    }

    public String getObservation() {
        return observation;
    }

    public Weight observation(String observation) {
        this.observation = observation;
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Dog getDog() {
        return dog;
    }

    public Weight dog(Dog dog) {
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
        Weight weight = (Weight) o;
        if (weight.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), weight.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Weight{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", weight=" + getWeight() +
            ", qtyRation=" + getQtyRation() +
            ", nbRation=" + getNbRation() +
            ", observation='" + getObservation() + "'" +
            "}";
    }
}
