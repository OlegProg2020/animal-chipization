package com.example.animalchipization.util.converter;

import com.example.animalchipization.data.repository.AccountRepository;
import com.example.animalchipization.data.repository.AnimalTypeRepository;
import com.example.animalchipization.data.repository.LocationPointRepository;
import com.example.animalchipization.exception.SettingLifeStatusInAliveFromDeadException;
import com.example.animalchipization.model.Account;
import com.example.animalchipization.model.Animal;
import com.example.animalchipization.model.AnimalType;
import com.example.animalchipization.model.LocationPoint;
import com.example.animalchipization.model.enums.LifeStatus;
import com.example.animalchipization.web.form.AnimalForm;
import com.example.animalchipization.web.form.AnimalPutForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
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