package com.example.animalchipization.web.controller;

import com.example.animalchipization.dto.AccountDto;
import com.example.animalchipization.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/accounts", produces = "application/json")
@Validated
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountId}")
    @PostAuthorize("#accountId.equals(authentication.principal.getId()) " +
            "or hasRole('ADMIN')")
    public ResponseEntity<AccountDto> findAccountById(@PathVariable("accountId") @Min(1) Long accountId) {
        return new ResponseEntity<>(accountService.findAccountById(accountId), HttpStatus.valueOf(200));
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Collection<AccountDto>> searchForAccounts(
            @RequestParam(name = "firstName", required = false) String firstName,
            @RequestParam(name = "lastName", required = false) String lastName,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "from", required = false, defaultValue = "0") @Min(0) Integer from,
            @RequestParam(name = "size", required = false, defaultValue = "10") @Min(1) Integer size) {

        Collection<AccountDto> accounts = accountService
                .searchForAccounts(firstName, lastName, email, from, size);
        return new ResponseEntity<>(accounts, HttpStatus.valueOf(200));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountDto> addAccount(@RequestBody @Valid AccountDto accountDto) {
        return new ResponseEntity<>(accountService.registry(accountDto), HttpStatus.valueOf(201));
    }

    @PutMapping(path = "/{accountId}", consumes = "application/json")
    @PostAuthorize("#accountId.equals(authentication.principal.getId()) " +
            "or hasRole('ADMIN')")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable("accountId") @Min(1) Long accountId,
                                                    @RequestBody @Valid AccountDto accountDto) {
        accountDto.setId(accountId);
        return new ResponseEntity<>(accountService.updateAccount(accountDto), HttpStatus.valueOf(200));
    }

    @DeleteMapping(path = "/{accountId}", consumes = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @PostAuthorize("#accountId.equals(authentication.principal.getId()) " +
            "or hasRole('ADMIN')")
    public void deleteAccountById(@PathVariable("accountId") @Min(1) Long accountId) {
        accountService.deleteAccountById(accountId);
    }

}
