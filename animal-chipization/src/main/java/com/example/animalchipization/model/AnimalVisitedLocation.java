package com.example.animalchipization.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AnimalVisitedLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private ZonedDateTime dateTimeOfVisitLocationPoint = ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    @OneToOne
    @JsonProperty("locationPointId")
    @NotNull
    private LocationPoint locationPoint;

    @JsonGetter("locationPointId")
    public Long getLocationPointId() {
        return this.locationPoint.getId();
    }

    @ManyToOne
    @JsonIgnore
    @JsonBackReference
    @JoinColumn(name = "animal_id")
    private Animal animal;

    public AnimalVisitedLocation(Animal animal, LocationPoint locationPoint) {
        this.animal = animal;
        this.locationPoint = locationPoint;
    }
}
