package io.github.jhipster.application.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "phone")
    private String phone;

    @Column(name = "mail")
    private String mail;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "location_center",
               joinColumns = @JoinColumn(name = "location_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "center_id", referencedColumnName = "id"))
    private Set<Center> centers = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "location_breeding",
               joinColumns = @JoinColumn(name = "location_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "breeding_id", referencedColumnName = "id"))
    private Set<Breeding> breedings = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "location_familly",
               joinColumns = @JoinColumn(name = "location_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "familly_id", referencedColumnName = "id"))
    private Set<Familly> famillies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public Location street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Location postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public Location city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Location state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public Location phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public Location mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Set<Center> getCenters() {
        return centers;
    }

    public Location centers(Set<Center> centers) {
        this.centers = centers;
        return this;
    }

    public Location addCenter(Center center) {
        this.centers.add(center);
        center.getLocations().add(this);
        return this;
    }

    public Location removeCenter(Center center) {
        this.centers.remove(center);
        center.getLocations().remove(this);
        return this;
    }

    public void setCenters(Set<Center> centers) {
        this.centers = centers;
    }

    public Set<Breeding> getBreedings() {
        return breedings;
    }

    public Location breedings(Set<Breeding> breedings) {
        this.breedings = breedings;
        return this;
    }

    public Location addBreeding(Breeding breeding) {
        this.breedings.add(breeding);
        breeding.getLocations().add(this);
        return this;
    }

    public Location removeBreeding(Breeding breeding) {
        this.breedings.remove(breeding);
        breeding.getLocations().remove(this);
        return this;
    }

    public void setBreedings(Set<Breeding> breedings) {
        this.breedings = breedings;
    }

    public Set<Familly> getFamillies() {
        return famillies;
    }

    public Location famillies(Set<Familly> famillies) {
        this.famillies = famillies;
        return this;
    }

    public Location addFamilly(Familly familly) {
        this.famillies.add(familly);
        familly.getLocations().add(this);
        return this;
    }

    public Location removeFamilly(Familly familly) {
        this.famillies.remove(familly);
        familly.getLocations().remove(this);
        return this;
    }

    public void setFamillies(Set<Familly> famillies) {
        this.famillies = famillies;
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
        Location location = (Location) o;
        if (location.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), location.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", street='" + getStreet() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", phone='" + getPhone() + "'" +
            ", mail='" + getMail() + "'" +
            "}";
    }
}
