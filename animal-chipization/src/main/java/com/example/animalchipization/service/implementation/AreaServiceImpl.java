package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AreaRepository;
import com.example.animalchipization.dto.AreaDto;
import com.example.animalchipization.entity.Area;
import com.example.animalchipization.service.AreaService;
import com.example.animalchipization.service.mapper.Mapper;
import com.example.animalchipization.service.validator.AreaBusinessRulesValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;
    private final Mapper<Area, AreaDto> areaMapper;
    private final AreaBusinessRulesValidator areaBusinessRulesValidator;

    @Autowired
    public AreaServiceImpl(AreaRepository areaRepository,
                           Mapper<Area, AreaDto> areaMapper,
                           AreaBusinessRulesValidator areaBusinessRulesValidator) {

        this.areaRepository = areaRepository;
        this.areaMapper = areaMapper;
        this.areaBusinessRulesValidator = areaBusinessRulesValidator;
    }

    @Override
    public AreaDto findById(@Min(1) Long id) {
        return areaMapper.toDto(areaRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    @Override
    @Transactional
    public AreaDto save(@Valid AreaDto areaDto) {
        Area area = areaMapper.toEntity(areaDto);

        areaBusinessRulesValidator.validateArea(area);

        area.setId(areaRepository.save(area));
        return areaMapper.toDto(area);
    }

    @Override
    @Transactional
    public AreaDto update(@Valid AreaDto areaDto) {
        Area area = areaMapper.toEntity(areaDto);

        if (!areaRepository.existsById(area.getId())) {
            throw new NoSuchElementException();
        }
        areaBusinessRulesValidator.validateArea(area);

        areaRepository.update(area);
        return areaDto;
    }

    @Override
    @Transactional
    public void deleteById(@Min(1) Long id) {
        areaRepository.deleteById(id);
    }

}
