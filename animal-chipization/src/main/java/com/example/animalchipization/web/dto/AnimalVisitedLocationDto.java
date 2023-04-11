package com.example.animalchipization.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@JsonDeserialize(builder = AnimalVisitedLocationDto.Builder.class)
@Getter
@NoArgsConstructor
public class AnimalVisitedLocationDto {

    @Setter
    private Long id;
    private ZonedDateTime dateTimeOfVisitLocationPoint;
    @Min(1)
    private Long locationPointId;
    @JsonIgnore
    @Min(1)
    private Long animalId;

    private AnimalVisitedLocationDto(Builder builder) {
        this.id = builder.id;
        this.dateTimeOfVisitLocationPoint = builder.dateTimeOfVisitLocationPoint;
        this.locationPointId = builder.locationPointId;
        this.animalId = builder.animalId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private ZonedDateTime dateTimeOfVisitLocationPoint;
        private Long locationPointId;
        private Long animalId;

        @JsonIgnore
        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        @JsonIgnore
        public Builder withDateTimeOfVisitLocationPoint(ZonedDateTime dateTimeOfVisitLocationPoint) {
            this.dateTimeOfVisitLocationPoint = dateTimeOfVisitLocationPoint;
            return this;
        }

        @JsonIgnore
        public Builder withLocationPointId(Long locationPointId) {
            this.locationPointId = locationPointId;
            return this;
        }

        @JsonIgnore
        public Builder withAnimalId(Long animalId) {
            this.animalId = animalId;
            return this;
        }

        public AnimalVisitedLocationDto build() {
            return new AnimalVisitedLocationDto(this);
        }
    }

}
