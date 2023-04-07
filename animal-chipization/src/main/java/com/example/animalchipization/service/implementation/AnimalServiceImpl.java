package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AnimalRepository;
import com.example.animalchipization.data.repository.AnimalTypeRepository;
import com.example.animalchipization.entity.Animal;
import com.example.animalchipization.entity.AnimalType;
import com.example.animalchipization.entity.AnimalVisitedLocation;
import com.example.animalchipization.entity.enums.Gender;
import com.example.animalchipization.entity.enums.LifeStatus;
import com.example.animalchipization.exception.*;
import com.example.animalchipization.mapper.Mapper;
import com.example.animalchipization.service.AnimalService;
import com.example.animalchipization.util.AnimalUtils;
import com.example.animalchipization.util.OffsetBasedPageRequest;
import com.example.animalchipization.web.dto.AnimalDto;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.animalchipization.data.specification.AnimalSpecification.*;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalUtils animalUtils;
    private final AnimalRepository animalRepository;
    private final AnimalTypeRepository animalTypeRepository;
    private final Mapper<Animal, AnimalDto> animalMapper;

    @Autowired
    public AnimalServiceImpl(AnimalUtils animalUtils,
                             AnimalRepository animalRepository,
                             AnimalTypeRepository animalTypeRepository,
                             Mapper<Animal, AnimalDto> animalMapper) {
        this.animalUtils = animalUtils;
        this.animalRepository = animalRepository;
        this.animalTypeRepository = animalTypeRepository;
        this.animalMapper = animalMapper;
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
    public Animal updateAnimal(Long animalId, Animal newAnimalDetails) {
        Animal oldAnimalDetails = animalRepository.findById(animalId)
                .orElseThrow(NoSuchElementException::new);
        Optional<AnimalVisitedLocation> optFirstVisitedLocation =
                oldAnimalDetails.getVisitedLocations().stream().findFirst();
        if (optFirstVisitedLocation.isPresent()
                && optFirstVisitedLocation.get().getLocationPoint()
                .equals(newAnimalDetails.getChippingLocation())) {
            throw new AnimalIsAlreadyAtThisPointException();
        }
        oldAnimalDetails.setWeight(newAnimalDetails.getWeight());
        oldAnimalDetails.setLength(newAnimalDetails.getLength());
        oldAnimalDetails.setHeight(newAnimalDetails.getHeight());
        oldAnimalDetails.setGender(newAnimalDetails.getGender());
        if (oldAnimalDetails.getLifeStatus() == LifeStatus.ALIVE) {
            if (newAnimalDetails.getLifeStatus() == LifeStatus.DEAD) {
                oldAnimalDetails.setLifeStatusToDeadAndSetDeathDateTime();
            }
        } else {
            if (newAnimalDetails.getLifeStatus() == LifeStatus.ALIVE) {
                throw new SettingLifeStatusInAliveFromDeadException();
            }
        }
        oldAnimalDetails.setChipper(newAnimalDetails.getChipper());
        oldAnimalDetails.setChippingLocation(newAnimalDetails.getChippingLocation());
        return animalRepository.save(oldAnimalDetails);
    }

    @Override
    @Transactional
    public void deleteAnimalById(@Min(1) Long animalId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(NoSuchElementException::new);
        if (animalUtils.checkAnimalAtChippingLocation(animalMapper.toDto(animal))) {
            animalRepository.delete(animal);
        } else {
            throw new AttemptToRemoveAnimalNotAtTheChippingPointException();
        }
    }

    @Override
    public Animal addTypeToAnimal(Long animalId, Long typeId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(NoSuchElementException::new);
        AnimalType type = animalTypeRepository.findById(typeId).orElseThrow(NoSuchElementException::new);
        if (!animal.addAnimalType(type)) {
            throw new DuplicateCollectionItemException();
        }
        return animalRepository.save(animal);
    }

    @Override
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

    @Override
    public Animal deleteTypeOfAnimal(Long animalId, Long typeId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(NoSuchElementException::new);
        AnimalType type = animalTypeRepository.findById(typeId).orElseThrow(NoSuchElementException::new);
        if (!animal.removeAnimalType(type)) {
            throw new NoSuchElementException();
        }
        if (animal.getAnimalTypes().size() == 0) {
            throw new RemovingSingleTypeOfAnimalException();
        }
        return animalRepository.save(animal);
    }

}
