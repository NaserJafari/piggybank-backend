package com.testing.piggybank;

import com.testing.piggybank.account.*;
import com.testing.piggybank.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AccountControllerApiTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    public void testGetAccount() {
        long accountId = 1L;
        Account account = new Account();
        account.setId((int) accountId);
        account.setName("Test Account");
        account.setBalance(BigDecimal.valueOf(100.0));

        when(accountService.getAccount(accountId)).thenReturn(Optional.of(account));

        ResponseEntity<AccountResponse> response = accountController.getAccount(accountId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountId, response.getBody().getId());
        assertEquals("Test Account", response.getBody().getName());
        assertEquals(BigDecimal.valueOf(100.0), response.getBody().getBalance());
    }

    @Test
    public void testGetAccountNotFound() {
        long accountId = 1L;

        when(accountService.getAccount(accountId)).thenReturn(Optional.empty());

        ResponseEntity<AccountResponse> response = accountController.getAccount(accountId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAccounts() {
        long userId = 1L;
        Account account1 = new Account();
        account1.setId(1);
        account1.setName("Account 1");
        account1.setBalance(BigDecimal.valueOf(100.0));

        Account account2 = new Account();
        account2.setId(2);
        account2.setName("Account 2");
        account2.setBalance(BigDecimal.valueOf(200.0));

        when(accountService.getAccountsByUserId(userId)).thenReturn(List.of(account1, account2));

        ResponseEntity<GetAccountsResponse> response = accountController.getAccounts(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getAccounts().size());
    }

    @Test
    public void testUpdateAccount() {
        UpdateAccountRequest request = new UpdateAccountRequest();
        request.setAccountId(1L);
        request.setAccountName("Updated Account");

        ResponseEntity<HttpStatus> response = accountController.updateAccount(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}