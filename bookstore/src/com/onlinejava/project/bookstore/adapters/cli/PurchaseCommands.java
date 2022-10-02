package com.onlinejava.project.bookstore.adapters.cli;

import com.onlinejava.project.bookstore.application.domain.entity.Purchase;
import com.onlinejava.project.bookstore.application.ports.input.PurchaseUseCase;
import com.onlinejava.project.bookstore.core.cli.CliCommand;
import com.onlinejava.project.bookstore.core.factory.BeanFactory;

import static com.onlinejava.project.bookstore.application.domain.BookStoreApplication.scanner;

@CliCommand
public class PurchaseCommands {
    private PurchaseUseCase service;

    private ConsolePrinter<Purchase> printer = new ConsolePrinter<>(Purchase.class);
    public PurchaseCommands() {
        this.service = BeanFactory.getInstance().get(PurchaseUseCase.class);
    }
    @CliCommand(ID = "5", title = "Buy a book")
    public void buyBook() {
        String title = ConsoleUtils.prompt("title");
        String customer = ConsoleUtils.prompt("customer");
        String email = ConsoleUtils.prompt("email");

        service.buyBook(title, customer, email);
    }
    @CliCommand(ID = "6", title = "Print purchase list")
    public void printPurchaseList() {
        printer.printList(service.getPurchaseList());
    }

    @CliCommand(ID = "12", title = "Print a user's purchases")
    public void printUsersPurchaseList() {
        String username = ConsoleUtils.prompt("username");

        service.printPurchaseListByUser(username);
    }

}
