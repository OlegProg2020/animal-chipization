package com.example.animalchipization.web.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonDeserialize(builder = AnimalTypeDto.Builder.class)
@Getter
@NoArgsConstructor
public class AnimalTypeDto {

    private Long id;
    @NotBlank
    private String type;

    private AnimalTypeDto(Builder builder) {
        this.type = builder.type;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String type;

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public AnimalTypeDto build() {
            return new AnimalTypeDto(this);
        }
    }

    public void setId(@Min(1) Long id) {
        this.id = id;
    }
}
