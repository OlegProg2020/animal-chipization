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
    @DecimalMin(value = "-90", inclusive = false)
    @DecimalMax(value = "90", inclusive = false)
    private Double latitude;
    @NotNull
    @DecimalMin(value = "-180", inclusive = false)
    @DecimalMin(value = "-180", inclusive = false)
    private Double longitude;

    public LocationPoint toLocationPoint() {
        return new LocationPoint(this.latitude, this.longitude);
    }
}
