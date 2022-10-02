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

    public BookCommands() {
        this.service = BeanFactory.getInstance().get(BookUseCase.class);
    }

    @CliCommand(ID = "1", title = "Print book list")
    public void printAllBook() {
        service.printAllBook(service.getBookList());
    }

    @CliCommand(ID = "2", title = "Add a new book")
    public void addBook() {

        System.out.println("title > ");
        String title = scanner.nextLine().trim();
        System.out.println("writer > ");
        String writer = scanner.nextLine().trim();
        System.out.println("publisher > ");
        String publisher = scanner.nextLine().trim();
        System.out.println("price > ");
        int price = Integer.parseInt(scanner.nextLine().trim());
        System.out.println("releaseDate > ");
        String releaseDate = scanner.nextLine().trim();
        System.out.println("location > ");
        String location = scanner.nextLine().trim();
        System.out.println("stock > ");
        int stock = Integer.parseInt(scanner.nextLine().trim());

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

        System.out.println("keyword :");
        String keyword = scanner.nextLine().trim();

        List<Book> list = service.searchBook(categoryNum , keyword);
        service.printAllBook(list);
    }

    @CliCommand(ID = "4", title = "Delete a book")
    public void removeBook() {
        System.out.println("Type TITLE :");
        String title = scanner.nextLine().trim();
        service.removeBook(title, scanner);
    }


    @CliCommand(ID = "7", title = "Add book stock")
    public void addBookStock() {
        System.out.println("Type title : ");
        String titleToAddStock = scanner.nextLine().trim();
        System.out.println("Type stock : ");
        int stock = Integer.parseInt(scanner.nextLine().trim());
        service.addStock(titleToAddStock, stock);
    }
}