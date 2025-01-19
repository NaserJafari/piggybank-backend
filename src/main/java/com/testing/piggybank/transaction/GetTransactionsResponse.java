package com.testing.piggybank.transaction;

import java.util.List;

public class GetTransactionsResponse {
    private List<TransactionResponse> transactions;

    public List<TransactionResponse> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionResponse> transactions) {
        this.transactions = transactions;
    }
}
