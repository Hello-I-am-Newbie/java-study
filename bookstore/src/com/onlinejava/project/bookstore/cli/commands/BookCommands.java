package com.onlinejava.project.bookstore.cli.commands;

import com.onlinejava.project.bookstore.Book;
import com.onlinejava.project.bookstore.core.cli.CliCommand;

import java.util.List;
import java.util.stream.Stream;

import static com.onlinejava.project.bookstore.Book.Properties.*;
import static com.onlinejava.project.bookstore.core.cli.CliCommandInterface.bookstore;
import static com.onlinejava.project.bookstore.core.cli.CliCommandInterface.scanner;

@CliCommand
public class BookCommands {
    @CliCommand(ID = "1", title = "Print book list")
    public void printAllBook() {
        bookstore.printAllBook(bookstore.getBookList());
    }

    @CliCommand(ID = "2", title = "Add a new book")
    public void addBook() {
        bookstore.addBook();
    }

    @CliCommand(ID = "3", title = "Search a book")
    public void searchBook() {
        System.out.println("Please select category number to search");
        Stream.of(TITTLE, WRITER, PUBLISHER, PRICE, RELEASEDATE, LOCATION, STOCK)
                .map(Book.Properties::toCategoryString)
                .forEach(System.out::println);
        int categoryNum = Integer.parseInt(scanner.nextLine().trim());

        System.out.println("keyword :");
        String keyword = scanner.nextLine().trim();

        List<Book> list = bookstore.searchBook(categoryNum , keyword);
        bookstore.printAllBook(list);
    }

    @CliCommand(ID = "4", title = "Delete a book")
    public void removeBook() {
        System.out.println("Type TITLE :");
        String title = scanner.nextLine().trim();
        bookstore.removeBook(title, scanner);
    }

    @CliCommand(ID = "5", title = "Buy a book")
    public void buyBook() {
        System.out.println("Type TITLE :");
        String titleToBuy = scanner.nextLine().trim();
        System.out.println("Type customer :");
        String customer = scanner.nextLine().trim();
        bookstore.buyBook(titleToBuy, customer);
    }

    @CliCommand(ID = "7", title = "Add book stock")
    public void addBookStock() {
        System.out.println("Type title : ");
        String titleToAddStock = scanner.nextLine().trim();
        System.out.println("Type stock : ");
        int stock = Integer.parseInt(scanner.nextLine().trim());
        bookstore.addStock(titleToAddStock, stock);
    }
}