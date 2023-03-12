package com.example.animalchipization.web.controller;

import com.example.animalchipization.model.Account;
import com.example.animalchipization.service.AccountService;
import com.example.animalchipization.web.form.AccountForm;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/accounts", produces = "application/json")
@Validated
public class AccountController {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountController(AccountService accountService, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> findAccountById(@PathVariable("accountId") @Min(1) Long accountId) {
        return new ResponseEntity<>(accountService.findAccountById(accountId), HttpStatus.valueOf(200));
    }

    @GetMapping("/search")
    public ResponseEntity<Iterable<Account>> searchForAccounts(
            @RequestParam(name = "firstName", required = false) String firstName,
            @RequestParam(name = "lastName", required = false) String lastName,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "from", required = false, defaultValue = "0") @Min(0) Integer from,
            @RequestParam(name = "size", required = false, defaultValue = "10") @Min(1) Integer size) {

        Iterable<Account> accounts = accountService.searchForAccounts(firstName, lastName, email, from, size);
        return new ResponseEntity<>(accounts, HttpStatus.valueOf(200));
    }

    @PutMapping(path = "/{accountId}", consumes = "application/json")
    public ResponseEntity<Account> updateAccountById(@PathVariable("accountId") @Min(1) Long accountId,
                                                     @RequestBody @Valid AccountForm accountForm) {
        Account account = accountForm.toAccount(passwordEncoder);
        account.setId(accountId);
        return new ResponseEntity<>(accountService.updateAccount(account), HttpStatus.valueOf(200));
    }

    @DeleteMapping(path = "/{accountId}", consumes = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteAccountById(@PathVariable("accountId") @Min(1) Long accountId) {
        accountService.deleteAccountById(accountId);
    }

}
