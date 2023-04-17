package com.example.animalchipization.entity;

import com.example.animalchipization.entity.enums.StatusOfAnimalVisitsToArea;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"area", "animalVisitedLocation", "status"})})
public class AreaAnalytics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Area area;
    private AnimalVisitedLocation animalVisitedLocation;
    private StatusOfAnimalVisitsToArea status;

}
