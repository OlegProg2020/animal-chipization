package com.example.animalchipization.web.dto;

import com.example.animalchipization.entity.enums.Gender;
import com.example.animalchipization.entity.enums.LifeStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.OrderBy;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonDeserialize(builder = AnimalDto.Builder.class)
@Getter
@NoArgsConstructor
public class AnimalDto {

    private Long id;
    @NotEmpty
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
    @Min(1)
    private Long chipperId;
    @Min(1)
    private Long chippingLocationId;
    private List<Long> visitedLocations = new ArrayList<>();
    private ZonedDateTime deathDateTime;

    private AnimalDto(Builder builder) {
        this.animalTypes = builder.animalTypes;
        this.weight = builder.weight;
        this.length = builder.length;
        this.height = builder.height;
        this.gender = builder.gender;
        this.lifeStatus = builder.lifeStatus;
        this.chipperId = builder.chipperId;
        this.chippingLocationId = builder.chippingLocationId;
    }

    public Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Set<Long> animalTypes;
        private Float weight;
        private Float length;
        private Float height;
        private Gender gender;
        private LifeStatus lifeStatus;
        private Long chipperId;
        private Long chippingLocationId;

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

        public Builder withLifeStatus(@NotNull LifeStatus lifeStatus) {
            this.lifeStatus = lifeStatus;
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

        public AnimalDto build() {
            return new AnimalDto(this);
        }
    }

    public void setId(@Min(1) Long id) {
        this.id = id;
    }

}
