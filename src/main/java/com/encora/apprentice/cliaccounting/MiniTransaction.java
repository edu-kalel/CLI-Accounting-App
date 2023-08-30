package com.encora.apprentice.cliaccounting;

public class MiniTransaction {
    private Account account;
    private Amount amount;

    public MiniTransaction() {
    }

    public MiniTransaction(Account account) {
        this.account = account;
    }

    public MiniTransaction(Account account, Amount amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public String toString(){
        StringBuilder hierarchy = new StringBuilder();
        hierarchy.append(account.getAccountName());
        account.getStringWithColons(hierarchy);
        return "\t"+hierarchy+"\t"+amount+"\n";
    }


//    @Override
//    public String toString() {
//        return "MiniTransaction{\n" +
//                "account=\n" + account.getAccountName() +
//                ", amount=\n" + amount +
//                '}';
//    }

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
