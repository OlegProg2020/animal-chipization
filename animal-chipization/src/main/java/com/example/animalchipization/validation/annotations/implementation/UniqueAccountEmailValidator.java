package com.example.animalchipization.validation.annotations.implementation;

import com.example.animalchipization.data.repository.AccountRepository;
import com.example.animalchipization.exception.AccountWithThisEmailAlreadyExistsException;
import com.example.animalchipization.validation.annotations.UniqueAccountEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueAccountEmailValidator implements ConstraintValidator<UniqueAccountEmail, String> {

    private final AccountRepository accountRepository;

    @Autowired
    public UniqueAccountEmailValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void initialize(UniqueAccountEmail uniqueAccountEmail) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (!accountRepository.existsByEmail(email)) {
            return true;
        } else {
            throw new AccountWithThisEmailAlreadyExistsException();
        }
    }
}