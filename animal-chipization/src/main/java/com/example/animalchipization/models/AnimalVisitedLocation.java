package com.example.animalchipization.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class AnimalVisitedLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime dateTimeOfVisitLocationPoint;
    @OneToOne
    private LocationPoint locationPoint;
    @ManyToOne
    private Animal animal;
}
