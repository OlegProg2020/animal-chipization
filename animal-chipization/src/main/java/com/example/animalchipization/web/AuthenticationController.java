package com.example.animalchipization.web;

import com.example.animalchipization.data.repositories.AccountRepository;
import com.example.animalchipization.exceptions.AccountWithThisEmailAlreadyExistsException;
import com.example.animalchipization.models.Account;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/registration", produces = "application/json")
@Validated
public class AuthenticationController {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Account> registry(@RequestBody @Valid AccountForm accountForm) {

        if (!accountRepository.existsByEmail(accountForm.getEmail())) {
            return new ResponseEntity<>(accountRepository.save(accountForm.toAccount(passwordEncoder)),
                    HttpStatus.valueOf(201));
        } else {
            throw new AccountWithThisEmailAlreadyExistsException();
        }
    }


}
