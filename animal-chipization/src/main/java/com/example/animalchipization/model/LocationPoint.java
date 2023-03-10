package com.example.animalchipization.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class LocationPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double latitude;
    private Double longitude;
}
