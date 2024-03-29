package com.example.animalchipization.web.model;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Coordinate {

    @DecimalMin(value = "-90")
    @DecimalMax(value = "90")
    private Double latitude;
    @DecimalMin(value = "-180")
    @DecimalMax(value = "180")
    private Double longitude;
}
