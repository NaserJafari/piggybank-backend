package com.testing.piggybank;

import com.testing.piggybank.transaction.GetTransactionsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionApiTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetTransactions() {
        ResponseEntity<GetTransactionsResponse> response = restTemplate
            .getForEntity("/api/v1/transactions/1", GetTransactionsResponse.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(5, Objects.requireNonNull(response.getBody()).getTransactions().size());
    }

    @Test
    public void testCreateTransaction() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestJson = "{\"senderAccountId\":1,\"receiverAccountId\":2,\"amount\":100,\"currency\":\"EURO\"}";
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                "/api/v1/transactions",
                entity,
                Void.class);

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}
