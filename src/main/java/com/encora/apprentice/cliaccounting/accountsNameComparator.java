package com.encora.apprentice.cliaccounting;

import java.util.Comparator;

public class accountsNameComparator implements Comparator<Account> {
    @Override
    public int compare(Account a1, Account a2) {
        return a1.getAccountName().compareTo(a2.getAccountName());
    }
}