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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;
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
    private LinkedList<AnimalVisitedLocation> visitedLocations;
    private ZonedDateTime deathDateTime;

    public Animal() {
        this.animalTypes = new HashSet<>();
        this.visitedLocations = new LinkedList<>();
    }


    public boolean checkAnimalAtChippingLocation() {
        AnimalVisitedLocation lastVisitedLocation = this.visitedLocations.peekLast();
        if (lastVisitedLocation != null) {
            return this.chippingLocation.equals(lastVisitedLocation.getLocationPoint());
        }
        return true;
    }

    private boolean isChippingLocationEqualsFirstVisitedLocationPoint() {
        AnimalVisitedLocation firstVisitedLocation = this.visitedLocations.peekFirst();
        if (firstVisitedLocation != null) {
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
        if (this.lifeStatus == LifeStatus.ALIVE) {
            throw new SettingLifeStatusInAliveFromDeadException();
        } else {
            this.lifeStatus = LifeStatus.DEAD;
            this.deathDateTime = ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        }
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
