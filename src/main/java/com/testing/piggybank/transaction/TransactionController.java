package com.testing.piggybank.transaction;

import com.testing.piggybank.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(final TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Get transactions for given account ID.
     *
     * @param limit     Limit the list of transaction (max results).
     * @param accountId The account ID of the requested transactions
     * @return {@link GetTransactionsResponse}
     */
    @GetMapping(path = "{accountId}")
    public ResponseEntity<GetTransactionsResponse> getTransactions(
            @RequestParam(name = "limit", required = false) final Integer limit,
            @PathVariable final long accountId) {

        // Fetch transactions and map it.
        final List<TransactionResponse> transactionResponses = transactionService.getTransactions(limit, accountId)
                .stream()
                .map(transaction -> mapTransactionToTransactionResponse(transaction, accountId))
                .collect(Collectors.toList());

        // Create the response
        final GetTransactionsResponse response = new GetTransactionsResponse();
        response.setTransactions(transactionResponses);
        return ResponseEntity.ok(response);
    }

    /**
     * Create a new transaction (transfer)
     *
     * @param request {@link CreateTransactionRequest} information to create the transactions
     * @return {@link HttpStatus}
     */
    @PostMapping
    public ResponseEntity<HttpStatus> createTransaction(@RequestBody final CreateTransactionRequest request) {
        transactionService.createTransaction(request);
        return ResponseEntity.ok().build();
    }

    public TransactionResponse mapTransactionToTransactionResponse(final Transaction transaction, final long accountId) {
        final TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setId(transaction.getId());

        transactionResponse.setDescription(transaction.getDescription());
        transactionResponse.setDateTime(transaction.getDateTime());

        // When transaction is initiated from current account, the amount is credit (subtracted).
        if (accountId == transaction.getSenderAccount().getId()) {
            transactionResponse.setAmount(transaction.getAmount().negate());
        } else {
            transactionResponse.setAmount(transaction.getAmount());
        }
        return transactionResponse;
    }
}
