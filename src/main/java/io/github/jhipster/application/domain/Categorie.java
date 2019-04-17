package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Categorie.
 */
@Entity
@Table(name = "categorie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Categorie implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "jhi_header")
    private Boolean header;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "resultat")
    private Float resultat;

    @Column(name = "jhi_comment")
    private String comment;

    @OneToMany(mappedBy = "subcat")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Categorie> names = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "categorie_criteria",
               joinColumns = @JoinColumn(name = "categorie_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "criteria_id", referencedColumnName = "id"))
    private Set<Criteria> criteria = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("names")
    private Categorie subcat;

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

    public Categorie name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Categorie description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isHeader() {
        return header;
    }

    public Categorie header(Boolean header) {
        this.header = header;
        return this;
    }

    public void setHeader(Boolean header) {
        this.header = header;
    }

    public Float getWeight() {
        return weight;
    }

    public Categorie weight(Float weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getResultat() {
        return resultat;
    }

    public Categorie resultat(Float resultat) {
        this.resultat = resultat;
        return this;
    }

    public void setResultat(Float resultat) {
        this.resultat = resultat;
    }

    public String getComment() {
        return comment;
    }

    public Categorie comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Set<Categorie> getNames() {
        return names;
    }

    public Categorie names(Set<Categorie> categories) {
        this.names = categories;
        return this;
    }

    public Categorie addName(Categorie categorie) {
        this.names.add(categorie);
        categorie.setSubcat(this);
        return this;
    }

    public Categorie removeName(Categorie categorie) {
        this.names.remove(categorie);
        categorie.setSubcat(null);
        return this;
    }

    public void setNames(Set<Categorie> categories) {
        this.names = categories;
    }

    public Set<Criteria> getCriteria() {
        return criteria;
    }

    public Categorie criteria(Set<Criteria> criteria) {
        this.criteria = criteria;
        return this;
    }

    public Categorie addCriteria(Criteria criteria) {
        this.criteria.add(criteria);
        criteria.getCategories().add(this);
        return this;
    }

    public Categorie removeCriteria(Criteria criteria) {
        this.criteria.remove(criteria);
        criteria.getCategories().remove(this);
        return this;
    }

    public void setCriteria(Set<Criteria> criteria) {
        this.criteria = criteria;
    }

    public Categorie getSubcat() {
        return subcat;
    }

    public Categorie subcat(Categorie categorie) {
        this.subcat = categorie;
        return this;
    }

    public void setSubcat(Categorie categorie) {
        this.subcat = categorie;
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
        Categorie categorie = (Categorie) o;
        if (categorie.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categorie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Categorie{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", header='" + isHeader() + "'" +
            ", weight=" + getWeight() +
            ", resultat=" + getResultat() +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
