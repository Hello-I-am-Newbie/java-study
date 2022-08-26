package com.onlinejava.project.bookstore;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
                System.out.println("1:title\n2.writer\n3.publisher\n4.price\n5.releaseDate\n6.location");
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
        Map<Integer, Function<Book, String>> map = Map.of(
                1, book -> book.getTitle()
                , 2, book -> book.getWriter()
                , 3, book -> book.getPublisher()
                , 4, book -> book.getPrice().toString()
                , 5, book -> book.getReleaseDate()
                , 6, book -> book.getLocation()
        );

        Predicate<Book> predicate = book -> map.getOrDefault(category, x -> "").apply(book).contains(keyword);
        return list.stream().filter(predicate).collect(Collectors.toUnmodifiableList());
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
