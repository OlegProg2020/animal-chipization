package com.example.animalchipization.util;

import com.example.animalchipization.model.Animal;
import com.example.animalchipization.web.form.AnimalForm;
import org.springframework.core.convert.converter.Converter;

public interface AnimalFormToAnimalConverter extends Converter<AnimalForm, Animal> {

}
