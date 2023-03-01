package com.example.animalchipization.web;

import com.example.animalchipization.data.repositories.AccountRepository;
import com.example.animalchipization.models.Account;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/registration", produces = "application/json", consumes = "application/json")
@Validated
public class AuthenticationController {

    private final AccountRepository accountRepository;

    @Autowired
    public AuthenticationController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostMapping
    public ResponseEntity<Account> registry(@RequestBody @Valid Account account) {
        return new ResponseEntity<>(accountRepository.save(account), HttpStatus.valueOf(201));
    }
}
