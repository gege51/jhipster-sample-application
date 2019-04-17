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
 * A Familly.
 */
@Entity
@Table(name = "familly")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Familly implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("famillyNames")
    private Dog name;

    @ManyToMany(mappedBy = "famillies")
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

    public Familly name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dog getName() {
        return name;
    }

    public Familly name(Dog dog) {
        this.name = dog;
        return this;
    }

    public void setName(Dog dog) {
        this.name = dog;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public Familly locations(Set<Location> locations) {
        this.locations = locations;
        return this;
    }

    public Familly addLocation(Location location) {
        this.locations.add(location);
        location.getFamillies().add(this);
        return this;
    }

    public Familly removeLocation(Location location) {
        this.locations.remove(location);
        location.getFamillies().remove(this);
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
        Familly familly = (Familly) o;
        if (familly.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), familly.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Familly{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
