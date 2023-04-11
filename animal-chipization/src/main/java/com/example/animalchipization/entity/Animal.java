package com.example.animalchipization.entity;

import com.example.animalchipization.entity.enums.Gender;
import com.example.animalchipization.entity.enums.LifeStatus;
import com.example.animalchipization.exception.AnimalIsAlreadyAtThisPointException;
import com.example.animalchipization.exception.DuplicateCollectionItemException;
import com.example.animalchipization.exception.SettingLifeStatusInAliveFromDeadException;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
        this.visitedLocations = new LinkedList<>();
    }


    public boolean isAnimalAtChippingLocation() {
        int lastVisitedLocationIndex = this.visitedLocations.size() - 1;
        if (lastVisitedLocationIndex >= 0) {
            AnimalVisitedLocation lastVisitedLocation = this.visitedLocations.get(lastVisitedLocationIndex);
            return this.chippingLocation.equals(lastVisitedLocation.getLocationPoint());
        }
        return true;
    }

    private boolean isChippingLocationEqualsFirstVisitedLocationPoint() {
        if (this.visitedLocations.size() > 0) {
            AnimalVisitedLocation firstVisitedLocation = this.visitedLocations.get(0);
            return firstVisitedLocation.getLocationPoint().equals(this.getChippingLocation());
        }
        return false;
    }

    public void setAndValidateChippingLocation(LocationPoint locationPoint) {
        this.chippingLocation = locationPoint;
        if (this.isChippingLocationEqualsFirstVisitedLocationPoint()) {
            throw new AnimalIsAlreadyAtThisPointException();
        }
    }

    private void setLifeStatusToDeadAndSetDeathDateTime() {
        this.lifeStatus = LifeStatus.DEAD;
        this.deathDateTime = ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    public void setAndValidateLifeStatus(LifeStatus newLifeStatus) {
        if (this.lifeStatus == LifeStatus.ALIVE) {
            if (newLifeStatus == LifeStatus.DEAD) {
                this.setLifeStatusToDeadAndSetDeathDateTime();
            }
        } else {
            if (newLifeStatus == LifeStatus.ALIVE) {
                throw new SettingLifeStatusInAliveFromDeadException();
            }
        }
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
