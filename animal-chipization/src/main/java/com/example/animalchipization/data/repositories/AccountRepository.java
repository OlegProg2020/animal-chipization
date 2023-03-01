package com.example.animalchipization.data.repositories;

import com.example.animalchipization.models.Account;

import com.example.animalchipization.models.AccountProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    public Optional<Account> findByEmail(String email);
    public Boolean existsByEmail(String email);
    public Optional<AccountProjection> findProjectionById(Long id);
    public AccountProjection saveAndReturnProjection(Account account);
    public Iterable<AccountProjection> findAllAndReturnProjections(Specification<Account> spec, Pageable pageable);
}
