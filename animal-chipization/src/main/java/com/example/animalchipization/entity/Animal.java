package com.example.animalchipization.entity;

import com.example.animalchipization.entity.enums.Gender;
import com.example.animalchipization.entity.enums.LifeStatus;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "animal_animal_types",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "animal_type_id"))
    private Set<AnimalType> animalTypes = new HashSet<>();
    private Float weight;
    private Float length;
    private Float height;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    //TODO перенести значение по умолчанию в сервисный слой
    private LifeStatus lifeStatus = LifeStatus.ALIVE;
    //TODO перенести значение по умолчанию в сервисный слой
    private ZonedDateTime chippingDateTime = ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    @ManyToOne(optional = false)
    private Account chipper;
    @ManyToOne(optional = false)
    private LocationPoint chippingLocation;
    @OneToMany(mappedBy = "animal", fetch = FetchType.LAZY)
    @OrderBy("dateTimeOfVisitLocationPoint ASC, id ASC")
    private List<AnimalVisitedLocation> visitedLocations = new ArrayList<>();
    private ZonedDateTime deathDateTime;

    public void setLifeStatusToDeadAndSetDeathDateTime() {
        this.lifeStatus = LifeStatus.DEAD;
        this.deathDateTime = ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    public Boolean addAnimalType(AnimalType animalType) {
        return this.animalTypes.add(animalType);
    }

    public Boolean removeAnimalType(AnimalType animalType) {
        return this.animalTypes.remove(animalType);
    }

}
