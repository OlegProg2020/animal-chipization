package com.example.animalchipization.web.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AnimalVisitedLocationForm {
    @NotNull
    @Min(1)
    private Long animalId;
    @NotNull
    @Min(1)
    private Long pointId;
}
