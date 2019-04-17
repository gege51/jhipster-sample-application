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
 * A BilanType.
 */
@Entity
@Table(name = "bilan_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BilanType implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "bilanTypes")
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

    public String getType() {
        return type;
    }

    public BilanType type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public BilanType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BilanCheck> getBilanChecks() {
        return bilanChecks;
    }

    public BilanType bilanChecks(Set<BilanCheck> bilanChecks) {
        this.bilanChecks = bilanChecks;
        return this;
    }

    public BilanType addBilanCheck(BilanCheck bilanCheck) {
        this.bilanChecks.add(bilanCheck);
        bilanCheck.getBilanTypes().add(this);
        return this;
    }

    public BilanType removeBilanCheck(BilanCheck bilanCheck) {
        this.bilanChecks.remove(bilanCheck);
        bilanCheck.getBilanTypes().remove(this);
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
        BilanType bilanType = (BilanType) o;
        if (bilanType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bilanType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BilanType{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
