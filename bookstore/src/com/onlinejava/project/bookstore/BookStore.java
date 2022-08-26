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
                getAllBook();
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
                getAllBook(tempList);
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


    public void getAllBook(){
        System.out.printf("| %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t |%n", "TITLE", "WRITER", "PUBLISHER", "PRICE", "RELEASEDATE", "LOCATION");
        this.list.forEach(System.out::println);
    }

    public void getAllBook(List<Book> tempList){
        System.out.printf("| %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t |%n", "TITLE", "WRITER", "PUBLISHER", "PRICE", "RELEASEDATE", "LOCATION");
        tempList.forEach(System.out::println);
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

        List<Book> tempList = new ArrayList<>();


        switch (category){
            case "title":
                tempList = this.list.stream().filter((book)->book.getTitle().contains(keyword)).collect(Collectors.toUnmodifiableList());
                return tempList;
            case "writer":
                tempList =this.list.stream().filter((book)->book.getWriter().contains(keyword)).collect(Collectors.toUnmodifiableList());
                return tempList;
            case "publisher":
                tempList = this.list.stream().filter((book)->book.getPublisher().contains(keyword)).collect(Collectors.toUnmodifiableList());
            return tempList;
            case "price":
                tempList = this.list.stream().filter((book)->book.getPrice().equals(Integer.parseInt(keyword))).collect(Collectors.toUnmodifiableList());
            return tempList;
            case "releaseDate":
                tempList = this.list.stream().filter((book)->book.getReleaseDate().contains(keyword)).collect(Collectors.toUnmodifiableList());
            return tempList;
            case "location":
                tempList = this.list.stream().filter((book)->book.getLocation().contains(keyword)).collect(Collectors.toUnmodifiableList());
                return tempList;
        }

        return tempList;
    }

    private void removeBook(String title,Scanner scanner){
        List<Book> tempList = list.stream()
                        .filter((book)->book.getTitle()
                        .equals(title))
                        .collect(Collectors.toUnmodifiableList());

        System.out.println("List of deleted Books");
        System.out.printf("| %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t |\n", "TITLE", "WRITER", "PUBLISHER", "PRICE", "RELEASEDATE", "LOCATION");
        tempList.forEach(System.out::println);

        System.out.println("Do you really want to delete?   Y\\N");

        String answer = scanner.nextLine().trim();
        if (answer.equalsIgnoreCase("Y")){
            while (list.stream().filter((book) -> book.getTitle().equals(title)).findFirst().isPresent()) {
                list.stream()
                        .filter((book) -> book.getTitle()
                                .equals(title)).findFirst()
                        .ifPresent(book -> this.list.remove(book));
            }

            getAllBook();
        }else if (answer.equalsIgnoreCase("N")){
            System.out.println("canceled deletion process");
        }else {
            System.out.println("Error : " + answer);
            System.out.println("canceled deletion process");
        }

    }
}
