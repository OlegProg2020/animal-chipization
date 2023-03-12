package com.example.animalchipization.web.form;

import com.example.animalchipization.model.AnimalType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnimalTypeForm {
    @NotBlank
    String type;

    public AnimalType toAnimalType() {
        AnimalType animalType = new AnimalType();
        animalType.setType(type);
        return animalType;
    }
}
