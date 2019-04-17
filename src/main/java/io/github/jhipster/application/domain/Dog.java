package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Creation Entitiy
 */
@ApiModel(description = "Creation Entitiy")
@Entity
@Table(name = "dog")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dog implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "puce_id")
    private Integer puceId;

    @Size(min = 6, max = 7)
    @Column(name = "tatoo_id", length = 7)
    private String tatooId;

    @Column(name = "passport_id")
    private Integer passportId;

    @Column(name = "name")
    private String name;

    @Column(name = "name_1")
    private String name1;

    @Column(name = "birthday")
    private Instant birthday;

    @Column(name = "sexe")
    private String sexe;

    @Column(name = "hc_num")
    private String hcNum;

    @OneToMany(mappedBy = "name")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Center> centerNames = new HashSet<>();
    @OneToMany(mappedBy = "name")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Breeding> breedingNames = new HashSet<>();
    @OneToMany(mappedBy = "name")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Familly> famillyNames = new HashSet<>();
    @OneToMany(mappedBy = "dog")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Weight> weights = new HashSet<>();
    @OneToMany(mappedBy = "dog")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<HealthSheet> healthSheets = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "dog_date_bilan",
               joinColumns = @JoinColumn(name = "dog_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "date_bilan_id", referencedColumnName = "id"))
    private Set<BilanCheck> dateBilans = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("names")
    private Race raceName;

    @ManyToOne
    @JsonIgnoreProperties("names")
    private Color colorName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPuceId() {
        return puceId;
    }

    public Dog puceId(Integer puceId) {
        this.puceId = puceId;
        return this;
    }

    public void setPuceId(Integer puceId) {
        this.puceId = puceId;
    }

    public String getTatooId() {
        return tatooId;
    }

    public Dog tatooId(String tatooId) {
        this.tatooId = tatooId;
        return this;
    }

    public void setTatooId(String tatooId) {
        this.tatooId = tatooId;
    }

    public Integer getPassportId() {
        return passportId;
    }

    public Dog passportId(Integer passportId) {
        this.passportId = passportId;
        return this;
    }

    public void setPassportId(Integer passportId) {
        this.passportId = passportId;
    }

    public String getName() {
        return name;
    }

    public Dog name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName1() {
        return name1;
    }

    public Dog name1(String name1) {
        this.name1 = name1;
        return this;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public Dog birthday(Instant birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public String getSexe() {
        return sexe;
    }

    public Dog sexe(String sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getHcNum() {
        return hcNum;
    }

    public Dog hcNum(String hcNum) {
        this.hcNum = hcNum;
        return this;
    }

    public void setHcNum(String hcNum) {
        this.hcNum = hcNum;
    }

    public Set<Center> getCenterNames() {
        return centerNames;
    }

    public Dog centerNames(Set<Center> centers) {
        this.centerNames = centers;
        return this;
    }

    public Dog addCenterName(Center center) {
        this.centerNames.add(center);
        center.setName(this);
        return this;
    }

    public Dog removeCenterName(Center center) {
        this.centerNames.remove(center);
        center.setName(null);
        return this;
    }

    public void setCenterNames(Set<Center> centers) {
        this.centerNames = centers;
    }

    public Set<Breeding> getBreedingNames() {
        return breedingNames;
    }

    public Dog breedingNames(Set<Breeding> breedings) {
        this.breedingNames = breedings;
        return this;
    }

    public Dog addBreedingName(Breeding breeding) {
        this.breedingNames.add(breeding);
        breeding.setName(this);
        return this;
    }

    public Dog removeBreedingName(Breeding breeding) {
        this.breedingNames.remove(breeding);
        breeding.setName(null);
        return this;
    }

    public void setBreedingNames(Set<Breeding> breedings) {
        this.breedingNames = breedings;
    }

    public Set<Familly> getFamillyNames() {
        return famillyNames;
    }

    public Dog famillyNames(Set<Familly> famillies) {
        this.famillyNames = famillies;
        return this;
    }

    public Dog addFamillyName(Familly familly) {
        this.famillyNames.add(familly);
        familly.setName(this);
        return this;
    }

    public Dog removeFamillyName(Familly familly) {
        this.famillyNames.remove(familly);
        familly.setName(null);
        return this;
    }

    public void setFamillyNames(Set<Familly> famillies) {
        this.famillyNames = famillies;
    }

    public Set<Weight> getWeights() {
        return weights;
    }

    public Dog weights(Set<Weight> weights) {
        this.weights = weights;
        return this;
    }

    public Dog addWeight(Weight weight) {
        this.weights.add(weight);
        weight.setDog(this);
        return this;
    }

    public Dog removeWeight(Weight weight) {
        this.weights.remove(weight);
        weight.setDog(null);
        return this;
    }

    public void setWeights(Set<Weight> weights) {
        this.weights = weights;
    }

    public Set<HealthSheet> getHealthSheets() {
        return healthSheets;
    }

    public Dog healthSheets(Set<HealthSheet> healthSheets) {
        this.healthSheets = healthSheets;
        return this;
    }

    public Dog addHealthSheet(HealthSheet healthSheet) {
        this.healthSheets.add(healthSheet);
        healthSheet.setDog(this);
        return this;
    }

    public Dog removeHealthSheet(HealthSheet healthSheet) {
        this.healthSheets.remove(healthSheet);
        healthSheet.setDog(null);
        return this;
    }

    public void setHealthSheets(Set<HealthSheet> healthSheets) {
        this.healthSheets = healthSheets;
    }

    public Set<BilanCheck> getDateBilans() {
        return dateBilans;
    }

    public Dog dateBilans(Set<BilanCheck> bilanChecks) {
        this.dateBilans = bilanChecks;
        return this;
    }

    public Dog addDateBilan(BilanCheck bilanCheck) {
        this.dateBilans.add(bilanCheck);
        bilanCheck.getDogs().add(this);
        return this;
    }

    public Dog removeDateBilan(BilanCheck bilanCheck) {
        this.dateBilans.remove(bilanCheck);
        bilanCheck.getDogs().remove(this);
        return this;
    }

    public void setDateBilans(Set<BilanCheck> bilanChecks) {
        this.dateBilans = bilanChecks;
    }

    public Race getRaceName() {
        return raceName;
    }

    public Dog raceName(Race race) {
        this.raceName = race;
        return this;
    }

    public void setRaceName(Race race) {
        this.raceName = race;
    }

    public Color getColorName() {
        return colorName;
    }

    public Dog colorName(Color color) {
        this.colorName = color;
        return this;
    }

    public void setColorName(Color color) {
        this.colorName = color;
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
        Dog dog = (Dog) o;
        if (dog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dog{" +
            "id=" + getId() +
            ", puceId=" + getPuceId() +
            ", tatooId='" + getTatooId() + "'" +
            ", passportId=" + getPassportId() +
            ", name='" + getName() + "'" +
            ", name1='" + getName1() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", hcNum='" + getHcNum() + "'" +
            "}";
    }
}
