package com.example.animalchipization.web.controllers;

import com.example.animalchipization.data.repositories.AccountRepository;
import com.example.animalchipization.exceptions.AccountWithThisEmailAlreadyExistsException;
import com.example.animalchipization.models.Account;
import com.example.animalchipization.web.forms.AccountForm;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.example.animalchipization.data.specifications.AccountSpecification.*;

@RestController
@RequestMapping(path = "/accounts", produces = "application/json")
@Validated
public class AccountController {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountController(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> findAccountById(@PathVariable("accountId") @Min(1) Long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            return new ResponseEntity<>(optionalAccount.get(), HttpStatus.valueOf(200));
        } else {
            throw new NoSuchElementException();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Iterable<Account>> searchForAccounts(
            @RequestParam(name = "firstName", required = false) String firstName,
            @RequestParam(name = "lastName", required = false) String lastName,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "from", required = false, defaultValue = "0") @Min(0) Integer from,
            @RequestParam(name = "size", required = false, defaultValue = "10") @Min(1) Integer size) {

        PageRequest pageRequest = PageRequest.of(from, size, Sort.by("id").ascending());
        Specification<Account> specifications = Specification
                .where(firstNameLike(firstName).and(lastNameLike(lastName)).and(emailLike(email)));
        Iterable<Account> accounts = accountRepository.findAll(specifications, pageRequest).getContent();
        return new ResponseEntity<>(accounts, HttpStatus.valueOf(200));
    }

    @PutMapping(path = "/{accountId}", consumes = "application/json")
    @PreAuthorize("#{isAuthenticated()}")
    public ResponseEntity<Account> updateAccount(@PathVariable("accountId") @Min(1) Long accountId,
                                                 @RequestBody @Valid AccountForm accountForm,
                                                 @AuthenticationPrincipal Account authenticatedAccount) {

        if(!authenticatedAccount.getId().equals(accountId)) {
            throw new AccessDeniedException("Updating a non-personal account");
        } else {
            if (!accountRepository.existsByEmail(accountForm.getEmail())) {
                Account account = accountForm.toAccount(passwordEncoder);
                account.setId(accountId);
                return new ResponseEntity<>(accountRepository.save(account), HttpStatus.valueOf(200));
            } else {
                throw new AccountWithThisEmailAlreadyExistsException();
            }
        }
    }

    @DeleteMapping(path = "/{accountId}", consumes = "application/json")
    @PreAuthorize("#{isAuthenticated()}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteAccount(@PathVariable("accountId") @Min(1) Long accountId,
                                    @AuthenticationPrincipal Account authenticatedAccount) {

        if(!authenticatedAccount.getId().equals(accountId)) {
            throw new AccessDeniedException("Deleting a non-personal account");
        } else {
            accountRepository.deleteById(accountId);
        }
    }

}
