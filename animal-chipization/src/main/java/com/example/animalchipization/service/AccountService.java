package com.example.animalchipization.service;

import com.example.animalchipization.model.Account;

public interface AccountService {

    Account findAccountById(Long accountId);

    Iterable<Account> searchForAccounts(String firstName, String lastName, String email, Integer from, Integer size);

    Account updateAccount(Account account);

    void deleteAccountById(Long accountId);

    Account registry(Account account);
}
