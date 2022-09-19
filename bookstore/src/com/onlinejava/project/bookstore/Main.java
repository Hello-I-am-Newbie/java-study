package com.onlinejava.project.bookstore;

import com.onlinejava.project.bookstore.domain.model.Book;
import com.onlinejava.project.bookstore.domain.model.Member;
import com.onlinejava.project.bookstore.domain.model.Purchase;
import com.onlinejava.project.bookstore.domain.service.BookStoreService;

import java.util.Scanner;


public class Main {

    public static final boolean HAS_HEADER = true;
    public static final String COMMAND_PACKAGE = "com.onlinejava.project.bookstore.cli.commands";
    public static final Scanner scanner = new Scanner(System.in);
    public static BookStoreService bookStoreService = BookStoreService.getInstance();
    public static void main(String[] args){

        bookStoreService.setBookList(bookStoreService.getModelListFromLines("booklist.csv", Book.class));
        bookStoreService.setMemberList(bookStoreService.getModelListFromLines("memberlist.csv", Member.class));
        bookStoreService.setPurchaseList(bookStoreService.getModelListFromLines("purchaselist.csv", Purchase.class));

        while(true){
            bookStoreService.printWelcomePage();
            bookStoreService.runCommand(scanner);
        } // while

    }  // main

} // end class
