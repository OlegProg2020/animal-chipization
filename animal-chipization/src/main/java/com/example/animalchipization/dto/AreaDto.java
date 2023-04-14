package com.example.animalchipization.dto;

import com.example.animalchipization.web.model.Coordinate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@JsonDeserialize(builder = AreaDto.Builder.class)
@Getter
@NoArgsConstructor
public class AreaDto {

    @Setter
    private Long id;
    @NotEmpty
    private String name;
    @Min(3)
    private List<@Valid Coordinate> areaPoints = new LinkedList<>();

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
        private List<Coordinate> areaPoints = new LinkedList<>();

        @JsonIgnore
        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withAreaPoints(List<Coordinate> areaPoints) {
            this.areaPoints = areaPoints;
            return this;
        }

        public AreaDto build() {
            return new AreaDto(this);
        }
    }

}
