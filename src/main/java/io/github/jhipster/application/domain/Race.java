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
 * A Race.
 */
@Entity
@Table(name = "race")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Race implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "raceName")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Dog> names = new HashSet<>();
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

    public Race name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Dog> getNames() {
        return names;
    }

    public Race names(Set<Dog> dogs) {
        this.names = dogs;
        return this;
    }

    public Race addName(Dog dog) {
        this.names.add(dog);
        dog.setRaceName(this);
        return this;
    }

    public Race removeName(Dog dog) {
        this.names.remove(dog);
        dog.setRaceName(null);
        return this;
    }

    public void setNames(Set<Dog> dogs) {
        this.names = dogs;
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
        Race race = (Race) o;
        if (race.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), race.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Race{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
