package com.example.animalchipization.model;

import com.example.animalchipization.model.enums.Gender;
import com.example.animalchipization.model.enums.LifeStatus;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @ManyToOne(targetEntity = LocationPoint.class)
    @NotNull
    @JsonProperty("chippingLocationId")
    private LocationPoint chippingLocation;
    @OneToMany(targetEntity = AnimalVisitedLocation.class)
    @JoinColumn(name = "animal_id")
    private List<Long> visitedLocations = new ArrayList<>();
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
