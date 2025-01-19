package com.testing.piggybank;

import com.testing.piggybank.account.AccountRepository;
import com.testing.piggybank.account.AccountService;
import com.testing.piggybank.model.Account;
import com.testing.piggybank.model.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
@Transactional
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setId(1);
        account.setBalance(BigDecimal.valueOf(1000));
        accountRepository.save(account);
    }

    @Test
    public void testGetAccount() {
        Optional<Account> result = accountService.getAccount(1L);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(1L, result.get().getId());
    }
}