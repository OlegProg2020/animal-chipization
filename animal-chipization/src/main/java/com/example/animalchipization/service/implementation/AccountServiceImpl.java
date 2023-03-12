package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AccountRepository;
import com.example.animalchipization.exception.AccountWithThisEmailAlreadyExistsException;
import com.example.animalchipization.model.Account;
import com.example.animalchipization.service.AccountService;
import com.example.animalchipization.util.OffsetBasedPageRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static com.example.animalchipization.data.specification.AccountSpecification.*;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account findAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Iterable<Account> searchForAccounts(String firstName, String lastName,
                                               String email, Integer from, Integer size) {
        OffsetBasedPageRequest pageRequest =
                new OffsetBasedPageRequest(from, size, Sort.by("id").ascending());
        Specification<Account> specifications =
                Specification.where(
                        firstNameLike(firstName).and(lastNameLike(lastName)).and(emailLike(email)));
        return accountRepository.findAll(specifications, pageRequest).getContent();
    }

    @Override
    @PreAuthorize("#account.id == authentication.principal.getId()")
    public Account updateAccount(@Valid Account account) {
        //TODO || currentAccountDetails.getEmail().equals(account.getEmail())
        if (!accountRepository.existsByEmail(account.getEmail())) {
            return accountRepository.save(account);
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
    public Account registry(@Valid Account account) {
        if (!accountRepository.existsByEmail(account.getEmail())) {
            return accountRepository.save(account);
        } else {
            throw new AccountWithThisEmailAlreadyExistsException();
        }
    }
}
