package com.example.animalchipization.services;

import com.example.animalchipization.models.Account;
import com.example.animalchipization.web.forms.AccountForm;

public interface AccountService {

    Account findAccountById(Long accountId);
    Iterable<Account> searchForAccounts(String firstName, String lastName, String email, Integer from, Integer size);
    Account updateAccountById(Long accountId, AccountForm accountForm);
    void deleteAccountById(Long accountId);
}
