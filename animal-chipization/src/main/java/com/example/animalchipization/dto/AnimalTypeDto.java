package com.example.animalchipization.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonDeserialize(builder = AnimalTypeDto.Builder.class)
@Getter
@NoArgsConstructor
public class AnimalTypeDto {

    @Setter
    private Long id;
    @NotBlank
    private String type;

    private AnimalTypeDto(Builder builder) {
        this.id = builder.id;
        this.type = builder.type;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String type;

        @JsonIgnore
        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public AnimalTypeDto build() {
            return new AnimalTypeDto(this);
        }
    }
}
