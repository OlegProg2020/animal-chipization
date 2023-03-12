package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AnimalTypeRepository;
import com.example.animalchipization.exception.AnimalTypeWithThisTypeAlreadyExistsException;
import com.example.animalchipization.model.AnimalType;
import com.example.animalchipization.service.AnimalTypeService;
import com.example.animalchipization.web.form.AnimalTypeForm;
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

    @Override
    public AnimalType addAnimalType(AnimalTypeForm animalTypeForm) {
        if (!animalTypeRepository.existsByType(animalTypeForm.getType())) {
            return animalTypeRepository.save(animalTypeForm.toAnimalType());
        } else {
            throw new AnimalTypeWithThisTypeAlreadyExistsException();
        }
    }

    @Override
    public AnimalType updateAnimalTypeById(Long animalTypeId, AnimalTypeForm animalTypeForm) {
        if (!animalTypeRepository.existsByType(animalTypeForm.getType())) {
            AnimalType newAnimalType = animalTypeForm.toAnimalType();
            newAnimalType.setId(animalTypeId);
            return animalTypeRepository.save(newAnimalType);
        } else {
            throw new AnimalTypeWithThisTypeAlreadyExistsException();
        }
    }

    @Override
    public void deleteAnimalTypeById(Long typeId) {
        animalTypeRepository.deleteById(typeId);
    }

}
