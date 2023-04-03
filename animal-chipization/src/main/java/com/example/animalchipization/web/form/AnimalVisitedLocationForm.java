package com.example.animalchipization.web.form;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AnimalVisitedLocationForm {
    private Long animalId;
    private Long pointId;
}
