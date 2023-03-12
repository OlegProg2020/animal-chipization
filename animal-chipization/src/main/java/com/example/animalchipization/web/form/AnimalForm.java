package com.example.animalchipization.web.form;

import com.example.animalchipization.model.Animal;
import com.example.animalchipization.model.enums.Gender;
import com.example.animalchipization.model.enums.LifeStatus;
import com.example.animalchipization.validation.annotations.UniqueElements;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AnimalForm {
    @NotEmpty
    @UniqueElements
    private List<@NotNull @Min(1) Long> animalTypes = new ArrayList<>();
    @NotNull
    @Min(1)
    private Float weight;
    @NotNull
    @Min(1)
    private Float length;
    @NotNull
    @Min(1)
    private Float height;
    @NotNull
    private Gender gender;
    private LifeStatus lifeStatus;
    @NotNull
    @Min(1)
    private Long chipperId;
    @NotNull
    @Min(1)
    private Long chippingLocationId;

    public Animal toAnimal() {
        Animal animal = new Animal(new HashSet<>(animalTypes), weight, length, height,
                gender, chipperId, chippingLocationId);
        if (this.lifeStatus == LifeStatus.DEAD) {
            animal.setLifeStatusToDeadAndSetDeathDateTime();
        }
        return animal;
    }

}
