package com.example.animalchipization.service;

import com.example.animalchipization.dto.AccountDto;

import java.util.Collection;

public interface AccountService {

    AccountDto findAccountById(Long accountId);

    Collection<AccountDto> searchForAccounts(String firstName, String lastName,
                                             String email, Integer from, Integer size);

    AccountDto updateAccount(AccountDto accountDto);

    void deleteAccountById(Long accountId);

    AccountDto registry(AccountDto accountDto);
}
