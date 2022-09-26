package com.onlinejava.project.bookstore.application.ports.input;


import com.onlinejava.project.bookstore.application.domain.entity.Book;
import com.onlinejava.project.bookstore.application.domain.entity.Grade;
import com.onlinejava.project.bookstore.application.domain.entity.Member;
import com.onlinejava.project.bookstore.application.domain.entity.Purchase;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.onlinejava.project.bookstore.application.domain.entity.Book.Properties.valuesToList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summarizingInt;

public interface BookUseCase {

    public abstract void addStock(String titleToAddStock, int stock);
    public abstract void printAllBook(List<Book> bookList);

    public abstract void createBook(Book newBook);

    public abstract List<Book> searchBook(int category,String keyword);

    public abstract void removeBook(String title,Scanner scanner);

    public abstract List<Book> getBookList();
}
