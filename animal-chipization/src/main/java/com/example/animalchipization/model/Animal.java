package com.example.animalchipization.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @ManyToMany
    private Set<AnimalType> animalTypes = new HashSet<>();
    private Float weight;
    private Float length;
    private Float height;
    private Gender gender;
    @Setter(value = AccessLevel.NONE)
    private LifeStatus lifeStatus = LifeStatus.ALIVE;
    private LocalDateTime chippingDateTime = LocalDateTime.now();
    @ManyToOne(optional = false)
    private Account chipper;
    @ManyToOne
    private LocationPoint chippingLocation;
    @OneToMany
    @JoinColumn(name = "animal_id")
    private List<AnimalVisitedLocation> visitedLocations = new ArrayList<>();
    @Setter(value = AccessLevel.NONE)
    private LocalDateTime deathDateTime = null;

    public void setLifeStatus(LifeStatus lifeStatus) {
        this.lifeStatus = LifeStatus.DEAD;
        this.deathDateTime = LocalDateTime.now();
    }

    public enum Gender {
        MALE, FEMALE, OTHER
    }
    public enum LifeStatus {
        ALIVE, DEAD;
    }
}
