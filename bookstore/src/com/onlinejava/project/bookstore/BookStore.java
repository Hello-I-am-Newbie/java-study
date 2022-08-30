package com.onlinejava.project.bookstore;

import com.onlinejava.project.bookstore.Book.Properties;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.onlinejava.project.bookstore.Book.Properties.*;

public class BookStore {

    private List<Book> list;
    private List<Purchase> puchaseList;

    {
        list = new ArrayList<>();
        puchaseList = new ArrayList<>();
        try {
            this.list = Files.lines(Path.of("booklist.csv"))
                    .map(line -> {
                        List<String> book = Arrays.stream(line.split(",")).map(String::trim).collect(Collectors.toList());
                        return new Book(book.get(0), book.get(1), book.get(2), Integer.parseInt(book.get(3)), book.get(4), book.get(5), Integer.parseInt(book.get(6)));
                    }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void printWelcomePage() {
        System.out.println("===============================================================");
        System.out.println("                                                               ");
        System.out.println("                                                               ");
        System.out.println("                   Welcome to Bookstore                        ");
        System.out.println("            ---------------------------------                  ");
        System.out.println("           |                                 |                 ");
        System.out.println("           |       1. Print book list        |                 ");
        System.out.println("           |       2. Add book               |                 ");
        System.out.println("           |       3. Search book            |                 ");
        System.out.println("           |       4. Remove book            |                 ");
        System.out.println("           |       5. Buy book               |                 ");
        System.out.println("           |       6. Print purchase list    |                 ");
        System.out.println("           |       0. Quit                   |                 ");
        System.out.println("           |                                 |                 ");
        System.out.println("            ---------------------------------                  ");
        System.out.println("                                                               ");
        System.out.println("                                                               ");
        System.out.println("                                                               ");
        System.out.println("===============================================================");
    }

    public void runCommand(Scanner scanner) {
        String command = scanner.nextLine().trim();
        switch (command) {
            case "1":
                printAllBook(this.list);
                break;
            case "2":
                addBook();
                break;
            case "3":
                System.out.println("Please select category number to search");
                Stream.of(TITTLE, WRITER, PUBLISHER, PRICE, RELEASEDATE, LOCATION, STOCK)
                        .map(Properties::toCategoryString)
                        .forEach(System.out::println);
                int categoryNum = Integer.parseInt(scanner.nextLine().trim());

                System.out.println("keyword :");
                String keyword = scanner.nextLine().trim();

                List<Book> tempList = searchBook(categoryNum , keyword);
                printAllBook(tempList);
                break;
            case "4":
                System.out.println("Type TITLE :");
                String title = scanner.nextLine().trim();
                removeBook(title, scanner);
                break;
            case "5":
                System.out.println("Type TITLE :");
                String titleToBuy = scanner.nextLine().trim();
                System.out.println("Type customer :");
                String customer = scanner.nextLine().trim();
                buyBook(titleToBuy, customer);
                break;
            case "6":
                printPurchaseList();
                break;
            case "0":
                System.exit(0);
                break;
            default:
                System.out.println("Error : " + command);
        }

    }

    private void buyBook(String titleToBuy, String customer) {

        this.list.stream()
                .filter(book -> book.getTitle().equals(titleToBuy))
                .filter(book -> book.getStock() > 0)
                .forEach(book -> {
                    book.setStock(book.getStock()-1);
                    this.puchaseList.add(
                        new Purchase(titleToBuy, customer, 1)
                    );
                });
    }

    private void printPurchaseList() {
        this.puchaseList.stream()
                .forEach(System.out::println);
    }


    private void printAllBook(List<Book> bookList){
        System.out.printf("| %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t |%n", "TITLE", "WRITER", "PUBLISHER", "PRICE", "RELEASEDATE", "LOCATION", "STOCK");
        bookList.forEach(i -> System.out.println(i));

    }

    // 책 추가
    private void addBook(){
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
        System.out.println("stock > ");
        int stock = Integer.parseInt(scanner.nextLine().trim());


        Book book = new Book(title, writer, publisher, price, releaseDate, location, stock);
        this.list.add(book);

    }

    private List<Book> searchBook(int category,String keyword){

        Predicate<Book> bookPredicate = valuesToList().stream()
                .filter(p -> p.getCategoryNumber() == category)
                .map(p -> p.same(keyword))
                .findFirst()
                .orElseGet(() -> book -> false);

        return list.stream()
                .filter(bookPredicate)
                .collect(Collectors.toUnmodifiableList());

    }

    private void removeBook(String title,Scanner scanner){

        System.out.println("List of deleted Books");
        System.out.printf("| %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t |%n", "TITLE", "WRITER", "PUBLISHER", "PRICE", "RELEASEDATE", "LOCATION", "STOCK");
        list.stream()
                .filter(book -> book.getTitle().equals(title))
                .collect(Collectors.toUnmodifiableList())
                .forEach(System.out::println);

        System.out.println("Do you really want to delete?   Y\\N");

        String answer = scanner.nextLine().trim();
        if (answer.equalsIgnoreCase("Y")){
            while(list.stream().filter((book)->book.getTitle().equals(title)).findFirst().isPresent()){
                list.stream()
                        .filter((book)->book.getTitle()
                        .equals(title)).findFirst()
                        .ifPresent(book -> this.list.remove(book));
            }
            printAllBook(this.list);
        }else if (answer.equalsIgnoreCase("N")){
            System.out.println("canceled deletion process");
        }else {
            System.out.println("remove Book Error : " + answer);
            System.out.println("canceled deletion process");
        }

    }
}
