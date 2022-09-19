package com.onlinejava.project.bookstore.cli.commands;

import com.onlinejava.project.bookstore.core.cli.CliCommand;

import static com.onlinejava.project.bookstore.Main.*;
import static com.onlinejava.project.bookstore.Main.scanner;

@CliCommand
public class PurchaseCommands {

    @CliCommand(ID = "6", title = "Print purchase list")
    public void printPurchaseList() {
        bookStoreService.printPurchaseList();
    }

    @CliCommand(ID = "12", title = "Print a user's purchases")
    public void printUsersPurchaseList() {
        System.out.println("Type username : ");
        String usernameToPrintPurchases = scanner.nextLine().trim();
        bookStoreService.printPurchaseListByUser(usernameToPrintPurchases);
    }

}
