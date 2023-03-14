package com.example.animalchipization.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@NoArgsConstructor
@Data
public class AnimalVisitedLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private ZonedDateTime dateTimeOfVisitLocationPoint = ZonedDateTime.now();
    @OneToOne
    @JsonProperty("locationPointId")
    private LocationPoint locationPoint;
    @JsonGetter("locationPointId")
    public Long getLocationPointId() {
        return this.locationPoint.getId();
    }
    @ManyToOne
    @JsonIgnore
    private Animal animal;

    public AnimalVisitedLocation(Animal animal, LocationPoint locationPoint) {
        this.animal = animal;
        this.locationPoint = locationPoint;
    }
}
