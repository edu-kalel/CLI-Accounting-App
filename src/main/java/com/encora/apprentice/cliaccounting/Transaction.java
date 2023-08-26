package com.encora.apprentice.cliaccounting;

import java.util.ArrayList;
import java.util.Date;

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

    @Override
    public String toString() {
        return "Transaction{" +
                "date=" + date +
                ", miniTransactions=" + miniTransactions +
                ", comments=" + comments +
                ", description='" + description + '\'' +
                '}';
    }
}
