package com.encora.apprentice.cliaccounting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Transaction {
    private Date date;
    private ArrayList<MiniTransaction> miniTransactions;
    private ArrayList<String> comments;
    private String description;

    public Transaction() {
        comments = new ArrayList<String>();
        miniTransactions = new ArrayList<MiniTransaction>();
    }
    public void addMiniTransaction(MiniTransaction miniTransaction){
        miniTransactions.add(miniTransaction);
    }
    public void addComment(String comment){
        comments.add(comment);
    }

    public Transaction(Date date, ArrayList<MiniTransaction> miniTransactions, ArrayList<String> comments, String description) {
        this.date = date;
        this.miniTransactions = miniTransactions;
        this.comments = comments;
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<MiniTransaction> getMiniTransactions() {
        return miniTransactions;
    }

    public void setMiniTransactions(ArrayList<MiniTransaction> miniTransactions) {
        this.miniTransactions = miniTransactions;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String toStringPrintCommand(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        StringBuilder result= new StringBuilder();
        for (MiniTransaction miniTransaction:miniTransactions){
            result.append(miniTransaction.toStringPrintCommand());
        }
        return formatter.format(date)+" "+description+
                "\n"+result;
    }
    public String toStringRegisterCommand(){
        SimpleDateFormat formatter = new SimpleDateFormat("yy-MMM-dd");
        StringBuilder result = new StringBuilder();
        result.append(miniTransactions.get(0).toStringRegisterCommand());
        for (int i=1; i<miniTransactions.size();i++){
            result.append(" ".repeat(51)).append(miniTransactions.get(i).toStringRegisterCommand());
        }
        String format ="%-9s \033[0;1m%-40s\u001B[0m %s";
//        String descriptionFormatted =  "\033[0;1m" + description + "\u001B[0m";
//        System.out.println(miniTransactions);
        return String.format(format, formatter.format(date), description, result);
//        return formatter.format(date)+" "+description;
    }

//    @Override
//    public String toString() {
//        return "Transaction{\n" +
//                "date=\n" + date +
//                ", miniTransactions=\n" + miniTransactions +
//                ", comments=\n" + comments +
//                ", description=\n'" + description + '\'' +
//                '}';
//    }
}
