package com.example.animalchipization.model;

import com.example.animalchipization.model.enums.Gender;
import com.example.animalchipization.model.enums.LifeStatus;
import com.example.animalchipization.validation.annotations.UniqueElements;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Data
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToMany(fetch = FetchType.LAZY)
    @NotEmpty
    private Set<AnimalType> animalTypes = new HashSet<>();

    @JsonGetter("animalTypes")
    public Iterable<Long> getAnimalTypesIds() {
        return animalTypes.stream().map(AnimalType::getId).collect(Collectors.toList());
    }

    @NotNull
    @Min(1)
    private Float weight;
    @NotNull
    @Min(1)
    private Float length;
    @NotNull
    @Min(1)
    private Float height;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private LifeStatus lifeStatus = LifeStatus.ALIVE;
    private ZonedDateTime chippingDateTime = ZonedDateTime.now();
    @ManyToOne(optional = false)
    @NotNull
    @JsonProperty("chipperId")
    private Account chipper;

    @JsonGetter("chipperId")
    public Long getChipperId() {
        return chipper.getId();
    }

    @ManyToOne
    @NotNull
    @JsonProperty("chippingLocationId")
    private LocationPoint chippingLocation;

    @JsonGetter("chippingLocationId")
    public Long getChippingLocationId() {
        return chippingLocation.getId();
    }

    @OneToMany
    @JoinColumn(name = "animal_id")
    private List<AnimalVisitedLocation> visitedLocations = new ArrayList<>();
    @JsonGetter("visitedLocations")
    public Iterable<Long> getVisitedLocationsIds() {
        return visitedLocations.stream().map(AnimalVisitedLocation::getId).collect(Collectors.toList());
    }

    private ZonedDateTime deathDateTime = null;

    public Animal(Set<AnimalType> animalTypes, Float weight, Float length, Float height,
                  Gender gender, Account chipper, LocationPoint chippingLocation) {
        this.animalTypes = animalTypes;
        this.weight = weight;
        this.length = length;
        this.height = height;
        this.gender = gender;
        this.chipper = chipper;
        this.chippingLocation = chippingLocation;
    }

    public void setLifeStatusToDeadAndSetDeathDateTime() {
        this.lifeStatus = LifeStatus.DEAD;
        this.deathDateTime = ZonedDateTime.now();
    }

}
