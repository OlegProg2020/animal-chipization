package com.example.animalchipization.util.converter;

import com.example.animalchipization.data.repository.AccountRepository;
import com.example.animalchipization.data.repository.AnimalTypeRepository;
import com.example.animalchipization.data.repository.LocationPointRepository;
import com.example.animalchipization.entity.Account;
import com.example.animalchipization.entity.Animal;
import com.example.animalchipization.entity.AnimalType;
import com.example.animalchipization.entity.LocationPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Component
public class AnimalFormToAnimalConverter implements Converter<AnimalForm, Animal> {

    private final AccountRepository accountRepository;
    private final AnimalTypeRepository animalTypeRepository;
    private final LocationPointRepository locationPointRepository;

    @Autowired
    public AnimalFormToAnimalConverter(AccountRepository accountRepository,
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
        if (animalTypes.size() < animalForm.getAnimalTypes().size()) {
            throw new NoSuchElementException();
        }

        LocationPoint chippingLocation = locationPointRepository.findById(animalForm.getChippingLocationId())
                .orElseThrow(NoSuchElementException::new);


        return new Animal(animalTypes, animalForm.getWeight(), animalForm.getLength(),
                animalForm.getHeight(), animalForm.getGender(), chipper, chippingLocation);
    }

}
