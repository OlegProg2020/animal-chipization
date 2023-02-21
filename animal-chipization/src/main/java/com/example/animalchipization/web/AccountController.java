package com.example.animalchipization.web;

import com.example.animalchipization.models.Account;
import com.example.animalchipization.data.AccountRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@RestController
@RequestMapping(path = "/accounts", produces = "application/json")
public class AccountController {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> accountById(@PathVariable("accountId") Long accountId) {
        ResponseEntity<Account> response;
        if(accountId <= 0) {
            response = new ResponseEntity<>(null, HttpStatus.valueOf(400));
        } else {
            Optional<Account> account = accountRepository.findById(accountId);
            if(account.isPresent()) {
                response = new ResponseEntity<>(account.get(), HttpStatus.valueOf(200));
            } else {
                response = new ResponseEntity<>(null, HttpStatus.valueOf(404));
            }
        }
        return response;
    }


}
