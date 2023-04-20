package com.example.animalchipization.dto;

import com.example.animalchipization.entity.enums.Gender;
import com.example.animalchipization.entity.enums.LifeStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@JsonDeserialize(builder = AnimalDto.Builder.class)
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class AnimalDto {

    @Setter
    private Long id;
    private Set<@Min(1) Long> animalTypes = new HashSet<>();
    @DecimalMin(value = "0", inclusive = false)
    private Float weight;
    @DecimalMin(value = "0", inclusive = false)
    private Float length;
    @DecimalMin(value = "0", inclusive = false)
    private Float height;
    @NotNull
    private Gender gender;
    private LifeStatus lifeStatus;
    private ZonedDateTime chippingDateTime;
    @Min(1)
    private Long chipperId;
    @Min(1)
    private Long chippingLocationId;
    private List<@Min(1) Long> visitedLocations = new LinkedList<>();
    private ZonedDateTime deathDateTime;

    private AnimalDto(Builder builder) {
        this.id = builder.id;
        this.animalTypes = builder.animalTypes;
        this.weight = builder.weight;
        this.length = builder.length;
        this.height = builder.height;
        this.gender = builder.gender;
        this.lifeStatus = builder.lifeStatus;
        this.chippingDateTime = builder.chippingDateTime;
        this.chipperId = builder.chipperId;
        this.chippingLocationId = builder.chippingLocationId;
        this.visitedLocations = builder.visitedLocations;
        this.deathDateTime = builder.deathDateTime;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Set<Long> animalTypes = new HashSet<>();
        private Float weight;
        private Float length;
        private Float height;
        private Gender gender;
        private LifeStatus lifeStatus;
        private ZonedDateTime chippingDateTime;
        private Long chipperId;
        private Long chippingLocationId;
        private List<Long> visitedLocations = new LinkedList<>();
        private ZonedDateTime deathDateTime;

        @JsonIgnore
        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withAnimalTypes(Set<Long> animalTypes) {
            this.animalTypes = animalTypes;
            return this;
        }

        public Builder withWeight(Float weight) {
            this.weight = weight;
            return this;
        }

        public Builder withLength(Float length) {
            this.length = length;
            return this;
        }

        public Builder withHeight(Float height) {
            this.height = height;
            return this;
        }

        public Builder withGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder withLifeStatus(LifeStatus lifeStatus) {
            this.lifeStatus = lifeStatus;
            return this;
        }

        @JsonIgnore
        public Builder withChippingDateTime(ZonedDateTime chippingDateTime) {
            this.chippingDateTime = chippingDateTime;
            return this;
        }

        public Builder withChipperId(Long chipperId) {
            this.chipperId = chipperId;
            return this;
        }

        public Builder withChippingLocationId(Long chippingLocationId) {
            this.chippingLocationId = chippingLocationId;
            return this;
        }

        @JsonIgnore
        public Builder withVisitedLocations(List<Long> visitedLocations) {
            this.visitedLocations = visitedLocations;
            return this;
        }

        @JsonIgnore
        public Builder withDeathDateTime(ZonedDateTime deathDateTime) {
            this.deathDateTime = deathDateTime;
            return this;
        }

        public AnimalDto build() {
            return new AnimalDto(this);
        }
    }

}
