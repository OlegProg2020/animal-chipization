package com.example.animalchipization.util;

import com.example.animalchipization.data.repository.AccountRepository;
import com.example.animalchipization.data.repository.AnimalTypeRepository;
import com.example.animalchipization.data.repository.LocationPointRepository;
import com.example.animalchipization.model.Account;
import com.example.animalchipization.model.Animal;
import com.example.animalchipization.model.AnimalType;
import com.example.animalchipization.model.LocationPoint;
import com.example.animalchipization.model.enums.LifeStatus;
import com.example.animalchipization.web.form.AnimalForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AnimalFormToAnimalConverterImpl implements Converter<AnimalForm, Animal> {

    private final AccountRepository accountRepository;
    private final AnimalTypeRepository animalTypeRepository;
    private final LocationPointRepository locationPointRepository;

    @Autowired
    public AnimalFormToAnimalConverterImpl(AccountRepository accountRepository,
                                           AnimalTypeRepository animalTypeRepository,
                                           LocationPointRepository locationPointRepository) {
        this.accountRepository = accountRepository;
        this.animalTypeRepository = animalTypeRepository;
        this.locationPointRepository = locationPointRepository;
    }

    @Override
    public Animal convert(AnimalForm animalForm) {
        Account chipper = accountRepository.findById(animalForm.getChipperId())
                .orElseThrow(NoSuchElementException::new);

        Iterable<AnimalType> animalTypesIterable = animalTypeRepository.findAllById(animalForm.getAnimalTypes());
        Set<AnimalType> animalTypes = new HashSet<>();
        animalTypesIterable.forEach(animalTypes::add);
        if(animalTypes.size() < animalForm.getAnimalTypes().size()) {
            throw new NoSuchElementException();
        }

        LocationPoint chippingLocation = locationPointRepository.findById(animalForm.getChippingLocationId())
                .orElseThrow(NoSuchElementException::new);


        Animal animal = new Animal(animalTypes, animalForm.getWeight(), animalForm.getLength(),
                animalForm.getHeight(), animalForm.getGender(), chipper, chippingLocation);
        if (animalForm.getLifeStatus() == LifeStatus.DEAD) {
            animal.setLifeStatusToDeadAndSetDeathDateTime();
        }
        return animal;
    }

}
