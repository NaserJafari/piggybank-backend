package com.testing.piggybank.account;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class UpdateAccountRequest {
    @NotEmpty
    private String accountName;

    @Min(value = 1)
    private long accountId;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
