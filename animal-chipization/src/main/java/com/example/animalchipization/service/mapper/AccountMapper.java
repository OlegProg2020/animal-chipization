package com.example.animalchipization.service.mapper;

import com.example.animalchipization.entity.Account;
import com.example.animalchipization.web.dto.AccountDto;
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
