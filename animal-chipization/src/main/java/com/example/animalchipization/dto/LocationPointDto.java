package com.example.animalchipization.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonDeserialize(builder = LocationPointDto.Builder.class)
@Getter
@NoArgsConstructor
public class LocationPointDto {

    @Setter
    private Long id;
    @DecimalMin(value = "-90")
    @DecimalMax(value = "90")
    private Double latitude;
    @DecimalMin(value = "-180")
    @DecimalMax(value = "180")
    private Double longitude;

    private LocationPointDto(Builder builder) {
        this.id = builder.id;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Double latitude;
        private Double longitude;

        @JsonIgnore
        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder withLongitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public LocationPointDto build() {
            return new LocationPointDto(this);
        }
    }

}
