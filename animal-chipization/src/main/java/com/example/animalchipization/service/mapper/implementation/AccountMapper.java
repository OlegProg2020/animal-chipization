package com.example.animalchipization.service.mapper.implementation;

import com.example.animalchipization.dto.AccountDto;
import com.example.animalchipization.entity.Account;
import com.example.animalchipization.service.mapper.DefaultMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper extends DefaultMapper<Account, AccountDto> {

    @Autowired
    public AccountMapper(ModelMapper mapper) {
        super(mapper, Account.class, AccountDto.class);
    }
}
