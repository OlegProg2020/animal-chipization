package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AnimalTypeRepository;
import com.example.animalchipization.exception.AnimalTypeWithThisTypeAlreadyExistsException;
import com.example.animalchipization.model.AnimalType;
import com.example.animalchipization.service.AnimalTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AnimalTypeServiceImpl implements AnimalTypeService {

    private final AnimalTypeRepository animalTypeRepository;

    @Autowired
    public AnimalTypeServiceImpl(AnimalTypeRepository animalTypeRepository) {
        this.animalTypeRepository = animalTypeRepository;
    }

    @Override
    public AnimalType findAnimalTypeById(Long typeId) {
        return animalTypeRepository.findById(typeId).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public AnimalType addAnimalType(@Valid AnimalType animalType) {
        if (!animalTypeRepository.existsByType(animalType.getType())) {
            return animalTypeRepository.save(animalType);
        } else {
            throw new AnimalTypeWithThisTypeAlreadyExistsException();
        }
    }

    @Override
    public AnimalType updateAnimalType(@Valid AnimalType animalType) {
        if (!animalTypeRepository.existsByType(animalType.getType())) {
            if (animalTypeRepository.existsById(animalType.getId())) {
                return animalTypeRepository.save(animalType);
            } else {
                throw new NoSuchElementException();
            }
        } else {
            throw new AnimalTypeWithThisTypeAlreadyExistsException();
        }
    }

    @Override
    public void deleteAnimalTypeById(Long typeId) {
        try {
            animalTypeRepository.deleteById(typeId);
        } catch (EmptyResultDataAccessException ignoredException) {
            throw new NoSuchElementException();
        }
    }

}
