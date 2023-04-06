package com.example.animalchipization.util.converter;

import com.example.animalchipization.data.repository.AccountRepository;
import com.example.animalchipization.data.repository.LocationPointRepository;
import com.example.animalchipization.entity.Account;
import com.example.animalchipization.entity.Animal;
import com.example.animalchipization.entity.LocationPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class AnimalPutFormToAnimalConverter implements Converter<AnimalPutForm, Animal> {

    private final AccountRepository accountRepository;
    private final LocationPointRepository locationPointRepository;

    @Autowired
    public AnimalPutFormToAnimalConverter(AccountRepository accountRepository,
                                          LocationPointRepository locationPointRepository) {
        this.accountRepository = accountRepository;
        this.locationPointRepository = locationPointRepository;
    }

    @Override
    public Animal convert(AnimalPutForm animalForm) {
        Account chipper = accountRepository.findById(animalForm.getChipperId())
                .orElseThrow(NoSuchElementException::new);

        LocationPoint chippingLocation = locationPointRepository.findById(animalForm.getChippingLocationId())
                .orElseThrow(NoSuchElementException::new);

        Animal animal = new Animal(animalForm.getWeight(), animalForm.getLength(),
                animalForm.getHeight(), animalForm.getGender(), chipper, chippingLocation);
        animal.setLifeStatus(animalForm.getLifeStatus());
        return animal;
    }
}
