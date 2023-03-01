package com.example.animalchipization.services.annotations;

import com.example.animalchipization.data.repositories.AccountRepository;
import com.example.animalchipization.models.Account;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final AccountRepository accountRepository;

    @Autowired
    public UniqueEmailValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void initialize(UniqueEmail uniqueUEmail) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        return optionalAccount.isEmpty();
    }
}
