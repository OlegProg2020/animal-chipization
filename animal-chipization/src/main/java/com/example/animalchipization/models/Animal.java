package com.example.animalchipization.models;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToMany
    private List<AnimalType> animalTypes = new ArrayList<>();
    private Float weight;
    private Float length;
    private Float height;
    private String gender;
    private String lifeStatus = "ALIVE";
    private LocalDateTime chippingDateTime = LocalDateTime.now();
    @ManyToOne
    private Account chipper;
    @ManyToOne
    private LocationPoint chippingLocation;
    @OneToMany
    private List<AnimalVisitedLocation> visitedLocations = new ArrayList<>();
    @Setter(value = AccessLevel.NONE)
    private LocalDateTime deathDateTime = null;

    public void setDeathDateTime(LocalDateTime deathDateTime) {
        this.deathDateTime = deathDateTime;
        this.setLifeStatus("DEAD");
    }
}
