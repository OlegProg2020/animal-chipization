package com.example.animalchipization.entity;

import com.example.animalchipization.entity.enums.LifeStatus;
import com.example.animalchipization.exception.AnimalIsAlreadyAtThisPointException;
import com.example.animalchipization.exception.AttemptAddingLocationToAnimalWithDeadStatusException;
import com.example.animalchipization.exception.FirstLocationPointConcidesWithChippingPointException;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class AnimalVisitedLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private ZonedDateTime dateTimeOfVisitLocationPoint;
    @ManyToOne(optional = false)
    private LocationPoint locationPoint;
    @ManyToOne(optional = false)
    private Animal animal;

    public AnimalVisitedLocation() {
        this.dateTimeOfVisitLocationPoint = ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

}
