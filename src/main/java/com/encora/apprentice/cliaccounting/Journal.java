package com.encora.apprentice.cliaccounting;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Journal {                          //in this journal, all transactions will be stored
    private ArrayList<Transaction> journal;     //array with all the transactions

    public Journal(File file) throws FileNotFoundException, ParseException {    //we receive the initial file, that could be 'index'
        lineReader(file);
    }

    public /*Transaction*/ void lineReader(File file) throws FileNotFoundException, ParseException {        //recursive
//        Scanner reader = new Scanner(line);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Scanner reader = new Scanner(file);                     //create the reader for the file
        String next = reader.next();                        //store the next token in next string
        while (reader.hasNext()){                               //loop through each token of the file
            if (next.equals("!include")) {                      //if it is an 'include'...
                System.out.println("archivo a indexar: " + reader.next());
                next= reader.next();;
            }
            else if (next./*equals(";")*/matches("[;#%|*]")) {     //check if it is a comment
                    System.out.println("comment" + reader.nextLine());  //aqui imprime el comment COMMENTS GLOBALES NO SE ALMACENAN EJEJEJE
                    next = reader.next();
                }
            else if (next.matches("\\d{4}/\\d{2}/\\d{2}")){         //caso d que see el REAL real deal
                    Transaction transaction = new Transaction();                //creamos nueva transaccion
                    Date date = formatter.parse(next);                          //formatear correctamente la fecha
                    transaction.setDate(date);                                  //set transaction date
//                    System.out.println("ESTO ES UNA FECHA: "+next);             //dbug
                    transaction.setDescription(reader.nextLine());              //set Description
                    String nextLine = reader.nextLine();                    //line con la que vamos a trabajar en esta iteración
                    do {
                        if (nextLine.contains(";")){                            //si es un comment
                            transaction.addComment(nextLine);                   //add to transaction comments
                        }
                        else{
                            nextLine=nextLine.strip();
                            String[] elements = nextLine.split("(?=([-\\d$]))",2);
                            System.out.println("elements:");
                            for (String a : elements)
                                System.out.println("\t"+a);
                        }
                        if (reader.hasNext()) {
                            nextLine = reader.nextLine();
//                            System.out.println("este es el next we: " + next);
                        }
                    } while ((!next.matches("\\d{4}/\\d{2}/\\d{2}"))&(reader.hasNext()));

//                    System.out.println("date: " + next);
//                    System.out.println("description" + reader.nextLine());

            }
        }
    }

}
