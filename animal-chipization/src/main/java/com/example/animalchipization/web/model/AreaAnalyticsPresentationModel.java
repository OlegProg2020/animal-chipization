package com.example.animalchipization.web.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AreaAnalyticsPresentationModel {

    Long totalQuantityAnimals;
    Long totalAnimalsArrived;
    Long totalAnimalsGone;
    Collection<AnimalAnalytics> animalsAnalytics = new ArrayList<>();

}
