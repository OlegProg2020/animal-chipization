package com.example.animalchipization.model;

import com.example.animalchipization.model.enums.Gender;
import com.example.animalchipization.model.enums.LifeStatus;
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
    @ManyToMany(targetEntity = AnimalType.class)
    private Set<Long> animalTypes = new HashSet<>();
    private Float weight;
    private Float length;
    private Float height;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Setter(value = AccessLevel.NONE)
    @Enumerated(EnumType.STRING)
    private LifeStatus lifeStatus = LifeStatus.ALIVE;
    private LocalDateTime chippingDateTime = LocalDateTime.now();
    @ManyToOne(optional = false, targetEntity = Account.class)
    private Long chipperId;
    @ManyToOne(targetEntity = LocationPoint.class)
    private Long chippingLocationId;
    @OneToMany(targetEntity = AnimalVisitedLocation.class)
    @JoinColumn(name = "animal_id")
    private List<Long> visitedLocations = new ArrayList<>();
    @Setter(value = AccessLevel.NONE)
    private LocalDateTime deathDateTime = null;

    public void setLifeStatusToDeadAndSetDeathDateTime() {
        this.lifeStatus = LifeStatus.DEAD;
        this.deathDateTime = LocalDateTime.now();
    }
}
