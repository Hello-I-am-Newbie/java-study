package com.onlinejava.project.bookstore;

import com.onlinejava.project.bookstore.Book.Properties;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.onlinejava.project.bookstore.Book.Properties.*;

public class BookStore {

    private List<Book> list;
    private List<Purchase> purchaseList;
    private List<Member> memberlist;

    {
        list = new ArrayList<>();
        purchaseList = new ArrayList<>();
        memberlist = new ArrayList<>();

        try {
            this.list = Files.lines(Path.of("booklist.csv"))
                    .map(line -> {
                        List<String> book = Arrays.stream(line.split(",")).map(String::trim).collect(Collectors.toList());
                        return new Book(book.get(0), book.get(1), book.get(2), Integer.parseInt(book.get(3)), book.get(4), book.get(5), Integer.parseInt(book.get(6)));
                    }).collect(Collectors.toList());

            this.purchaseList = Files.lines(Path.of("purchaselist.csv"))
                    .map(line -> {
                        List<String> purchase = Arrays.stream(line.split(",")).map(String::trim).collect(Collectors.toList());
                        return new Purchase(purchase.get(0), purchase.get(1), Integer.parseInt(purchase.get(2)));
                    }).collect(Collectors.toList());

            this.memberlist = Files.lines(Path.of("memberlist.csv"))
                    .map(line -> {
                        List<String> member = Arrays.stream(line.split(",")).map(String::trim).collect(Collectors.toList());
                        return new Member(member.get(0), member.get(1), member.get(2));
                    }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }



//        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("booklist.csv")));){
//            this.list = reader.lines().map(line -> {
//                    List<String> book = Arrays.stream(line.split(",")).map(String::trim).collect(Collectors.toList());
//                    return new Book(book.get(0), book.get(1), book.get(2), Integer.parseInt(book.get(3)), book.get(4), book.get(5), Integer.parseInt(book.get(6)));
//                }).collect(Collectors.toList());
//
//        } catch(Exception e){
//            e.printStackTrace();
//        }

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
        System.out.println("           |       7. Add book stock         |                 ");
        System.out.println("           |       8. Print member list      |                 ");
        System.out.println("           |       9. Add new member         |                 ");
        System.out.println("           |      10. Withdraw a member      |                 ");
        System.out.println("           |      11. Modify a member        |                 ");
        System.out.println("           |      12. Print a user's purchase|                 ");
        System.out.println("           |       s. Save                   |                 ");
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
            case "7":
                System.out.println("Type title : ");
                String titleToAddStock = scanner.nextLine().trim();
                System.out.println("Type stock : ");
                int stock = Integer.parseInt(scanner.nextLine().trim());
                addStock(titleToAddStock, stock);
                break;
            case "8":
                printMemberList();
                break;
            case "9":
                System.out.println("Type username : ");
                String username = scanner.nextLine().trim();
                System.out.println("Type email : ");
                String email = scanner.nextLine().trim();
                System.out.println("Type address : ");
                String address = scanner.nextLine().trim();
                addMember(username, email, address);
                break;
            case "10":
                System.out.println("Type username : ");
                String usernameToWithdraw = scanner.nextLine().trim();
                widrawMember(usernameToWithdraw);
                break;
            case "11":
                // TODO : modify member
                System.out.println( "Type usernmae : ");
                String usernameToModify = scanner.nextLine().trim();
                List<Member> memberListToModify = getMemberListToModify(usernameToModify);
                if (memberListToModify.size()!=1){
                    System.out.println("Type email");
                    String emailToModify = scanner.nextLine().trim();
                    memberListToModify = getMemberListToModify(usernameToModify, emailToModify);
                }

                System.out.printf("Type new user name [default:%s] :", memberListToModify.get(0).getUserName());
                String newUserName = scanner.nextLine().trim();
                if(newUserName =="") newUserName = memberListToModify.get(0).getUserName();

                System.out.printf("Type email [default:%s] :", memberListToModify.get(0).getEmail());
                String newEmail = scanner.nextLine().trim();
                if(newEmail =="") newEmail = memberListToModify.get(0).getEmail();

                System.out.printf("Type address [default:%s] :", memberListToModify.get(0).getAddress());
                String newAddress = scanner.nextLine().trim();
                if(newAddress =="") newAddress = memberListToModify.get(0).getAddress();

                new Member(newUserName, newEmail, newAddress);
                modifyMember(memberListToModify, new Member(newUserName, newEmail, newAddress));


                break;
            case "12":
                System.out.println("Type username : ");
                String usernameToPrintPurchases = scanner.nextLine().trim();
                printPurchaseListByUser(usernameToPrintPurchases);
                break;
            case "s":
                saveAsFile();
                break;
            case "0":
                System.exit(0);
                break;
            default:
                System.out.println("Error : " + command);
        }

    }


    private void saveAsFile() {
        try {
            File tmpFile = new File("memberlist.csv.tmp");
            tmpFile.createNewFile();
            memberlist.forEach(member -> {
                try {
                    Files.writeString(Path.of("memberlist.csv.tmp"), member.toCsvString() + "\n", StandardOpenOption.APPEND);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Files.move(Path.of("memberlist.csv.tmp"), Path.of("memberlist.csv"), StandardCopyOption.REPLACE_EXISTING);
            tmpFile.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            File bookTmpFile = new File("booklist.csv.tmp");
            bookTmpFile.createNewFile();
            list.forEach(book -> {
                try {
                    Files.writeString(Path.of("booklist.csv.tmp"), book.toCsvString() + "\n", StandardOpenOption.APPEND);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Files.move(Path.of("booklist.csv.tmp"), Path.of("booklist.csv"), StandardCopyOption.REPLACE_EXISTING);
            bookTmpFile.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            File purchaseTmpFile = new File("purchaselist.csv.tmp");
            purchaseTmpFile.createNewFile();
            purchaseList.forEach(purchase -> {
                try {
                    Files.writeString(Path.of("purchaselist.csv.tmp"), purchase.toCsvString() + "\n", StandardOpenOption.APPEND);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Files.move(Path.of("purchaselist.csv.tmp"), Path.of("purchaselist.csv"), StandardCopyOption.REPLACE_EXISTING);
            purchaseTmpFile.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void printPurchaseListByUser(String usernameToPrintPurchases) {
        purchaseList.stream().filter(purchase -> purchase.getCustomer().equals(usernameToPrintPurchases))
                .forEach(System.out::println);
    }

    private void modifyMember(List<Member> memberListToModify, Member member) {
        memberlist.stream().filter(m -> m.equals(memberListToModify.get(0)))
                .forEach(m -> {
                    if(!member.getUserName().isBlank()){
                        m.setUserName(member.getUserName());
                    }
                    if (!member.getEmail().isBlank()){
                        m.setEmail(member.getEmail());
                    }
                    if (!member.getAddress().isBlank()){
                        m.setAddress(member.getAddress());
                    }
                });
    }

    private List<Member> getMemberListToModify(String usernameToModify, String emailToModify) {
        List<Member> memberListToModify = this.memberlist.stream().filter(member -> member.getUserName().equals(usernameToModify))
                .filter(member -> member.getEmail().equals(emailToModify))
                .collect(Collectors.toUnmodifiableList());

        return memberListToModify;
    }

    private List<Member> getMemberListToModify(String usernameToModify) {
        List<Member> memberListToModify = this.memberlist.stream().filter(member -> member.getUserName().equals(usernameToModify))
                .collect(Collectors.toUnmodifiableList());
        memberListToModify.forEach(System.out::println);

        return memberListToModify;
    }

    private void widrawMember(String userToWithdraw) {
        memberlist.stream().filter(member -> member.getUserName().equals(userToWithdraw))
                .forEach(member -> member.setActive(false));
    }

    private void addMember(String username, String email, String address) {
        memberlist.add(
                new Member(username, email, address)
        );
    }

    private void printMemberList() {
        memberlist.stream().filter(member -> member.isActive())
                .forEach(System.out::println);
    }

    private void addStock(String titleToAddStock, int stock) {
        list.stream().filter(book -> book.getTitle().equals(titleToAddStock))
                .forEach(book -> book.setStock(book.getStock()+stock));
    }

    private void buyBook(String titleToBuy, String customer) {

        this.list.stream()
                .filter(book -> book.getTitle().equals(titleToBuy))
                .filter(book -> book.getStock() > 0)
                .forEach(book -> {
                    book.setStock(book.getStock()-1);
                    this.purchaseList.add(
                        new Purchase(titleToBuy, customer, 1)
                    );
                });
    }

    private void printPurchaseList() {
        this.purchaseList.stream()
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
                .findFirst().get();
//                .orElseGet(() -> book -> false);

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
