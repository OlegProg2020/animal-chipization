package com.example.animalchipization.service.implementation;

import com.example.animalchipization.model.enums.LifeStatus;
import com.example.animalchipization.model.enums.Gender;
import com.example.animalchipization.web.form.AnimalForm;

import com.example.animalchipization.data.repository.AnimalRepository;
import com.example.animalchipization.model.Animal;
import com.example.animalchipization.service.AnimalService;
import com.example.animalchipization.util.OffsetBasedPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static com.example.animalchipization.data.specification.AnimalSpecification.*;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;

    @Autowired
    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
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
    public Animal addAnimal(AnimalForm animalForm) {
        Animal animal = animalForm.toAnimal();
        return animalRepository.save(animal);
    }

}
