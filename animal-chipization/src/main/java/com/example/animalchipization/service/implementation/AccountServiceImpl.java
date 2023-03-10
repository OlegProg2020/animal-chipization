package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AccountRepository;
import com.example.animalchipization.exception.AccountWithThisEmailAlreadyExistsException;
import com.example.animalchipization.model.Account;
import com.example.animalchipization.service.AccountService;
import com.example.animalchipization.util.OffsetBasedPageRequest;
import com.example.animalchipization.web.form.AccountForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.example.animalchipization.data.specification.AccountSpecification.*;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Account findAccountById(Long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Iterable<Account> searchForAccounts(
            String firstName, String lastName, String email, Integer from, Integer size) {
        OffsetBasedPageRequest pageRequest =
                new OffsetBasedPageRequest(from, size, Sort.by("id").ascending());
        Specification<Account> specifications =
                Specification.where(
                        firstNameLike(firstName).and(lastNameLike(lastName)).and(emailLike(email)));
        return accountRepository.findAll(specifications, pageRequest).getContent();
    }

    @Override
    @PreAuthorize("#accountId == authentication.principal.getId()")
    public Account updateAccountById(Long accountId, AccountForm accountForm) {
        Account currentAccountDetails =
                accountRepository.findById(accountId).orElseThrow(NoSuchElementException::new);
        Account newAccountDetails = accountForm.toAccount(passwordEncoder);
        if (!accountRepository.existsByEmail(newAccountDetails.getEmail())
                || currentAccountDetails.getEmail().equals(newAccountDetails.getEmail())) {

            newAccountDetails.setId(accountId);
            return accountRepository.save(newAccountDetails);
        } else {
            throw new AccountWithThisEmailAlreadyExistsException();
        }
    }

    @Override
    @PreAuthorize("#accountId == authentication.principal.getId()")
    public void deleteAccountById(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    @Override
    public Account registry(AccountForm accountForm) {
        if (!accountRepository.existsByEmail(accountForm.getEmail())) {
            return accountRepository.save(accountForm.toAccount(passwordEncoder));
        } else {
            throw new AccountWithThisEmailAlreadyExistsException();
        }
    }
}
