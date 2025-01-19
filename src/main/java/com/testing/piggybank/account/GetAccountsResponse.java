package com.testing.piggybank.account;

import java.util.ArrayList;
import java.util.List;

public class GetAccountsResponse {
    private List<AccountResponse> accounts = new ArrayList<>();

    public List<AccountResponse> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountResponse> accounts) {
        this.accounts = accounts;
    }
}
