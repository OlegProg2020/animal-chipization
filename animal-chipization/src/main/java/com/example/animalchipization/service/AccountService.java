package com.example.animalchipization.service;

import com.example.animalchipization.model.Account;
import com.example.animalchipization.web.form.AccountForm;

public interface AccountService {

    Account findAccountById(Long accountId);
    Iterable<Account> searchForAccounts(String firstName, String lastName, String email, Integer from, Integer size);
    Account updateAccountById(Long accountId, AccountForm accountForm);
    void deleteAccountById(Long accountId);
    Account registry(AccountForm accountForm);
}
