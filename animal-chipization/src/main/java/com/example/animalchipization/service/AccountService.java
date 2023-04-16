package com.example.animalchipization.service;

import com.example.animalchipization.dto.AccountDto;

import java.util.Collection;

public interface AccountService {

    AccountDto findById(Long accountId);

    Collection<AccountDto> searchForAccounts(String firstName, String lastName,
                                             String email, Integer from, Integer size);

    AccountDto update(AccountDto accountDto);

    void deleteById(Long accountId);

    AccountDto registry(AccountDto accountDto);
}
