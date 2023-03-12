package com.example.animalchipization.model;

import com.example.animalchipization.validation.annotations.UniqueAnimalType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class AnimalType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    @UniqueAnimalType
    private String type;
}
