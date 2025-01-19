package com.testing.piggybank;

import com.testing.piggybank.model.Account;
import com.testing.piggybank.model.Currency;
import com.testing.piggybank.model.Transaction;
import com.testing.piggybank.transaction.CreateTransactionRequest;
import com.testing.piggybank.transaction.TransactionRepository;
import com.testing.piggybank.transaction.TransactionService;
import com.testing.piggybank.helper.CurrencyConverterService;
import com.testing.piggybank.account.AccountService;
import com.testing.piggybank.model.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    private TransactionService transactionService;
    private TransactionRepository transactionRepository;
    private CurrencyConverterService converterService;
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        converterService = mock(CurrencyConverterService.class);
        accountService = mock(AccountService.class);
        transactionService = new TransactionService(transactionRepository, converterService, accountService);
    }

    @Test
    public void testGetTransactions() {
        when(transactionRepository.findAll()).thenReturn(Collections.emptyList());

        List<Transaction> result = transactionService.getTransactions(10, 1L);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testFilterAndLimitTransactions() {
        Transaction transaction = new Transaction();
        Account account = new Account();
        account.setId(1);
        transaction.setReceiverAccount(account);
        transaction.setSenderAccount(account);

        List<Transaction> transactionList = List.of(transaction);
        List<Transaction> result = transactionService.filterAndLimitTransactions(transactionList, 1L, 10);

        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void testCreateTransaction() {
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setSenderAccountId(1L);
        request.setReceiverAccountId(2L);
        request.setAmount(BigDecimal.TEN);
        request.setCurrency(Currency.EURO);
        request.setDescription("Test transaction");

        Account senderAccount = new Account();
        senderAccount.setId(1);
        Account receiverAccount = new Account();
        receiverAccount.setId(2);

        when(accountService.getAccount(1L)).thenReturn(Optional.of(senderAccount));
        when(accountService.getAccount(2L)).thenReturn(Optional.of(receiverAccount));
        when(converterService.toEuro(any(Currency.class), any(BigDecimal.class))).thenReturn(BigDecimal.TEN);

        transactionService.createTransaction(request);

        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(accountService, times(1)).updateBalance(1L, BigDecimal.TEN, Direction.CREDIT);
        verify(accountService, times(1)).updateBalance(2L, BigDecimal.TEN, Direction.DEBIT);
    }
}