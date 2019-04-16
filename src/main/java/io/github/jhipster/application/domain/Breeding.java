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
 * A Breeding.
 */
@Entity
@Table(name = "breeding")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Breeding implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("breedingNames")
    private Dog name;

    @ManyToMany(mappedBy = "breedings")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Location> locations = new HashSet<>();

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

    public Breeding name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dog getName() {
        return name;
    }

    public Breeding name(Dog dog) {
        this.name = dog;
        return this;
    }

    public void setName(Dog dog) {
        this.name = dog;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public Breeding locations(Set<Location> locations) {
        this.locations = locations;
        return this;
    }

    public Breeding addLocation(Location location) {
        this.locations.add(location);
        location.getBreedings().add(this);
        return this;
    }

    public Breeding removeLocation(Location location) {
        this.locations.remove(location);
        location.getBreedings().remove(this);
        return this;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
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
        Breeding breeding = (Breeding) o;
        if (breeding.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), breeding.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Breeding{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
