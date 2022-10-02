package com.onlinejava.project.bookstore.application.domain.service;

import com.onlinejava.project.bookstore.application.domain.entity.Book;
import com.onlinejava.project.bookstore.application.ports.input.BookUseCase;
import com.onlinejava.project.bookstore.application.ports.output.BookRepository;
import com.onlinejava.project.bookstore.core.factory.Bean;
import com.onlinejava.project.bookstore.core.factory.Inject;

import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.onlinejava.project.bookstore.application.domain.entity.Book.Properties.valuesToList;

@Bean
public class BookService implements BookUseCase {

    @Inject
    private BookRepository repository;

    public BookService() {}

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Book> getBookList() {
        return repository.findAll();
    }

    @Override
    public void addStock(String titleToAddStock, int stock) {
        getBookList().stream().filter(book -> book.getTitle().equals(titleToAddStock))
                .forEach(book -> book.setStock(book.getStock()+stock));
    }

    @Override
    public void printAllBook(List<Book> bookList){
        System.out.printf("| %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t |%n", "TITLE", "WRITER", "PUBLISHER", "PRICE", "RELEASEDATE", "LOCATION", "STOCK");

        bookList.forEach(i -> System.out.println(i));

    }

    @Override
    public void createBook(Book newBook){
        getBookList().add(newBook);

    }

    @Override
    public List<Book> searchBook(int category,String keyword){

        Predicate<Book> bookPredicate = valuesToList().stream()
                .filter(p -> p.getCategoryNumber() == category)
                .map(p -> p.same(keyword))
                .findFirst().get();
//                .orElseGet(() -> book -> false);

        return getBookList().stream()
                .filter(bookPredicate)
                .collect(Collectors.toUnmodifiableList());

    }

    @Override
    public void removeBook(String title,Scanner scanner){

        System.out.println("List of deleted Books");
        System.out.printf("| %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t |%n", "TITLE", "WRITER", "PUBLISHER", "PRICE", "RELEASEDATE", "LOCATION", "STOCK");
        getBookList().stream()
                .filter(book -> book.getTitle().equals(title))
                .collect(Collectors.toUnmodifiableList())
                .forEach(System.out::println);

        System.out.println("Do you really want to delete?   Y\\N");

        String answer = scanner.nextLine().trim();
        if (answer.equalsIgnoreCase("Y")){
            while(getBookList().stream().filter((book)->book.getTitle().equals(title)).findFirst().isPresent()){
                getBookList().stream()
                        .filter((book)->book.getTitle()
                                .equals(title)).findFirst()
                        .ifPresent(book -> getBookList().remove(book));
            }
            printAllBook(getBookList());
        }else if (answer.equalsIgnoreCase("N")){
            System.out.println("canceled deletion process");
        }else {
            System.out.println("remove Book Error : " + answer);
            System.out.println("canceled deletion process");
        }

    }

}
