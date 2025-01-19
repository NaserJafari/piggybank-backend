package com.testing.piggybank;

import com.testing.piggybank.model.Account;
import com.testing.piggybank.model.Currency;
import com.testing.piggybank.model.Transaction;
import com.testing.piggybank.account.AccountRepository;
import com.testing.piggybank.helper.CurrencyConverterService;
import com.testing.piggybank.transaction.CreateTransactionRequest;
import com.testing.piggybank.transaction.TransactionRepository;
import com.testing.piggybank.transaction.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@SpringBootTest
@Transactional
public class TransactionServiceIntegrationTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CurrencyConverterService converterService;

    private Account senderAccount;
    private Account receiverAccount;

    @BeforeEach
    public void setUp() {
        senderAccount = new Account();
        senderAccount.setId(1);
        senderAccount.setBalance(BigDecimal.valueOf(1000));
        accountRepository.save(senderAccount);

        receiverAccount = new Account();
        receiverAccount.setId(2);
        receiverAccount.setBalance(BigDecimal.valueOf(500));
        accountRepository.save(receiverAccount);
    }

    @Test
    public void testCreateTransaction() {
        transactionRepository.deleteAll();

        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setSenderAccountId(senderAccount.getId());
        request.setReceiverAccountId(receiverAccount.getId());
        request.setAmount(BigDecimal.TEN);
        request.setCurrency(Currency.EURO);
        request.setDescription("Integration Test Transaction");

        transactionService.createTransaction(request);

        List<Transaction> transactions = (List<Transaction>) transactionRepository.findAll();
        Assertions.assertEquals(1, transactions.size());

        Transaction transaction = transactions.get(0);
        Assertions.assertEquals(BigDecimal.TEN, transaction.getAmount());
        Assertions.assertEquals(Currency.EURO, transaction.getCurrency());
        Assertions.assertEquals("Integration Test Transaction", transaction.getDescription());
        Assertions.assertEquals(senderAccount.getId(), transaction.getSenderAccount().getId());
        Assertions.assertEquals(receiverAccount.getId(), transaction.getReceiverAccount().getId());
    }

    @Test
    public void testGetTransactions() {
        transactionRepository.deleteAll();

        Transaction transaction = new Transaction();
        transaction.setSenderAccount(senderAccount);
        transaction.setReceiverAccount(receiverAccount);
        transaction.setAmount(BigDecimal.TEN);
        transaction.setCurrency(Currency.EURO);
        transaction.setDescription("Test Transaction");
        transaction.setDateTime(Instant.now());
        transactionRepository.save(transaction);

        List<Transaction> transactions = transactionService.getTransactions(10, senderAccount.getId());
        Assertions.assertEquals(1, transactions.size());
    }
}