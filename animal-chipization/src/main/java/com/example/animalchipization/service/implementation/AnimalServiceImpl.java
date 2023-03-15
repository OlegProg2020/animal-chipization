package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AnimalRepository;
import com.example.animalchipization.data.repository.AnimalTypeRepository;
import com.example.animalchipization.exception.DuplicateCollectionItemException;
import com.example.animalchipization.exception.RemovingSingleTypeOfAnimalException;
import com.example.animalchipization.model.Animal;
import com.example.animalchipization.model.AnimalType;
import com.example.animalchipization.model.enums.Gender;
import com.example.animalchipization.model.enums.LifeStatus;
import com.example.animalchipization.service.AnimalService;
import com.example.animalchipization.util.OffsetBasedPageRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.example.animalchipization.data.specification.AnimalSpecification.*;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalTypeRepository animalTypeRepository;

    @Autowired
    public AnimalServiceImpl(AnimalRepository animalRepository, AnimalTypeRepository animalTypeRepository) {
        this.animalRepository = animalRepository;
        this.animalTypeRepository = animalTypeRepository;
    }

    @Override
    public Animal findAnimalById(Long animalId) {
        Optional<Animal> optionalAnimal = animalRepository.findById(animalId);
        if (optionalAnimal.isPresent()) {
            return optionalAnimal.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Iterable<Animal> searchForAnimals(LocalDateTime startDateTime, LocalDateTime endDateTime,
                                             Long chipperId, Long chippingLocationId, LifeStatus lifeStatus,
                                             Gender gender, Integer from, Integer size) {

        OffsetBasedPageRequest pageRequest = new OffsetBasedPageRequest(from, size, Sort.by("id").ascending());
        Specification<Animal> specifications = Specification.where(
                hasChippingDateTimeGreaterThanOrEqualTo(startDateTime)
                        .and(hasChippingDateTimeLessThanOrEqualTo(endDateTime))
                        .and(hasChipperId(chipperId))
                        .and(hasChippingLocationId(chippingLocationId))
                        .and(hasLifeStatus(lifeStatus))
                        .and(hasGender(gender))
        );
        return animalRepository.findAll(specifications, pageRequest).getContent();
    }

    @Override
    public Animal addAnimal(@Valid Animal animal) {
        return animalRepository.save(animal);
    }

    public Animal addTypeToAnimal(Long animalId, Long typeId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(NoSuchElementException::new);
        AnimalType type = animalTypeRepository.findById(typeId).orElseThrow(NoSuchElementException::new);
        if (!animal.addAnimalType(type)) {
            throw new DuplicateCollectionItemException();
        }
        return animalRepository.save(animal);
    }

    public Animal updateTypeOfAnimal(Long animalId, Long oldTypeId, Long newTypeId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(NoSuchElementException::new);
        AnimalType oldType = animalTypeRepository.findById(oldTypeId).orElseThrow(NoSuchElementException::new);
        AnimalType newType = animalTypeRepository.findById(newTypeId).orElseThrow(NoSuchElementException::new);
        if (!animal.removeAnimalType(oldType)) {
            throw new NoSuchElementException();
        }
        if (!animal.addAnimalType(newType)) {
            throw new DuplicateCollectionItemException();
        }
        return animalRepository.save(animal);
    }

    public Animal deleteTypeOfAnimal(Long animalId, Long typeId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(NoSuchElementException::new);
        AnimalType type = animalTypeRepository.findById(typeId).orElseThrow(NoSuchElementException::new);
        if (!animal.removeAnimalType(type)) {
            throw new NoSuchElementException();
        }
        if(animal.getAnimalTypes().size() == 0) {
            throw new RemovingSingleTypeOfAnimalException();
        }
        return animalRepository.save(animal);
    }

}
