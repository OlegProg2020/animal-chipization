package com.example.animalchipization.config;

import com.example.animalchipization.model.enums.Role;
import com.example.animalchipization.service.AccountService;
import com.example.animalchipization.web.dto.AccountDto;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataConfig {
    @Bean
    public ApplicationRunner dataLoader(AccountService accountService) {
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
        };
    }

}
