package com.encora.apprentice.cliaccounting;

import picocli.CommandLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;


public class CLIAccounting{

    @CommandLine.Command(name = "kelex", description = "main command",version = "CLI Accounting 0.1", mixinStandardHelpOptions = true,
            subcommands = {
                    RegisterCommand.class,
                    BalanceCommand.class
            })
    static class KelexCommand implements Runnable{

        @CommandLine.Option(names = {"-f", "--files"}, description = "Specify the file", required = true)
        private File file;
        @Override
        public void run() {
//            System.out.println("this command has been brought to u by the kelex gang");
            System.out.println("Parent command");
            try {
                Journal journal = new Journal(file);
            } catch (FileNotFoundException | ParseException e) {
                throw new RuntimeException(e);
            }
//            try {
//                Scanner reader = new Scanner(file);
//                while (reader.hasNextLine()){
//                    String data = reader.nextLine();
//                    System.out.println(data);
//                }
//                reader.close();
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            }

        }
    }
    @CommandLine.Command(name = "register", aliases = {"reg"})
    static class RegisterCommand implements Runnable{
        @CommandLine.ParentCommand
        private KelexCommand parent;
        @Override
        public void run() {
//            System.out.println("so you have chosen REGISTER MAH BOOOOIIII");
            System.out.println("So you have chosen the subcommand REGISTER");
        }
    }

    @CommandLine.Command(name = "balance", aliases = {"bal"})
    static class BalanceCommand implements Runnable{
        @CommandLine.ParentCommand
        private KelexCommand parent;
        @Override
        public void run() {
//            System.out.println("So you have chosen BALANCE YEAAAAHHHH BOOOOOIIIII");
            System.out.println("So you have chosen the subcommand BALANCE");
        }
    }
    public static void main(String[] args) {
//        int exitCode = new CommandLine(new CLIAccounting()).execute(args);
//        System.exit(exitCode);
        new CommandLine(new KelexCommand()).execute(args);
    }
}
