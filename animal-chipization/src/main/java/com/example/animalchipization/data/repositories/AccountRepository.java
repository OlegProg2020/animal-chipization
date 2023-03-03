package com.example.animalchipization.data.repositories;

import com.example.animalchipization.models.Account;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    Optional<Account> findByEmail(String email);

    Boolean existsByEmail(String email);

}
