package com.example.animalchipization.web.dto;

import com.example.animalchipization.entity.AnimalVisitedLocation;
import com.example.animalchipization.entity.LocationPoint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@JsonDeserialize(builder = AnimalVisitedLocationDto.Builder.class)
@Getter
@NoArgsConstructor
public class AnimalVisitedLocationDto {

    private Long id;
    private ZonedDateTime dateTimeOfVisitLocationPoint;
    @Min(1)
    private Long locationPointId;
    @JsonIgnore
    @Min(1)
    private Long animalId;

    private AnimalVisitedLocationDto(Builder builder) {
        this.locationPointId = builder.locationPointId;
        this.animalId = builder.animalId;
    }

    public Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long locationPointId;
        private Long animalId;

        public Builder withLocationPointId(Long locationPointId) {
            this.locationPointId = locationPointId;
            return this;
        }

        public Builder withAnimalId(Long animalId) {
            this.animalId = animalId;
            return this;
        }

        public AnimalVisitedLocationDto build() {
            return new AnimalVisitedLocationDto(this);
        }
    }

    public void setId(@Min(1) Long id) {
        this.id = id;
    }

}
