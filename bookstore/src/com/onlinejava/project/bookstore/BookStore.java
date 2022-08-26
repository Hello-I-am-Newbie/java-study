package com.onlinejava.project.bookstore;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
                printAllBook();
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

                List<Book> tempList = searchBook(selectCategory(categoryNum) , keyword);
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


    public void printAllBook(){
        System.out.printf("| %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t |%n", "TITLE", "WRITER", "PUBLISHER", "PRICE", "RELEASEDATE", "LOCATION");
        this.list.forEach(System.out::println);
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
        this.list.add(book);

    }

    public String selectCategory(int categoryNum){
        switch (categoryNum){
            case 1:
                return "title";
            case 2:
                return "writer";
            case 3:
                return "publisher";
            case 4:
                return "price";
            case 5:
                return "releaseDate";
            case 6:
                return "location";
            default:
                return "Error : " + categoryNum;

        }

    }

    public List<Book> searchBook(String category,String keyword){

        List<Book> bookList = new ArrayList<>();


        switch (category){
            case "title":
                bookList = this.list.stream().filter((book)->book.getTitle().contains(keyword)).collect(Collectors.toUnmodifiableList());
                return bookList;
            case "writer":
                bookList =this.list.stream().filter((book)->book.getWriter().contains(keyword)).collect(Collectors.toUnmodifiableList());
                return bookList;
            case "publisher":
                bookList = this.list.stream().filter((book)->book.getPublisher().contains(keyword)).collect(Collectors.toUnmodifiableList());
            return bookList;
            case "price":
                bookList = this.list.stream().filter((book)->book.getPrice().equals(Integer.parseInt(keyword))).collect(Collectors.toUnmodifiableList());
            return bookList;
            case "releaseDate":
                bookList = this.list.stream().filter((book)->book.getReleaseDate().contains(keyword)).collect(Collectors.toUnmodifiableList());
            return bookList;
            case "location":
                bookList = this.list.stream().filter((book)->book.getLocation().contains(keyword)).collect(Collectors.toUnmodifiableList());
                return bookList;
        }

        return bookList;
    }

    private void removeBook(String title, Scanner scanner){
        List<Book> bookLit = list.stream()
                        .filter((book)->book.getTitle()
                        .equals(title))
                        .collect(Collectors.toUnmodifiableList());

        System.out.println("List of deleted Books");
        System.out.printf("| %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t |\n", "TITLE", "WRITER", "PUBLISHER", "PRICE", "RELEASEDATE", "LOCATION");
        bookLit.forEach(System.out::println);

        System.out.println("Do you really want to delete?   Y\\N");

        String answer = scanner.nextLine().trim();
        if (answer.equalsIgnoreCase("Y")){
            list.removeIf(book -> book.getTitle().equals(title));
            printAllBook();
        }else if (answer.equalsIgnoreCase("N")){
            System.out.println("canceled deletion process");
        }else {
            System.out.println("Error : " + answer);
            System.out.println("canceled deletion process");
        }

    }
}
