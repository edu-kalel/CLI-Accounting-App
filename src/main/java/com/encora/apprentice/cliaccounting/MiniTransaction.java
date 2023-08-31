package com.encora.apprentice.cliaccounting;

import java.util.ArrayList;

public class MiniTransaction {
    private Account account;
    private Amount amount;
    private ArrayList<Amount> runningTotals;

    public MiniTransaction() {
        this.runningTotals = new ArrayList<Amount>();
    }

    public MiniTransaction(Account account) {
        this.account = account;
        this.runningTotals = new ArrayList<Amount>();
    }

    public MiniTransaction(Account account, Amount amount) {
        this.account = account;
        this.amount = amount;
        this.runningTotals = new ArrayList<Amount>();
    }

    public String toStringPrintCommand(){
        StringBuilder hierarchy = new StringBuilder();
        hierarchy.append(account.getAccountName());
        account.getStringWithColons(hierarchy);
        String format = "    %-40s %11s\n";
        return String.format(format, hierarchy, amount);
//        return "\t"+hierarchy+"\t"+amount+"\n";
    }
    public String toStringRegisterCommand(){
        //TODO: print runningTotals
        StringBuilder hierarchy = new StringBuilder();
//        hierarchy.append("\u001B[35m"); //color
        hierarchy.append(account.getAccountName());
        account.getStringWithColons(hierarchy);
//        hierarchy.insert(0, "\u001B[35m");  //color
//        hierarchy.append("\u001B[0m");   //decolor
        StringBuilder runningTotalsString = new StringBuilder();
        String formatRT = "%18s";
        String formatRTNegative = "\u001B[31m%18s\u001B[0m";
        if (this.runningTotals.get(0).toString().contains("-")){
            runningTotalsString.append(String.format(formatRTNegative, this.runningTotals.get(0)));
        }
        else {
            runningTotalsString.append(String.format(formatRT, this.runningTotals.get(0)));
        }
        for (int i =1; i<runningTotals.size(); i++){
            if (this.runningTotals.get(i).toString().contains("-")){
                runningTotalsString.append("\n").append(" ".repeat(102)).append(String.format(formatRTNegative, this.runningTotals.get(i)));
            }
            else {
                runningTotalsString.append("\n").append(" ".repeat(102)).append(String.format(formatRT, this.runningTotals.get(i)));
            }
        }
//        System.out.println("running totals on mT"+this.runningTotals);
        String amountToString = String.valueOf(amount);
        String format;
        if (amountToString.contains("-")){
            format = "    \u001B[34m%-35s\u001B[0m \u001B[31m%11s\u001B[0m";
        }
        else {
            format = "    \u001B[34m%-35s\u001B[0m %11s";
        }

        String part1 = String.format(format, hierarchy, amount);

//        return String.format(format, hierarchy, amount, runningTotalsString);
        return part1 + runningTotalsString + "\n";
//        return String.format(format, hierarchy, amount, runningTotalsString);

    }

    @Override
    public String toString() {
        return "MiniTransaction{" +
                "account=" + account +
                ", amount=" + amount +
                ", runningTotals=" + runningTotals +
                "}\n";
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

    public ArrayList<Amount> getRunningTotals() {
        return this.runningTotals;
    }

    public void setRunningTotals(ArrayList<Amount> runningTotals) {

//        System.out.println("rt being set: "+runningTotals);
        for (Amount amount1 : runningTotals){
            Amount amount2 = new Amount(amount1);
            this.runningTotals.add(amount2);
        }
//        this.runningTotals = new ArrayList<>(runningTotals);
    }
}
