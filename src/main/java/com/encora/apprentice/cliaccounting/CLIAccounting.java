package com.encora.apprentice.cliaccounting;

import picocli.CommandLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;


public class CLIAccounting{

    @CommandLine.Command(name = "kelex", description = "main command",version = "CLI Accounting 0.1", mixinStandardHelpOptions = true,
            subcommands = {
                    RegisterCommand.class,
                    BalanceCommand.class,
                    PrintCommand.class
            })
    static class KelexCommand implements Runnable{

        @CommandLine.Option(names = {"-f", "--file"}, description = "Specify the file", required = true)
        private File file;
        @Override
        public void run() {
//            System.out.println("this command has been brought to u by the kelex gang");
            System.out.println("Parent command");
        }
    }
    @CommandLine.Command(name = "print")
    static class PrintCommand implements Runnable{
        @CommandLine.ParentCommand
        private KelexCommand parent;

        @Override
        public void run() {
            try {
                Journal journal = new Journal(parent.file);
                System.out.println(journal.printCommand());
            } catch (FileNotFoundException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @CommandLine.Command(name = "register", aliases = {"reg"})
    static class RegisterCommand implements Runnable{
        @CommandLine.ParentCommand
        private KelexCommand parent;
        @Override
        public void run() {
            try {
                Journal journal = new Journal(parent.file);
                System.out.println(journal.registerCommand());
            } catch (FileNotFoundException | ParseException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @CommandLine.Command(name = "balance", aliases = {"bal"})
    static class BalanceCommand implements Runnable{
        @CommandLine.ParentCommand
        private KelexCommand parent;
        @Override
        public void run() {
            try {
                Journal journal = new Journal(parent.file);
                System.out.println(journal.balanceCommand());
            } catch (FileNotFoundException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void main(String[] args) {
        new CommandLine(new KelexCommand()).execute(args);
    }
}
