package com.encora.apprentice.cliaccounting;

import java.util.ArrayList;
import java.util.Collections;

public class Account {
    private Account parent;
    private ArrayList<Account> children;
    private String accountName;
    private ArrayList<Amount> amounts;
    private boolean balanceHasBeenCalc;

    public Account() {
        children = new ArrayList<Account>();
        amounts = new ArrayList<Amount>();
        balanceHasBeenCalc = false;
    }

    public Account(String accountName) {
        children = new ArrayList<Account>();
        amounts = new ArrayList<Amount>();
        balanceHasBeenCalc = false;
        this.accountName = accountName;
    }
    public Account(Account parent, String accountName){
        this.parent =parent;
        this.accountName = accountName;
        balanceHasBeenCalc = false;
        children = new ArrayList<Account>();
        amounts = new ArrayList<Amount>();
    }
    public void addAmount(Amount amount){
        boolean existsAmountForUnit = false;
        for (Amount existingAmount : amounts){
            if (existingAmount.getUnit().equals(amount.getUnit())){
                existingAmount.addToAmount(amount.getAmount());
                existsAmountForUnit=true;
                break;
            }
        }
        if (!existsAmountForUnit){
            Amount newAmountForUnit = new Amount(amount.getAmount(), amount.getUnit());
            amounts.add(newAmountForUnit);
        }
    }
    public ArrayList<Amount> getAccountBalance(){
        //
        if (balanceHasBeenCalc)
            return getAmounts();
        //
        if (!children.isEmpty()){
            for (Account child:children){
                ArrayList<Amount> childAmounts = new ArrayList<Amount>(child.getAccountBalance());
                for (Amount childAmount:childAmounts){
                    this.addAmount(childAmount);
                }
            }
            balanceHasBeenCalc=true;
            return this.amounts;
        }
        else {
            balanceHasBeenCalc=true;
            return getAmounts();
        }
    }
//    public String toStringBalanceCommand(){
//        if (children.size()==1){
//            //account:child
//        }
//        else if (children.isEmpty()){
//            //account
//        }
//        else {
//
//        }
//    }

    public void addChild(Account childAccount){
        children.add(childAccount);
    }

    public Account getParent() {
        return parent;
    }

    public void setParent(Account parent) {
        this.parent = parent;
    }

    public ArrayList<Account> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Account> children) {
        this.children = children;
    }

    public String getAccountName() {
//        if (this.parent==null)
//            return "no parent jaja";    //probably refactor or DELETE this
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    public String getChildrenNames(){
        StringBuilder names= new StringBuilder();
        for (Account child : children) {
            names.append(child.getAccountName());
        }
        return names.toString();
    }
    public String printTreeFromParent(int tab){
        StringBuilder tree = new StringBuilder();
        tree.append("\n");
        tree.append("\t".repeat(Math.max(0, tab)));
        tree.append(this.accountName);
        tab++;
        for(Account child : children){
            tree/*.append("\n\t")*/.append(child.printTreeFromParent(tab));
        }
        tree.append("\n");
        return String.valueOf(tree.toString());
    }
    public String balanceCommand(int tab){
        Collections.sort(children, new accountsNameComparator());
        StringBuilder result = new StringBuilder();
        StringBuilder tabul = new StringBuilder();
        tabul.append(("  ").repeat(tab));
        String format;
        if (this.getChildren().size()==1){       //solo tiene un child
            if (this.getAccountBalance().size()==1) {        //solo tiene 1 tipo de moneda
                if (this.getAccountBalance().get(0).toString().contains("-")){
                    format = "\u001B[31m%20s  %s\u001B[34m%s\u001B[0m\n";
                } else {
                    format = "%20s  %s\u001B[34m%s\u001B[0m\n";
                }
                result.append(String.format(format, this.getAccountBalance().get(0), tabul,(this.getAccountName()+":"+this.getChildren().get(0).getAccountName())));
            }
            else if (this.getAccountBalance().size()>1){                                              //mas de 1 tipo de moneda
                for (int i=0; i<this.getAccountBalance().size()-1; i++){
                    if (this.getAccountBalance().get(0).toString().contains("-")){
                        format = "\u001B[31m%20s\u001B[0m\n";
                    } else {
                        format = "%20s\n";
                    }
                    result.append(String.format(format, this.getAccountBalance().get(i)));
                }
                if (this.getAccountBalance().get(this.getAccountBalance().size()-1).toString().contains("-")){
                    format = "\u001B[31m%20s  %s\u001B[34m%s\u001B[0m\n";
                } else {
                    format = "%20s  %s\u001B[34m%s\u001B[0m\n";
                }
                result.append(String.format(format, this.getAccountBalance().get(this.getAccountBalance().size()-1), tabul, (this.getAccountName()+":"+this.getChildren().get(0).getAccountName())));
            }
        }
        else/* if (this.getChildren().size()>1)*/{       //children!=1
//                result.append("work in progress, hang in there\n");
            if (this.getAccountBalance().size()==1) {        //solo tiene 1 tipo de moneda
                if (this.getAccountBalance().get(0).toString().contains("-")){
                    format = "\u001B[31m%20s  %s\u001B[34m%s\u001B[0m\n";
                } else {
                    format = "%20s  %s\u001B[34m%s\u001B[0m\n";
                }
                result.append(String.format(format, this.getAccountBalance().get(0), tabul, this.getAccountName()));
            }
            else if (this.getAccountBalance().size()>1){                                              //mas de 1 tipo de moneda
                for (int i=0; i<this.getAccountBalance().size()-1; i++){
                    if (this.getAccountBalance().get(0).toString().contains("-")){
                        format = "\u001B[31m%20s\u001B[0m\n";
                    } else {
                        format = "%20s\n";
                    }
                    result.append(String.format(format, this.getAccountBalance().get(i)));
                }
                if (this.getAccountBalance().get(this.getAccountBalance().size()-1).toString().contains("-")){
                    format = "\u001B[31m%20s  %s\u001B[34m%s\u001B[0m\n";
                } else {
                    format = "%20s  %s\u001B[34m%s\u001B[0m\n";
                }
                result.append(String.format(format, this.getAccountBalance().get(this.getAccountBalance().size()-1), tabul, this.getAccountName()));
            }
            tab++;
            for (Account childAccount: children){
                result.append(childAccount.balanceCommand(tab));
            }
        }
//            result.append(this.toStringBalanceCommand());
        return String.valueOf(result);
    }
    public void getStringWithColons(StringBuilder hierarchy){
        if (this.parent!=null){
            hierarchy.insert(0,(this.parent.accountName+":"));
            this.parent.getStringWithColons(hierarchy);
        }
    }

    public ArrayList<Amount> getAmounts() {
        return amounts;
    }

    public void setAmounts(ArrayList<Amount> amounts) {
        this.amounts = amounts;
    }

    @Override
    public String toString() {
//        return "Account{" +
////                "parent=" + parent.getAccountName() +
//
//                ", account=\n'" + accountName + '\'' +
//                ", children=\n" + getChildrenNames() +
//                '}';
        return printTreeFromParent(0);
    }
    //    private
}
