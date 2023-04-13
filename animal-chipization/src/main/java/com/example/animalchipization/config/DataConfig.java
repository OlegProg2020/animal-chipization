package com.example.animalchipization.config;

import com.example.animalchipization.data.repository.AreaRepository;
import com.example.animalchipization.entity.Area;
import com.example.animalchipization.entity.enums.Role;
import com.example.animalchipization.exception.AnimalIsAlreadyAtThisPointException;
import com.example.animalchipization.service.AccountService;
import com.example.animalchipization.web.dto.AccountDto;
import org.locationtech.jts.geom.Polygon;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

@Configuration
public class DataConfig {
    @Bean
    public ApplicationRunner dataLoader(AccountService accountService, AreaRepository areaRepository) {
        return args -> {
            AccountDto adminDto = AccountDto.builder().withFirstName("adminFirstName")
                    .withLastName("adminLastName").withEmail("admin@simbirsoft.com")
                    .withPassword("qwerty123").withRole(Role.ADMIN).build();
            adminDto.setId(1L);
            accountService.registry(adminDto);

            AccountDto chipperDto = AccountDto.builder().withFirstName("chipperFirstName")
                    .withLastName("chipperLastName").withEmail("chipper@simbirsoft.com")
                    .withPassword("qwerty123").withRole(Role.CHIPPER).build();
            chipperDto.setId(2L);
            accountService.registry(chipperDto);

            AccountDto userDto = AccountDto.builder().withFirstName("userFirstName")
                    .withLastName("userLastName").withEmail("user@simbirsoft.com")
                    .withPassword("qwerty123").withRole(Role.USER).build();
            userDto.setId(3L);
            accountService.registry(userDto);

            // Area
            Area area = new Area();
            area.setId(2L);
            area.setName("myArea");
            GeometryFactory geometryFactory = new GeometryFactory();
            Coordinate[] coordinates = new Coordinate[] {
                    new Coordinate(0, 0),
                    new Coordinate(0, 10),
                    new Coordinate(10, 10),
                    new Coordinate(10, 0),
                    new Coordinate(0, 0)
            };
            Polygon polygon = geometryFactory.createPolygon(coordinates);
            area.setAreaPoints(polygon);
            //var a = areaRepository.findById(1L);
            //var a = areaRepository.save(area);
            //area.setAreaPoints(polygon);
            //areaRepository.save(area);
            var a = areaRepository.findById(10L);
            area.setAreaPoints(polygon);
        };
    }

}
