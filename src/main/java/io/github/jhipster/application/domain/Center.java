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
 * A Center.
 */
@Entity
@Table(name = "center")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Center implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("centerNames")
    private Dog name;

    @ManyToMany(mappedBy = "centers")
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

    public Center name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dog getName() {
        return name;
    }

    public Center name(Dog dog) {
        this.name = dog;
        return this;
    }

    public void setName(Dog dog) {
        this.name = dog;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public Center locations(Set<Location> locations) {
        this.locations = locations;
        return this;
    }

    public Center addLocation(Location location) {
        this.locations.add(location);
        location.getCenters().add(this);
        return this;
    }

    public Center removeLocation(Location location) {
        this.locations.remove(location);
        location.getCenters().remove(this);
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
        Center center = (Center) o;
        if (center.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), center.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Center{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
