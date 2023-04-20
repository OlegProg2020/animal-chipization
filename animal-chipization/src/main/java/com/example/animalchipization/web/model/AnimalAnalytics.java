package com.example.animalchipization.web.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AnimalAnalytics {

    String animalType;
    Long animalTypeId;
    Long quantityAnimals;
    Long animalsArrived;
    Long animalsGone;

}
