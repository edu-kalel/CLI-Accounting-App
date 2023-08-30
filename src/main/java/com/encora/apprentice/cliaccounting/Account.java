package com.encora.apprentice.cliaccounting;

import java.util.ArrayList;

public class Account {
    private Account parent;
    private ArrayList<Account> children;
    private String accountName;

    public Account() {
        children = new ArrayList<Account>();
    }

    public Account(String accountName) {
        children = new ArrayList<Account>();
        this.accountName = accountName;
    }
    public Account(Account parent, String accountName){
        this.parent =parent;
        this.accountName = accountName;
        children = new ArrayList<Account>();
    }

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
    public void getStringWithColons(StringBuilder hierarchy){
        if (this.parent!=null){
            hierarchy.insert(0,(this.parent.accountName+":"));
            this.parent.getStringWithColons(hierarchy);
        }
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
