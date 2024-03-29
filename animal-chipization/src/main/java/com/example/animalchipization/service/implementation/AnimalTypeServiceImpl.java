package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AnimalTypeRepository;
import com.example.animalchipization.dto.AnimalTypeDto;
import com.example.animalchipization.entity.AnimalType;
import com.example.animalchipization.exception.AnimalTypeWithSuchTypeAlreadyExistsException;
import com.example.animalchipization.service.AnimalTypeService;
import com.example.animalchipization.service.mapper.Mapper;
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
    private final Mapper<AnimalType, AnimalTypeDto> animalTypeMapper;

    @Autowired
    public AnimalTypeServiceImpl(AnimalTypeRepository animalTypeRepository,
                                 Mapper<AnimalType, AnimalTypeDto> animalTypeMapper) {
        this.animalTypeRepository = animalTypeRepository;
        this.animalTypeMapper = animalTypeMapper;
    }

    @Override
    public AnimalTypeDto findById(@Min(1) Long id) {
        return animalTypeMapper.toDto(animalTypeRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    @Override
    @Transactional
    public AnimalTypeDto save(@Valid AnimalTypeDto animalTypeDto) {
        try {
            return animalTypeMapper.toDto(animalTypeRepository.save(animalTypeMapper.toEntity(animalTypeDto)));
        } catch (DataIntegrityViolationException exception) {
            throw new AnimalTypeWithSuchTypeAlreadyExistsException();
        }
    }

    @Override
    @Transactional
    public AnimalTypeDto update(@Valid AnimalTypeDto animalTypeDto) {
        AnimalType updatingAnimalType = animalTypeMapper.toEntity(animalTypeDto);

        if (!animalTypeRepository.existsById(updatingAnimalType.getId())) {
            throw new NoSuchElementException();
        }

        try {
            return animalTypeMapper.toDto(animalTypeRepository.save(updatingAnimalType));
        } catch (DataIntegrityViolationException exception) {
            throw new AnimalTypeWithSuchTypeAlreadyExistsException();
        }
    }

    @Override
    @Transactional
    public void deleteById(@Min(1) Long id) {
        try {
            animalTypeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NoSuchElementException();
        }
    }

}
