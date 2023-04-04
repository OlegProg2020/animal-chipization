package com.example.animalchipization;

import com.example.animalchipization.model.Account;
import com.example.animalchipization.model.enums.Role;
import com.example.animalchipization.service.AccountService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AnimalChipizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimalChipizationApplication.class, args);
    }

    @Bean
    public ApplicationRunner dataLoader(AccountService accountService) {
        return args -> {
            Account admin = new Account("adminFirstName", "adminLastName",
                    "admin@simbirsoft.com", "qwerty123");
            admin.setRole(Role.ADMIN);
            accountService.registry(admin);

            Account chipper = new Account("chipperFirstName", "chipperLastName",
                    "chipper@simbirsoft.com", "qwerty123");
            chipper.setRole(Role.CHIPPER);
            accountService.registry(chipper);

            Account user = new Account("userFirstName", "userLastName",
                    "user@simbirsoft.com", "qwerty123");
            user.setRole(Role.USER);
            accountService.registry(user);
        };
    }
}
