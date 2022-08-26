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
        System.out.println("           |       2. add book               |                 ");
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
                getAllBook();
                break;
            case "2":
                addBook();
                break;
            case "0":
                System.exit(0);
                break;
            default:
                System.out.println("Error : " + command);
        } // switch

    } // runCommand


    // 모든 책 조회
    public void getAllBook(){
        System.out.println(
                String.format("| %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t |", "TITLE", "WRITER", "PUBLISHER", "PRICE", "RELEASEDATE", "LOCATION")
        );
        list.forEach(i -> System.out.println(i));
    }

    // 책 추가
    public void addBook(){
        Scanner scanner = new Scanner(System.in);
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


        Book book = new Book(title, writer, publisher, price, releaseDate, location);
        list.add(book);

    } // addList

} // end class
