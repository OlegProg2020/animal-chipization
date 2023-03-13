package com.example.animalchipization.web.form;

import com.example.animalchipization.model.LocationPoint;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocationPointForm {
    @NotNull
    @DecimalMin(value = "-90")
    @DecimalMax(value = "90")
    private Double latitude;
    @NotNull
    @DecimalMin(value = "-180")
    @DecimalMax(value = "180")
    private Double longitude;

    public LocationPoint toLocationPoint() {
        return new LocationPoint(this.latitude, this.longitude);
    }
}
