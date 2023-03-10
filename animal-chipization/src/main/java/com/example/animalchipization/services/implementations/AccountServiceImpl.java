package com.example.animalchipization.services.implementations;

import com.example.animalchipization.data.repositories.AccountRepository;
import com.example.animalchipization.exceptions.AccountWithThisEmailAlreadyExistsException;
import com.example.animalchipization.models.Account;
import com.example.animalchipization.services.AccountService;

import static com.example.animalchipization.data.specifications.AccountSpecification.*;

import com.example.animalchipization.services.OffsetBasedPageRequest;
import com.example.animalchipization.web.forms.AccountForm;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;

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
    public Iterable<Account> searchForAccounts(String firstName, String lastName, String email,
                                               Integer from, Integer size) {
        OffsetBasedPageRequest pageRequest = new OffsetBasedPageRequest(from, size, Sort.by("id").ascending());
        Specification<Account> specifications = Specification
                .where(firstNameLike(firstName).and(lastNameLike(lastName)).and(emailLike(email)));
        return accountRepository.findAll(specifications, pageRequest).getContent();
    }

    @Override
    public Account updateAccountById(Long accountId, AccountForm accountForm) {
        Account currentAccountDetails = accountRepository.findById(accountId)
                .orElseThrow(NoSuchElementException::new);
        Account newAccountDetails = accountForm.toAccount(passwordEncoder);
        if (!accountRepository.existsByEmail(newAccountDetails.getEmail()) ||
                currentAccountDetails.getEmail().equals(newAccountDetails.getEmail())) {

            newAccountDetails.setId(accountId);
            return accountRepository.save(newAccountDetails);
        } else {
            throw new AccountWithThisEmailAlreadyExistsException();
        }
    }

    @Override
    public void deleteAccountById(Long accountId) {
        accountRepository.deleteById(accountId);
    }

}
