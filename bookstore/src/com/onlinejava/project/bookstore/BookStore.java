package com.onlinejava.project.bookstore;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.onlinejava.project.bookstore.Book.Properties.*;

public class BookStore {

    private List<Book> list = new ArrayList<>();

    private static boolean test(Book i) {
        return false;
    }

    public void printWelcomePage() {
        System.out.println("===============================================================");
        System.out.println("                                                               ");
        System.out.println("                                                               ");
        System.out.println("                   Welcome to Bookstore                        ");
        System.out.println("            ---------------------------------                  ");
        System.out.println("           |                                 |                 ");
        System.out.println("           |       1. Print book list        |                 ");
        System.out.println("           |       2. add book               |                 ");
        System.out.println("           |       3. search book            |                 ");
        System.out.println("           |       4. remove book            |                 ");
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
                printAllBook(list);
                break;
            case "2":
                addBook();
                break;
            case "3":
                System.out.println("Please select category number to search");

                List.of(TITTLE, WRITER, PUBLISHER, PRICE, RELEASEDATE, LOCATION).stream()
                        .map(Book.Properties::toCategoryString)
                        .forEach(System.out::println);
                int categoryNum = Integer.parseInt(scanner.nextLine().trim());

                System.out.println("keyword :");
                String keyword = scanner.nextLine().trim();

                List<Book> tempList = searchBook(categoryNum, keyword);
                printAllBook(tempList);
                break;
            case "4":
                System.out.println("Type TITLE :");
                String title = scanner.nextLine().trim();
                removeBook(title, scanner);
                break;
            case "0":
                System.exit(0);
                break;
            default:
                System.out.println("Error : " + command);
        }

    }

    public void printAllBook(List<Book> bookList){
        System.out.printf("| %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t |%n", "TITLE", "WRITER", "PUBLISHER", "PRICE", "RELEASEDATE", "LOCATION");
        bookList.forEach(System.out::println);
    }

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

    }

    public List<Book> searchBook(int category,String keyword){
        Predicate<Book> bookPredicate = valuesToList().stream()
                .filter(p -> p.getCategoryNumber() == category)
                .map(p -> p.same(keyword))
                .findFirst()
                .orElseGet(() -> book -> false);

        return list.stream()
                .filter(bookPredicate)
                .collect(Collectors.toUnmodifiableList());
    }

    private void removeBook(String title, Scanner scanner){

        System.out.println("List of deleted Books");
        System.out.printf("| %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t |\n", "TITLE", "WRITER", "PUBLISHER", "PRICE", "RELEASEDATE", "LOCATION");
        list.stream()
            .filter(book -> book.getTitle().equals(title))
            .collect(Collectors.toUnmodifiableList())
            .forEach(System.out::println);

        System.out.println("Do you really want to delete?   Y\\N");

        String answer = scanner.nextLine().trim();
        if (answer.equalsIgnoreCase("Y")){
            list.removeIf(book -> book.getTitle().equals(title));
            printAllBook(list);
        }else if (answer.equalsIgnoreCase("N")){
            System.out.println("canceled deletion process");
        }else {
            System.out.println("Error : " + answer);
            System.out.println("canceled deletion process");
        }

    }
}
