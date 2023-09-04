package com.encora.apprentice.cliaccounting;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Journal {                          //in this journal, all transactions will be stored
    //TODO: Journal is pretty much done (?????), maybe prettify the toString
    //TODO: Noooo, checar si ya existe account  DOOOOOOOOONEEEE
    private ArrayList<Transaction> transactions;     //array with all the transactions
    private ArrayList<Account> accounts;             //existing accounts
    private ArrayList<Amount> runningTotals;

    public Journal(File file) throws FileNotFoundException, ParseException {    //we receive the initial file, that could be 'index'
        transactions = new ArrayList<Transaction>();
        accounts = new ArrayList<Account>();
        runningTotals = new ArrayList<Amount>();
        fileReader(file);
    }
    public String printCommand(){
        StringBuilder result = new StringBuilder();
        for (Transaction transaction : transactions){
            result.append(transaction.toStringPrintCommand());
        }
        return String.valueOf(result);
    }
    public String printCommand(ArrayList<String> parameters){
        StringBuilder result = new StringBuilder();
        for (Transaction transaction: transactions){
            String transactionWF = transaction.toStringPrintCommand();
            for (String parameter:parameters){
                if (transactionWF.contains(parameter)){
                    result.append(transactionWF);
                    break;
                }
            }
        }
        return String.valueOf(result);
    }
    public String balanceCommand(){
        //TODO: ya funciona cuando es 1 cuenta con 1 hijo y con 1 tipo de amount, implementar los demás + 1 nuevo running total para el bal
        StringBuilder result = new StringBuilder();
        Collections.sort(accounts, new accountsNameComparator());
        String format;
        for (Account account:accounts){
            result.append(account.balanceCommand(0));
        }
        result.append("-".repeat(20)).append("\n");
        for (Amount amount:runningTotals){
            if (amount.toString().contains("-"))
                format="\u001B[31m%20s\u001B[0m\n";
            else
                format="%20s\n";
            result.append(String.format(format, amount.toString()));
        }
        return String.valueOf(result);
    }
    public String registerCommand(){
        StringBuilder result = new StringBuilder();
        for (Transaction transaction:transactions){
            result.append(transaction.toStringRegisterCommand());
        }
        return String.valueOf(result);
    }
    public String registerCommand(ArrayList<String> parameters){
        StringBuilder result = new StringBuilder();

        for (Transaction transaction: transactions){
            String transactionWF = transaction.toStringRegisterCommand(parameters);
            for (String parameter:parameters){
                if (transactionWF.contains(parameter)){
                    result.append(transactionWF);
                    break;
                }
            }
        }

//        for (Transaction transaction:transactions){
//            result.append(transaction.toStringRegisterCommand(parameters));
//        }
        return String.valueOf(result);
    }

    @Override
    public String toString() {
        return "Journal{\n" +
                "transactions=\n" + transactions +
                ", accounts=\n" + accounts +
                "number of registered transactions: "+transactions.size()+
//                "\n which are: "++
                "number of accounts: " + accounts.size()+
                '}';
    }

    public void fileReader(File file) throws FileNotFoundException, ParseException {        //recursive
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Scanner reader = new Scanner(file);                     //create the reader for the file
        String nextLine = reader.nextLine();                        //store the next Line in next string
        while (reader.hasNextLine()||!nextLine.isBlank()){                               //loop through each line of the file
            if (nextLine.contains("!include")) {                      //if it is an 'include'...
                //TODO: fix last file not being indexed, problem is in while->nextLine DOOOOOOOOONEEEE
                String[] elements = nextLine.split("\\s", 2);
//                System.out.println("archivo a indexar: " + elements[1]);
                File fileToIndex = new File(elements[1]);
                fileReader(fileToIndex);
//                if (reader.hasNextLine())
//                    nextLine= reader.nextLine();
                if (reader.hasNextLine())
                    nextLine = reader.nextLine();
                else{
                    nextLine="";
                }
            }
            else if (nextLine./*equals(";")*/matches("[;#%|*]\\s.*")) {     //check if it is a comment
                    String[] elements = nextLine.split("\\s", 2);
//                    System.out.println("comment" + elements[1]);  //aqui imprime el comment COMMENTS GLOBALES NO SE ALMACENAN EJEJEJE
//                    nextLine = reader.nextLine();
                    if (reader.hasNextLine())
                        nextLine = reader.nextLine();
                    else{
                        nextLine="";
                    }
                }
            else if (nextLine.matches("\\d{2,4}/\\d{1,2}/\\d{1,2}\\s.*")){         //caso d que see el REAL real deal
                    String[] elements = nextLine.split("\\s", 2);
                    Transaction transaction = new Transaction();                //creamos nueva transaccion
                    Amount runningAmountJustInCase = new Amount(0);              //in case there's a miniT with no amount
                    Date date = formatter.parse(elements[0]);                          //formatear correctamente la fecha
                    transaction.setDate(date);                                  //set transaction date
//                    System.out.println("ESTO ES UNA FECHA: "+next);             //dbug
                    transaction.setDescription(elements[1]);              //set Description
                    nextLine = reader.nextLine();                    //line con la que vamos a trabajar en esta iteración
                    do {
                        if (nextLine.contains(";")){                            //si es un comment
                            transaction.addComment(nextLine);                   //add to transaction comments
                        }
                        else{                                                   //aqui ya va el mov para crear la miniT
                            nextLine=nextLine.strip();                          //strip
                            elements = nextLine.split("(?=([-\\d$]))",2);   //separar, account & amount
                            String[] elementsForAccount = elements[0].split(":", 2);    //separar parent account &children
                            //TODO check if account already exists  DOOOOOOOOONEEEE
                            boolean accountExists = false;              //flag to indicate if the account to be created already exists in the journal
                            MiniTransaction miniTransaction = null;
                            for (Account existing : accounts){          //recorrer existing accounts to check if the parent account already exists
//                                System.out.println(existing.getAccountName());
                                if (existing.getAccountName().equals(elementsForAccount[0])){       //the account already exists
//                                    System.out.println("la cuenta: "+existing.getAccountName()+"ya existe");
                                    accountExists=true;
                                    miniTransaction = new MiniTransaction(createAccountTree(elementsForAccount[1], existing));
                                    break;
                                }
                            }
                            if (!accountExists) {           //if the parent account doesn't exist already
                                Account parent = new Account(elementsForAccount[0]);    //crear parent account
                                accounts.add(parent);                                   //add parent account to journal
                                miniTransaction = new MiniTransaction(createAccountTree(elementsForAccount[1], parent));
                            }
                            Amount amount = new Amount();
                            Amount runningTotal = new Amount();
                            if (elements.length>1){
                                if (elements[1].contains("$")) {
                                    amount.setUnit("$");
                                    runningAmountJustInCase.setUnit("$");
                                    String[] sub = elements[1].split("\\$");
//                                    System.out.println("AQUIIIIIIIIIIII\t"+sub.length);
//                                    for (String a : sub)
//                                        System.out.println(a);
                                    if (!sub[0].isBlank()) {
                                        amount.setAmount((Float.parseFloat(sub[1])) * (-1));
                                        runningAmountJustInCase.addToAmount((Float.parseFloat(sub[1])) * (-1));
                                    }
                                    else {
                                        amount.setAmount(Float.parseFloat(sub[1]));
                                        runningAmountJustInCase.addToAmount(Float.parseFloat(sub[1]));
                                    }
                                } else {
                                    //TODO: split on whitespace, 0 is amount and 1 is unit  DOOOOOOOOONEEEE
                                    String[] elementsForAmount = elements[1].split("\\s");
                                    amount.setUnit(elementsForAmount[1]);
                                    amount.setAmount(Float.parseFloat(elementsForAmount[0]));
                                    runningAmountJustInCase.setUnit(elementsForAmount[1]);
                                    runningAmountJustInCase.addToAmount(Float.parseFloat(elementsForAmount[0]));
                                }
                            }
                            else {//TODO: when at the end of the transaction, the last miniT doesn't have amount    DOOOOOOOOONEEEE
                                amount.setAmount((runningAmountJustInCase.getAmount())*(-1));
                                amount.setUnit(runningAmountJustInCase.getUnit());
                            }

                            //running total for reg
                            boolean existsRunningTotal = false;
                            for (Amount existingRunningTotal:runningTotals){
                                if (existingRunningTotal.getUnit().equals(amount.getUnit())){
                                    existingRunningTotal.addToAmount(amount.getAmount());
                                    existsRunningTotal = true;
                                    break;
                                }
                            }
                            if (!existsRunningTotal){
                                Amount newRunningTotal = new Amount(amount.getAmount(), amount.getUnit());
                                runningTotals.add(newRunningTotal);
                            }
//                            ArrayList<Amount> copyOfRT = new ArrayList<Amount>(runningTotals);
                            //continue building and adding miniT
                            miniTransaction.setAmount(amount);
//                            System.out.println("runnnig totals added: "+runningTotals);
                            miniTransaction.setRunningTotals(runningTotals);
                            transaction.addMiniTransaction(miniTransaction);
//                            System.out.println(miniTransaction);
//                            transactions.add(transaction);
//                            createAccountTree(elementsForAccount[1], parent);       //create account tree
//                            System.out.println(parent.printTreeFromParent());       //print tree (?) masomeno
                        }
                        if (reader.hasNextLine())
                            nextLine = reader.nextLine();
                        else{
                            nextLine="";
                            break;
                        }
                    } while ((!nextLine.matches("\\d{2,4}/\\d{1,2}/\\d{1,2}\\s.*"))/*&(reader.hasNextLine())*/);
                    transactions.add(transaction);
            }
        }
    }
    public Account createAccountTree(String accountTree, Account parent){   //creates the account tree from the parent account, returns the 'youngest'(?) (last child added(?))
        accountTree=accountTree.strip();
        String[] elements = accountTree.split(":", 2);
//        boolean childExists = false;
        for (int i=0; i<parent.getChildren().size(); i++){
            if (parent.getChildren().get(i).getAccountName().equals(elements[0])){
//                childExists=true;
                if (elements.length>1){
                    return createAccountTree(elements[1], parent.getChildren().get(i));
                }
                else {
                    return parent.getChildren().get(i);
                }
            }
        }
        Account account = new Account(parent, elements[0]);
        parent.addChild(account);
        if (elements.length > 1) {
            return createAccountTree(elements[1], account);
        } else {return account;}
    }
}
