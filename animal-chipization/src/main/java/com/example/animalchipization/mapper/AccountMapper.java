package com.example.animalchipization.mapper;

import com.example.animalchipization.entity.Account;
import com.example.animalchipization.web.dto.AccountDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AccountMapper implements Mapper<Account, AccountDto> {

    private final ModelMapper mapper;

    @Autowired
    public AccountMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Account toEntity(AccountDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, Account.class);
    }

    @Override
    public AccountDto toDto(Account entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, AccountDto.class);
    }
}
