package com.example.animalchipization.model;


import com.example.animalchipization.validation.annotations.UniqueLocationPoint;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@UniqueLocationPoint
@Table(uniqueConstraints = {@UniqueConstraint(columnNames={"latitude", "longitude"})})
public class LocationPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @DecimalMin(value = "-90", inclusive = false)
    @DecimalMax(value = "90", inclusive = false)
    private Double latitude;
    @NotNull
    @DecimalMin(value = "-180", inclusive = false)
    @DecimalMin(value = "-180", inclusive = false)
    private Double longitude;

    public LocationPoint(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
