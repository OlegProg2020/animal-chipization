package com.example.animalchipization.entity;

import com.example.animalchipization.entity.enums.Gender;
import com.example.animalchipization.entity.enums.LifeStatus;
import com.example.animalchipization.exception.DuplicateCollectionItemException;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToMany(fetch = FetchType.EAGER)
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
    @OneToMany(mappedBy = "animal", fetch = FetchType.EAGER)
    @OrderBy("dateTimeOfVisitLocationPoint ASC, id ASC")
    private List<AnimalVisitedLocation> visitedLocations;
    private ZonedDateTime deathDateTime;

    public Animal() {
        this.animalTypes = new HashSet<>();
        this.visitedLocations = new LinkedList<>();
    }


    public void addAnimalType(AnimalType animalType) {
        if (!this.animalTypes.add(animalType)) {
            throw new DuplicateCollectionItemException();
        }
    }

    public void removeAnimalType(AnimalType animalType) {
        if (!this.animalTypes.remove(animalType)) {
            throw new NoSuchElementException();
        }
    }

}
