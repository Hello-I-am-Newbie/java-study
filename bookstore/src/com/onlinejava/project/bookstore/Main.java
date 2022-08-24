package com.onlinejava.project.bookstore;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args){

        BookStore bookStore = new BookStore();
        Book book1 = new Book("TITLE1","WRITER1","PUBLISHER1","10000","2022-08-23","Korea");
        Book book2 = new Book("TITLE2","WRITER2","PUBLISHER2","10000","2022-08-23","Korea");
        Book book3 = new Book("TITLE3","WRITER3","PUBLISHER3","10000","2022-08-23","Korea");

        bookStore.addList(book1);
        bookStore.addList(book2);
        bookStore.addList(book3);

        while(true){
            bookStore.printWelcomePage();
            bookStore.runCommand(scanner);
        } // while

    }  // main

} // end class
