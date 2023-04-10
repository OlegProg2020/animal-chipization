package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AnimalRepository;
import com.example.animalchipization.entity.Animal;
import com.example.animalchipization.entity.AnimalType;
import com.example.animalchipization.entity.enums.Gender;
import com.example.animalchipization.entity.enums.LifeStatus;
import com.example.animalchipization.exception.AttemptToRemoveAnimalNotAtTheChippingPointException;
import com.example.animalchipization.exception.RemovingSingleTypeOfAnimalException;
import com.example.animalchipization.mapper.Mapper;
import com.example.animalchipization.service.AnimalService;
import com.example.animalchipization.util.OffsetBasedPageRequest;
import com.example.animalchipization.web.dto.AnimalDto;
import com.example.animalchipization.web.dto.AnimalTypeDto;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.example.animalchipization.data.specification.AnimalSpecification.*;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final Mapper<Animal, AnimalDto> animalMapper;
    private final Mapper<AnimalType, AnimalTypeDto> animalTypeMapper;

    @Autowired
    public AnimalServiceImpl(AnimalRepository animalRepository,
                             Mapper<Animal, AnimalDto> animalMapper,
                             Mapper<AnimalType, AnimalTypeDto> animalTypeMapper) {
        this.animalRepository = animalRepository;
        this.animalMapper = animalMapper;
        this.animalTypeMapper = animalTypeMapper;
    }

    @Override
    public AnimalDto findById(@Min(1) Long animalId) {
        return animalMapper.toDto(animalRepository.findById(animalId)
                .orElseThrow(NoSuchElementException::new));
    }

    @Override
    public Collection<AnimalDto> searchForAnimals(ZonedDateTime startDateTime, ZonedDateTime endDateTime,
                                                  Long chipperId, Long chippingLocationId, LifeStatus lifeStatus,
                                                  Gender gender, Integer from, Integer size) {

        OffsetBasedPageRequest pageRequest = new OffsetBasedPageRequest(
                from, size, Sort.by("id").ascending());
        Specification<Animal> specifications = Specification.where(
                hasChippingDateTimeGreaterThanOrEqualTo(startDateTime)
                        .and(hasChippingDateTimeLessThanOrEqualTo(endDateTime))
                        .and(hasChipperId(chipperId))
                        .and(hasChippingLocationId(chippingLocationId))
                        .and(hasLifeStatus(lifeStatus))
                        .and(hasGender(gender))
        );
        return animalRepository.findAll(specifications, pageRequest).getContent().stream()
                .map(animalMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AnimalDto addAnimal(@Valid AnimalDto animalDto) {
        Animal animal = animalMapper.toEntity(animalDto);
        if (animal.getAnimalTypes().isEmpty()) {
            throw new ValidationException();
        }
        animal.setLifeStatus(LifeStatus.ALIVE);
        animal.setChippingDateTime(ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return animalMapper.toDto(animalRepository.save(animal));
    }

    @Override
    @Transactional
    public AnimalDto updateAnimal(@Min(1) Long animalId, @Valid AnimalDto updatedAnimalDto) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(NoSuchElementException::new);
        Animal updatedAnimal = animalMapper.toEntity(updatedAnimalDto);

        animal.setWeight(updatedAnimal.getWeight());
        animal.setLength(updatedAnimal.getLength());
        animal.setHeight(updatedAnimal.getHeight());
        animal.setGender(updatedAnimal.getGender());
        animal.setAndValidateLifeStatus(updatedAnimal.getLifeStatus());
        animal.setChipper(updatedAnimal.getChipper());
        animal.setAndValidateChippingLocation(updatedAnimal.getChippingLocation());

        return animalMapper.toDto(animalRepository.save(animal));
    }

    @Override
    @Transactional
    public void deleteAnimalById(@Min(1) Long animalId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(NoSuchElementException::new);
        if (animal.checkAnimalAtChippingLocation()) {
            animalRepository.delete(animal);
        } else {
            throw new AttemptToRemoveAnimalNotAtTheChippingPointException();
        }
    }

    @Override
    @Transactional
    public AnimalDto addTypeToAnimal(@Min(1) Long animalId, @Valid AnimalTypeDto animalTypeDto) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(NoSuchElementException::new);
        AnimalType animalType = animalTypeMapper.toEntity(animalTypeDto);

        animal.addAnimalType(animalType);

        return animalMapper.toDto(animalRepository.save(animal));
    }

    @Override
    @Transactional
    public AnimalDto updateTypeOfAnimal(@Min(1) Long animalId, @Valid AnimalTypeDto oldTypeDto,
                                        @Valid AnimalTypeDto newTypeDto) {

        Animal animal = animalRepository.findById(animalId).orElseThrow(NoSuchElementException::new);
        AnimalType oldType = animalTypeMapper.toEntity(oldTypeDto);
        AnimalType newType = animalTypeMapper.toEntity(newTypeDto);

        animal.removeAnimalType(oldType);
        animal.addAnimalType(newType);

        return animalMapper.toDto(animalRepository.save(animal));
    }

    @Override
    public AnimalDto deleteTypeOfAnimal(@Min(1) Long animalId, @Valid AnimalTypeDto animalTypeDto) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(NoSuchElementException::new);
        AnimalType type = animalTypeMapper.toEntity(animalTypeDto);

        animal.removeAnimalType(type);
        if (animal.getAnimalTypes().size() == 0) {
            throw new RemovingSingleTypeOfAnimalException();
        }

        return animalMapper.toDto(animalRepository.save(animal));
    }

}
