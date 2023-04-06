package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AnimalTypeRepository;
import com.example.animalchipization.exception.AnimalTypeWithSuchTypeAlreadyExistsException;
import com.example.animalchipization.mapper.Mapper;
import com.example.animalchipization.entity.AnimalType;
import com.example.animalchipization.service.AnimalTypeService;
import com.example.animalchipization.web.dto.AnimalTypeDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class AnimalTypeServiceImpl implements AnimalTypeService {

    private final AnimalTypeRepository animalTypeRepository;
    private final Mapper<AnimalType, AnimalTypeDto> mapper;

    @Autowired
    public AnimalTypeServiceImpl(AnimalTypeRepository animalTypeRepository,
                                 Mapper<AnimalType, AnimalTypeDto> mapper) {
        this.animalTypeRepository = animalTypeRepository;
        this.mapper = mapper;
    }

    @Override
    public AnimalTypeDto findAnimalTypeById(@Min(1) Long typeId) {
        return mapper.toDto(animalTypeRepository.findById(typeId).orElseThrow(NoSuchElementException::new));
    }

    @Override
    @Transactional
    public AnimalTypeDto addAnimalType(@Valid AnimalTypeDto animalTypeDto) {
        try {
            return mapper.toDto(animalTypeRepository.save(mapper.toEntity(animalTypeDto)));
        } catch (DataIntegrityViolationException exception) {
            throw new AnimalTypeWithSuchTypeAlreadyExistsException();
        }
    }

    @Override
    @Transactional
    public AnimalTypeDto updateAnimalType(@Valid AnimalTypeDto animalTypeDto) {
        AnimalType updatingAnimalType = mapper.toEntity(animalTypeDto);
        try {
            if (animalTypeRepository.existsById(updatingAnimalType.getId())) {
                return mapper.toDto(animalTypeRepository.save(updatingAnimalType));
            } else {
                throw new NoSuchElementException();
            }
        } catch (DataIntegrityViolationException exception) {
            throw new AnimalTypeWithSuchTypeAlreadyExistsException();
        }
    }

    @Override
    @Transactional
    public void deleteAnimalTypeById(@Min(1) Long typeId) {
        try {
            animalTypeRepository.deleteById(typeId);
        } catch (EmptyResultDataAccessException exception) {
            throw new NoSuchElementException();
        }
    }

}
