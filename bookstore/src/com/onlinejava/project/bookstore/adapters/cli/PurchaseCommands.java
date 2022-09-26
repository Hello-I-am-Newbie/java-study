package com.onlinejava.project.bookstore.adapters.cli;

import com.onlinejava.project.bookstore.application.domain.BookStoreApplication;
import com.onlinejava.project.bookstore.application.domain.BookStoreFactory;
import com.onlinejava.project.bookstore.application.ports.input.PurchaseUseCase;
import com.onlinejava.project.bookstore.core.cli.CliCommand;

import static com.onlinejava.project.bookstore.application.domain.BookStoreApplication.scanner;

@CliCommand
public class PurchaseCommands {
    private PurchaseUseCase service;

    public PurchaseCommands() {
        this.service = BookStoreFactory.lookup(PurchaseUseCase.class);
    }
    @CliCommand(ID = "5", title = "Buy a book")
    public void buyBook() {
        System.out.println("Type TITLE :");
        String titleToBuy = scanner.nextLine().trim();
        System.out.println("Type customer :");
        String customer = scanner.nextLine().trim();
        System.out.println("Type email :");
        String email = scanner.nextLine().trim();
        service.buyBook(titleToBuy, customer, email);
    }
    @CliCommand(ID = "6", title = "Print purchase list")
    public void printPurchaseList() {
        service.getPurchaseList().forEach(System.out::println);
    }

    @CliCommand(ID = "12", title = "Print a user's purchases")
    public void printUsersPurchaseList() {
        System.out.println("Type username : ");
        String usernameToPrintPurchases = scanner.nextLine().trim();
        service.printPurchaseListByUser(usernameToPrintPurchases);
    }

}
