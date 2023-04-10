package com.example.animalchipization.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

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
