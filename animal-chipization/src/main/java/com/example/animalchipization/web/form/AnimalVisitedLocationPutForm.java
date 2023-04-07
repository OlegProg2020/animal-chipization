package com.example.animalchipization.web.form;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AnimalVisitedLocationPutForm {
    @Min(1)
    private Long visitedLocationPointId;
    @Min(1)
    private Long locationPointId;
}
