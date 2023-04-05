package com.example.animalchipization.model;


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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"latitude", "longitude"})})
public class LocationPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double latitude;
    private Double longitude;

}
