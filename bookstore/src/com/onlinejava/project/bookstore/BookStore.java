package com.onlinejava.project.bookstore;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookStore {

    private List<Book> list = new ArrayList<>();
    public void printWelcomePage() {
        System.out.println("===============================================================");
        System.out.println("                                                               ");
        System.out.println("                                                               ");
        System.out.println("                   Welcome to Bookstore                        ");
        System.out.println("            ---------------------------------                  ");
        System.out.println("           |                                 |                 ");
        System.out.println("           |       1. Print book list        |                 ");
        System.out.println("           |       0. Quit                   |                 ");
        System.out.println("           |                                 |                 ");
        System.out.println("            ---------------------------------                  ");
        System.out.println("                                                               ");
        System.out.println("                                                               ");
        System.out.println("                                                               ");
        System.out.println("===============================================================");
    } // printWelcomePage

    public void runCommand(Scanner scanner) {
        String command = scanner.nextLine().trim();
        switch (command) {
            case "1":
                list.forEach(i -> System.out.println(i));
                break;
            case "0":
                System.exit(0);
                break;
            default:
                System.out.println("Error : " + command);
        } // switch

    } // runCommand

    public void addList(Book book){
        list.add(book);
    } // addList

} // end class
