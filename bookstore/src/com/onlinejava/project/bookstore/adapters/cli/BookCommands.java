package com.onlinejava.project.bookstore.adapters.cli;

import com.onlinejava.project.bookstore.application.domain.entity.Book;
import com.onlinejava.project.bookstore.application.ports.input.BookUseCase;
import com.onlinejava.project.bookstore.core.cli.CliCommand;
import com.onlinejava.project.bookstore.core.factory.BeanFactory;

import java.util.List;
import java.util.stream.Stream;

import static com.onlinejava.project.bookstore.application.domain.BookStoreApplication.scanner;
import static com.onlinejava.project.bookstore.application.domain.entity.Book.Properties.*;

@CliCommand
public class BookCommands {
    private BookUseCase service;

    private ConsolePrinter<Book> printer = new ConsolePrinter<>(Book.class);

    public BookCommands() {
        this.service = BeanFactory.getInstance().get(BookUseCase.class);
    }

    @CliCommand(ID = "1", title = "Print book list")
    public void printAllBook() {
        printer.printList(service.getBookList());
    }

    @CliCommand(ID = "2", title = "Add a new book")
    public void addBook() {

        String title = ConsoleUtils.prompt("title");
        String writer = ConsoleUtils.prompt("writer");
        String publisher = ConsoleUtils.prompt("publisher");
        int price = ConsoleUtils.prompt("price", Integer::parseInt);
        String releaseDate = ConsoleUtils.prompt("releaseDate");
        String location = ConsoleUtils.prompt("location");
        int stock = ConsoleUtils.prompt("stock", Integer::parseInt);

        Book book = new Book(title, writer, publisher, price, releaseDate, location, stock);

        service.createBook(book);
    }

    @CliCommand(ID = "3", title = "Search a book")
    public void searchBook() {
        System.out.println("Please select category number to search");
        Stream.of(TITTLE, WRITER, PUBLISHER, PRICE, RELEASEDATE, LOCATION, STOCK)
                .map(Book.Properties::toCategoryString)
                .forEach(System.out::println);
        int categoryNum = Integer.parseInt(scanner.nextLine().trim());

        String keyword = ConsoleUtils.prompt("keyword");

        List<Book> list = service.searchBook(categoryNum , keyword);
        printer.printList(list);
    }

    @CliCommand(ID = "4", title = "Delete a book")
    public void removeBook() {
        String title = ConsoleUtils.prompt("title");
        service.removeBook(title, scanner);
        printer.printList(service.getBookList());
    }


    @CliCommand(ID = "7", title = "Add book stock")
    public void addBookStock() {
        String title = ConsoleUtils.prompt("title");
        int stock = ConsoleUtils.prompt("stock", Integer::parseInt);
        service.addStock(title, stock);

    }

}