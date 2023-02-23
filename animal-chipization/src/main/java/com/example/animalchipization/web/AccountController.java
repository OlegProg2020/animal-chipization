package com.example.animalchipization.web;

import com.example.animalchipization.data.AccountSpecification;
import com.example.animalchipization.data.SearchCriteria;
import com.example.animalchipization.models.Account;
import com.example.animalchipization.data.AccountRepository;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        if (accountId <= 0) {
            response = new ResponseEntity<>(null, HttpStatus.valueOf(400));
        } else {
            Optional<Account> account = accountRepository.findById(accountId);
            if (account.isPresent()) {
                response = new ResponseEntity<>(account.get(), HttpStatus.valueOf(200));
            } else {
                response = new ResponseEntity<>(null, HttpStatus.valueOf(404));
            }
        }
        return response;
    }

    @GetMapping("/search")
    public ResponseEntity<Iterable<Account>> searchAccounts(
            @RequestParam(name = "firstName", required = false) String firstName,
            @RequestParam(name = "lastName", required = false) String lastName,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {

        if (from < 0 || size <= 0) {
            return new ResponseEntity<>(HttpStatus.valueOf(400));
        }
        PageRequest pageRequest = PageRequest.of(from, size, Sort.by("id").ascending());
        Page<Account> accounts = accountRepository.findAll(
                Specification.where(new AccountSpecification(new SearchCriteria("firstName", firstName))
                        .and(new AccountSpecification(new SearchCriteria("lastName", lastName)))
                        .and(new AccountSpecification(new SearchCriteria("email", email)))),
                pageRequest);
        return new ResponseEntity<>(accounts, HttpStatus.valueOf(200));
    }


}
