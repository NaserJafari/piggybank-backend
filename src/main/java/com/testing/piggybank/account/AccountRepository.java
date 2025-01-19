package com.testing.piggybank.account;

import com.testing.piggybank.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    /**
     * Get all accounts from database.
     * @return List of {@link Account}
     */
    List<Account> findAll();

    /**
     * Get all accounts from database for specifiv userId;
     * @return List of {@link Account}
     */
    List<Account> findAllByUserId(long userId);
}
