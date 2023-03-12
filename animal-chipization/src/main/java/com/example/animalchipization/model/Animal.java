package com.example.animalchipization.model;

import com.example.animalchipization.model.enums.Gender;
import com.example.animalchipization.model.enums.LifeStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    @ManyToMany(targetEntity = AnimalType.class)
    @NotEmpty
    private Set<Long> animalTypes = new HashSet<>();
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
    private LocalDateTime chippingDateTime = LocalDateTime.now();
    @ManyToOne(optional = false, targetEntity = Account.class)
    @NotNull
    @Min(1)
    private Long chipperId;
    @ManyToOne(targetEntity = LocationPoint.class)
    @NotNull
    @Min(1)
    private Long chippingLocationId;
    @OneToMany(targetEntity = AnimalVisitedLocation.class)
    @JoinColumn(name = "animal_id")
    private List<Long> visitedLocations = new ArrayList<>();
    private LocalDateTime deathDateTime = null;

    public Animal(Set<Long> animalTypes, Float weight, Float length, Float height,
                  Gender gender, Long chipperId, Long chippingLocationId) {
        this.animalTypes = animalTypes;
        this.weight = weight;
        this.length = length;
        this.height = height;
        this.gender = gender;
        this.chipperId = chipperId;
        this.chippingLocationId = chippingLocationId;
    }

    public void setLifeStatusToDeadAndSetDeathDateTime() {
        this.lifeStatus = LifeStatus.DEAD;
        this.deathDateTime = LocalDateTime.now();
    }

}
