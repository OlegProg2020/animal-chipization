package com.example.animalchipization.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class AnimalVisitedLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private ZonedDateTime dateTimeOfVisitLocationPoint = ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    @ManyToOne(optional = false)
    private LocationPoint locationPoint;
    @ManyToOne(optional = false)
    private Animal animal;

}
