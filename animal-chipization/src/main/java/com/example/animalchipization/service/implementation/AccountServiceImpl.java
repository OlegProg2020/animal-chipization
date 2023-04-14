package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AccountRepository;
import com.example.animalchipization.dto.AccountDto;
import com.example.animalchipization.entity.Account;
import com.example.animalchipization.exception.AccountWithSuchEmailAlreadyExistsException;
import com.example.animalchipization.service.AccountService;
import com.example.animalchipization.service.mapper.Mapper;
import com.example.animalchipization.util.pagination.OffsetBasedPageRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.example.animalchipization.data.specification.AccountSpecification.*;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final Mapper<Account, AccountDto> mapper;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder,
                              Mapper<Account, AccountDto> mapper) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Override
    public AccountDto findAccountById(@Min(1) Long accountId) {
        return mapper.toDto(accountRepository.findById(accountId).orElseThrow(NoSuchElementException::new));
    }

    @Override
    public Collection<AccountDto> searchForAccounts(String firstName, String lastName,
                                                    String email, @Min(0) Integer from, @Min(1) Integer size) {
        OffsetBasedPageRequest pageRequest =
                new OffsetBasedPageRequest(from, size, Sort.by("id").ascending());
        Specification<Account> specifications = Specification.where(
                firstNameLike(firstName)
                        .and(lastNameLike(lastName))
                        .and(emailLike(email)));

        return accountRepository.findAll(specifications, pageRequest).getContent()
                .stream().map(account -> mapper.toDto(account))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AccountDto updateAccount(@Valid AccountDto accountDto) {
        Account updatingAccount = mapper.toEntity(accountDto);
        updatingAccount.setPassword(passwordEncoder.encode(updatingAccount.getPassword()));
        try {
            return mapper.toDto(accountRepository.save(updatingAccount));
        } catch (DataIntegrityViolationException exception) {
            throw new AccountWithSuchEmailAlreadyExistsException();
        }
    }

    @Override
    @Transactional
    public void deleteAccountById(@Min(1) Long accountId) {
        try {
            accountRepository.deleteById(accountId);
        } catch (EmptyResultDataAccessException exception) {
            throw new NoSuchElementException();
        }

    }

    @Override
    @Transactional
    public AccountDto registry(@Valid AccountDto accountDto) {
        Account newAccount = mapper.toEntity(accountDto);
        newAccount.setPassword(passwordEncoder.encode(newAccount.getPassword()));
        try {
            return mapper.toDto(accountRepository.save(newAccount));
        } catch (DataIntegrityViolationException exception) {
            throw new AccountWithSuchEmailAlreadyExistsException();
        }
    }

}
