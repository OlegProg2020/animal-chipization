package com.example.animalchipization.service;

import com.example.animalchipization.web.dto.AccountDto;

public interface AccountService {

    AccountDto findAccountById(Long accountId);

    Iterable<AccountDto> searchForAccounts(String firstName, String lastName, String email, Integer from, Integer size);

    AccountDto updateAccount(AccountDto accountDto);

    void deleteAccountById(Long accountId);

    AccountDto registry(AccountDto accountDto);
}
