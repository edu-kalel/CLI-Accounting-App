package com.encora.apprentice.cliaccounting;

public class MiniTransaction {
    private Account account;
    private Amount amount;

    public MiniTransaction() {
    }

    public MiniTransaction(Account account, Amount amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "MiniTransaction{" +
                "account=" + account +
                ", amount=" + amount +
                '}';
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }
}
