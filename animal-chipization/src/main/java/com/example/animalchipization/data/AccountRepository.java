package com.example.animalchipization.data;

import com.example.animalchipization.models.Account;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long>, JpaSpecificationExecutor<Account> {

}
