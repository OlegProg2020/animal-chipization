package com.example.animalchipization.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonDeserialize(builder = LocationPointDto.Builder.class)
@Getter
@NoArgsConstructor
public class LocationPointDto {

    private Long id;
    @DecimalMin(value = "-90")
    @DecimalMax(value = "90")
    private Double latitude;
    @DecimalMin(value = "-180")
    @DecimalMax(value = "180")
    private Double longitude;

    private LocationPointDto(Builder builder) {
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Double latitude;
        private Double longitude;

        public Builder withLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder withLongitude(Double longitude) {
            this.latitude = longitude;
            return this;
        }

        public LocationPointDto build() {
            return new LocationPointDto(this);
        }
    }

    public void setId(@Min(1) Long id) {
        this.id = id;
    }

}
