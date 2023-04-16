package com.example.animalchipization.web.controller;

import com.example.animalchipization.dto.AreaDto;
import com.example.animalchipization.service.AreaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/areas", produces = "application/json")
@Validated
public class AreaController {

    private final AreaService areaService;

    @Autowired
    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @GetMapping("/{areaId}")
    public ResponseEntity<AreaDto> findAreaById(@PathVariable("areaId") @Min(1) Long areaId) {
        return new ResponseEntity<>(areaService.findById(areaId),
                HttpStatus.valueOf(200));
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AreaDto> addArea(@RequestBody @Valid AreaDto areaDto) {
        return new ResponseEntity<>(areaService.save(areaDto),
                HttpStatus.valueOf(201));
    }

    @PostMapping(path = "/{areaId}", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AreaDto> updateArea(@PathVariable(name = "areaId") @Min(1) Long areaId,
                                              @RequestBody @Valid AreaDto areaDto) {

        areaDto.setId(areaId);
        return new ResponseEntity<>(areaService.update(areaDto),
                HttpStatus.valueOf(201));
    }

    @DeleteMapping(path = "/{areaId}", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAreaById(@PathVariable(name = "areaId") @Min(1) Long areaId) {
        areaService.deleteById(areaId);
    }

}
