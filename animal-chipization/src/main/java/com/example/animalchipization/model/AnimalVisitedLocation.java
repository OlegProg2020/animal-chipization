package com.example.animalchipization.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@NoArgsConstructor
@Data
public class AnimalVisitedLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime dateTimeOfVisitLocationPoint = LocalDateTime.now(ZonedDateTime.now().getZone());
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
    @NotNull
    private Animal animal;

    public AnimalVisitedLocation(Animal animal, LocationPoint locationPoint) {
        this.animal = animal;
        this.locationPoint = locationPoint;
    }
}
