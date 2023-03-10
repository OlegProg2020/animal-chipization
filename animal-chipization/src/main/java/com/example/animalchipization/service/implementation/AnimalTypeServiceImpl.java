package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AnimalTypeRepository;
import com.example.animalchipization.model.AnimalType;
import com.example.animalchipization.service.AnimalTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AnimalTypeServiceImpl implements AnimalTypeService {

    private final AnimalTypeRepository animalTypeRepository;

    @Autowired
    public AnimalTypeServiceImpl(AnimalTypeRepository animalTypeRepository) {
        this.animalTypeRepository = animalTypeRepository;
    }

    @Override
    public AnimalType findAnimalTypeById(Long typeId) {
        Optional<AnimalType> optionalAnimalType = animalTypeRepository.findById(typeId);
        if (optionalAnimalType.isPresent()) {
            return optionalAnimalType.get();
        } else {
            throw new NoSuchElementException();
        }
    }

}
