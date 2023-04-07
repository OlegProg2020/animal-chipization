package com.example.animalchipization.entity;

import com.example.animalchipization.entity.enums.Gender;
import com.example.animalchipization.entity.enums.LifeStatus;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "animal_animal_types",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "animal_type_id"))
    private Set<AnimalType> animalTypes;
    private Float weight;
    private Float length;
    private Float height;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private LifeStatus lifeStatus;
    private ZonedDateTime chippingDateTime;
    @ManyToOne(optional = false)
    private Account chipper;
    @ManyToOne(optional = false)
    private LocationPoint chippingLocation;
    @OneToMany(mappedBy = "animal", fetch = FetchType.LAZY)
    @OrderBy("dateTimeOfVisitLocationPoint ASC, id ASC")
    private List<AnimalVisitedLocation> visitedLocations;
    private ZonedDateTime deathDateTime;

    public Animal() {
        this.animalTypes = new HashSet<>();
        this.visitedLocations = new ArrayList<>();
    }

    public void setLifeStatusToDeadAndSetDeathDateTime() {
        this.lifeStatus = LifeStatus.DEAD;
        this.deathDateTime = ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    public Boolean addAnimalType(AnimalType animalType) {
        return this.animalTypes.add(animalType);
    }

    public Boolean removeAnimalType(AnimalType animalType) {
        return this.animalTypes.remove(animalType);
    }

}
