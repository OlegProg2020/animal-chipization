package com.example.animalchipization.web.controller;

import com.example.animalchipization.dto.AccountDto;
import com.example.animalchipization.service.AccountService;
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
@RequestMapping(path = "/registration", produces = "application/json")
@Validated
public class AuthenticationController {

    private final AccountService accountService;

    @Autowired
    public AuthenticationController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<AccountDto> registry(@RequestBody @Valid AccountDto accountDto) {
        return new ResponseEntity<>(accountService.registry(accountDto), HttpStatus.valueOf(201));
    }

}
