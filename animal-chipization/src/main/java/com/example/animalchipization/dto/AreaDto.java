package com.example.animalchipization.dto;

import com.example.animalchipization.web.model.Coordinate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@JsonDeserialize(builder = AreaDto.Builder.class)
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class AreaDto {

    @Setter
    private Long id;
    @NotEmpty
    private String name;
    @Size(min = 3)
    private Set<@Valid Coordinate> areaPoints = new LinkedHashSet<>();

    private AreaDto(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.areaPoints = builder.areaPoints;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private LinkedHashSet<Coordinate> areaPoints = new LinkedHashSet<>();

        @JsonIgnore
        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withAreaPoints(LinkedHashSet<Coordinate> areaPoints) {
            this.areaPoints = areaPoints;
            return this;
        }

        public AreaDto build() {
            return new AreaDto(this);
        }
    }

}
