package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AnimalRepository;
import com.example.animalchipization.data.repository.AnimalTypeRepository;
import com.example.animalchipization.dto.AnimalDto;
import com.example.animalchipization.entity.Animal;
import com.example.animalchipization.entity.AnimalType;
import com.example.animalchipization.entity.enums.Gender;
import com.example.animalchipization.entity.enums.LifeStatus;
import com.example.animalchipization.exception.EmptyAnimalTypesException;
import com.example.animalchipization.exception.RemovingSingleTypeOfAnimalException;
import com.example.animalchipization.service.AnimalService;
import com.example.animalchipization.service.mapper.Mapper;
import com.example.animalchipization.service.validation.AnimalBusinessRulesValidator;
import com.example.animalchipization.util.pagination.OffsetBasedPageRequest;
import jakarta.validation.Valid;
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
    private final AnimalTypeRepository animalTypeRepository;
    private final Mapper<Animal, AnimalDto> animalMapper;
    private final AnimalBusinessRulesValidator animalBusinessRulesValidator;

    @Autowired
    public AnimalServiceImpl(AnimalRepository animalRepository,
                             AnimalTypeRepository animalTypeRepository,
                             Mapper<Animal, AnimalDto> animalMapper,
                             AnimalBusinessRulesValidator animalBusinessRulesValidator) {

        this.animalRepository = animalRepository;
        this.animalTypeRepository = animalTypeRepository;
        this.animalMapper = animalMapper;
        this.animalBusinessRulesValidator = animalBusinessRulesValidator;
    }

    @Override
    public AnimalDto findById(@Min(1) Long id) {
        return animalMapper.toDto(animalRepository.findById(id)
                .orElseThrow(NoSuchElementException::new));
    }

    @Override
    public Collection<AnimalDto> searchForAnimals(ZonedDateTime startDateTime, ZonedDateTime endDateTime,
                                                  @Min(1) Long chipperId, @Min(1) Long chippingLocationId,
                                                  LifeStatus lifeStatus, Gender gender,
                                                  @Min(0) Integer from, @Min(1) Integer size) {

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
    public AnimalDto save(@Valid AnimalDto animalDto) {
        if (animalDto.getAnimalTypes().isEmpty()) {
            throw new EmptyAnimalTypesException();
        }

        Animal animal = animalMapper.toEntity(animalDto);
        animal.setLifeStatus(LifeStatus.ALIVE);
        animal.setChippingDateTime(ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        return animalMapper.toDto(animalRepository.save(animal));
    }

    @Override
    @Transactional
    public AnimalDto update(@Min(1) Long animalId, @Valid AnimalDto updatedAnimalDto) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(NoSuchElementException::new);
        Animal updatedAnimal = animalMapper.toEntity(updatedAnimalDto);

        animalBusinessRulesValidator.validatePatchAnimal(animal, updatedAnimal);

        animal.setWeight(updatedAnimal.getWeight());
        animal.setLength(updatedAnimal.getLength());
        animal.setHeight(updatedAnimal.getHeight());
        animal.setGender(updatedAnimal.getGender());
        if (animal.getLifeStatus() == LifeStatus.ALIVE
                && updatedAnimal.getLifeStatus() == LifeStatus.DEAD) {
            animal.setLifeStatus(LifeStatus.DEAD);
            animal.setDeathDateTime(ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        }
        animal.setChipper(updatedAnimal.getChipper());
        animal.setChippingLocation(updatedAnimal.getChippingLocation());

        return animalMapper.toDto(animalRepository.save(animal));
    }

    @Override
    @Transactional
    public void deleteById(@Min(1) Long id) {
        Animal animal = animalRepository.findById(id).orElseThrow(NoSuchElementException::new);

        animalBusinessRulesValidator.validateDeletingAnimal(animal);

        animalRepository.delete(animal);
    }

    @Override
    @Transactional
    public AnimalDto addTypeToAnimal(@Min(1) Long animalId, @Min(1) Long typeId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(NoSuchElementException::new);
        AnimalType animalType = animalTypeRepository.findById(typeId).orElseThrow(NoSuchElementException::new);

        animal.addAnimalType(animalType);

        return animalMapper.toDto(animalRepository.save(animal));
    }

    @Override
    @Transactional
    public AnimalDto updateTypeOfAnimal(@Min(1) Long animalId, @Min(1) Long oldTypeId,
                                        @Min(1) Long newTypeId) {

        Animal animal = animalRepository.findById(animalId).orElseThrow(NoSuchElementException::new);
        AnimalType oldType = animalTypeRepository.findById(oldTypeId).orElseThrow(NoSuchElementException::new);
        AnimalType newType = animalTypeRepository.findById(newTypeId).orElseThrow(NoSuchElementException::new);

        animal.removeAnimalType(oldType);
        animal.addAnimalType(newType);

        return animalMapper.toDto(animalRepository.save(animal));
    }

    @Override
    public AnimalDto deleteTypeOfAnimal(@Min(1) Long animalId, @Min(1) Long typeId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(NoSuchElementException::new);
        AnimalType type = animalTypeRepository.findById(typeId).orElseThrow(NoSuchElementException::new);

        animal.removeAnimalType(type);
        if (animal.getAnimalTypes().size() == 0) {
            throw new RemovingSingleTypeOfAnimalException();
        }

        return animalMapper.toDto(animalRepository.save(animal));
    }

}
