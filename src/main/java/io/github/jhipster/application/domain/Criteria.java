package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Criteria.
 */
@Entity
@Table(name = "criteria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Criteria implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "jhi_value")
    private Float value;

    @Column(name = "jhi_comment")
    private String comment;

    @ManyToMany(mappedBy = "criteria")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Categorie> categories = new HashSet<>();

    @ManyToMany(mappedBy = "criteria")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<BilanCheck> bilanChecks = new HashSet<>();

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

    public Criteria name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Criteria description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getWeight() {
        return weight;
    }

    public Criteria weight(Float weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getValue() {
        return value;
    }

    public Criteria value(Float value) {
        this.value = value;
        return this;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public Criteria comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Set<Categorie> getCategories() {
        return categories;
    }

    public Criteria categories(Set<Categorie> categories) {
        this.categories = categories;
        return this;
    }

    public Criteria addCategorie(Categorie categorie) {
        this.categories.add(categorie);
        categorie.getCriteria().add(this);
        return this;
    }

    public Criteria removeCategorie(Categorie categorie) {
        this.categories.remove(categorie);
        categorie.getCriteria().remove(this);
        return this;
    }

    public void setCategories(Set<Categorie> categories) {
        this.categories = categories;
    }

    public Set<BilanCheck> getBilanChecks() {
        return bilanChecks;
    }

    public Criteria bilanChecks(Set<BilanCheck> bilanChecks) {
        this.bilanChecks = bilanChecks;
        return this;
    }

    public Criteria addBilanCheck(BilanCheck bilanCheck) {
        this.bilanChecks.add(bilanCheck);
        bilanCheck.getCriteria().add(this);
        return this;
    }

    public Criteria removeBilanCheck(BilanCheck bilanCheck) {
        this.bilanChecks.remove(bilanCheck);
        bilanCheck.getCriteria().remove(this);
        return this;
    }

    public void setBilanChecks(Set<BilanCheck> bilanChecks) {
        this.bilanChecks = bilanChecks;
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
        Criteria criteria = (Criteria) o;
        if (criteria.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), criteria.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Criteria{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", weight=" + getWeight() +
            ", value=" + getValue() +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
