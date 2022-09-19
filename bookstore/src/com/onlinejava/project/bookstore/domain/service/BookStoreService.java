package com.onlinejava.project.bookstore.domain.service;

import com.onlinejava.project.bookstore.Main;
import com.onlinejava.project.bookstore.core.reflect.ModelSetter;
import com.onlinejava.project.bookstore.domain.model.*;
import com.onlinejava.project.bookstore.core.cli.CliCommand;
import com.onlinejava.project.bookstore.core.cli.CliCommandInterface;
import com.onlinejava.project.bookstore.core.cli.CommandInvocationHandler;

import javax.swing.text.html.Option;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.onlinejava.project.bookstore.domain.model.Book.Properties.*;
import static com.onlinejava.project.bookstore.core.function.Functions.unchecked;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summarizingInt;

public class BookStoreService {
    private static BookStoreService bookStoreService = new BookStoreService();
    private List<Book> bookList;
    private List<Purchase> purchaseList;
    private List<Member> memberList;
    private Map<String, CliCommandInterface> commands;

    public static BookStoreService getInstance() {
        return BookStoreService.bookStoreService;
    }
    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public void setPurchaseList(List<Purchase> purchaseList) {
        this.purchaseList = purchaseList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }

    {
//        bookList = new ArrayList<>();
//        purchaseList = new ArrayList<>();
//        memberList = new ArrayList<>();

//        try {
//            this.bookList = Files.lines(Path.of("booklist.csv"))
//                    .map(line -> {
//                        List<String> book = Arrays.stream(line.split(",")).map(String::trim).collect(Collectors.toList());
//                        return new Book(book.get(0), book.get(1), book.get(2), Integer.parseInt(book.get(3)), book.get(4), book.get(5), Integer.parseInt(book.get(6)));
//                    }).collect(Collectors.toList());
//
//            this.purchaseList = Files.lines(Path.of("purchaselist.csv"))
//                    .map(line -> {
//                        List<String> purchase = Arrays.stream(line.split(",")).map(String::trim).collect(Collectors.toList());
//                        return new Purchase(purchase.get(0), purchase.get(1), purchase.get(2), Integer.parseInt(purchase.get(2)));
//                    }).collect(Collectors.toList());
//
//            this.memberList = Files.lines(Path.of("memberlist.csv"))
//                    .map(line -> {
//                        List<String> member = Arrays.stream(line.split(",")).map(String::trim).collect(Collectors.toList());
//                        return new Member(member.get(0), member.get(1), member.get(2));
//                    }).collect(Collectors.toList());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        String commandPackage = "com.onlinejava.project.bookstore.cli.commands";

        try (
                InputStream resourceIStream = ClassLoader.getSystemClassLoader().getResourceAsStream(commandPackage.replaceAll("[.]", "/"));
                InputStreamReader resourceISR = new InputStreamReader(resourceIStream);
                BufferedReader resourceReader = new BufferedReader(resourceISR);
        ) {
            List<Class> classesInPackage = resourceReader.lines()
                    .filter(line -> line.endsWith(".class"))
                    .map(unchecked(line -> Class.forName(commandPackage + "." + line.substring(0, line.length() - ".class".length()))))
                    .collect(Collectors.toUnmodifiableList());

            Stream<CliCommandInterface> cliCommandInterfaceStream = classesInPackage.stream()
                    .filter(clazz -> CliCommandInterface.class.isAssignableFrom(clazz))
                    .filter(clazz -> !clazz.isInterface())
                    .map(unchecked(clazz -> (CliCommandInterface) clazz.getDeclaredConstructor().newInstance()));

            Stream<CliCommandInterface> annotatedCommandStream = classesInPackage.stream()
                    .filter(clazz -> clazz.isAnnotationPresent(CliCommand.class))
                    .flatMap(clazz -> Arrays.stream(clazz.getDeclaredMethods()))
                    .filter(method -> method.isAnnotationPresent(CliCommand.class))
                    .map(BookStoreService::methodToCliCommand);

            commands = Stream.concat(cliCommandInterfaceStream, annotatedCommandStream)
                    .map(BookStoreService::commandToProxy)
                    .collect(Collectors.toMap(CliCommandInterface::getCommandID, Function.identity()));


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

    public <T extends Model> List<T> getModelListFromLines(String filePath, Class<T> clazz) {
        List<String> lines = getFileLines(filePath).collect(Collectors.toUnmodifiableList());
        return getModelListFromLines(lines, clazz, Main.HAS_HEADER);
    }

    public <T extends Model> List<T> getModelListFromLines(List<String> lines, Class<T> clazz, boolean hasHeader) {
        return hasHeader
                ? getModelListFromLinesWithHeader(lines, clazz)
                : getModelListFromLinesWithoutHeader(lines, clazz);
    }

    public <T extends Model> List<T> getModelListFromLinesWithHeader(List<String> lines, Class<T> clazz) {
        if (lines.size() <= 1) {
            return Collections.emptyList();
        }

        String[] headers = lines.get(0).split(",");
        return lines.stream().skip(1)
                .map(line -> {

                    ModelSetter<T> ObjectSetter = new ModelSetter(clazz);
                    String[] values = Arrays.stream(line.split(",")).map(String::trim).toArray(String[]::new);
                    for (int i = 0; i < headers.length; i++) {
                        ObjectSetter.set(headers[i], values[i]);
                    }

                    return ObjectSetter.getObject();
                })
                .collect(Collectors.toList());
    }

    public <T extends Model> List<T> getModelListFromLinesWithoutHeader(List<String> lines, Class<T> clazz) {
        return lines.stream()
                .map(line -> {

                    ModelSetter<T> ObjectSetter = new ModelSetter(clazz);
                    Field[] fields = clazz.getDeclaredFields();
                    String[] values = Arrays.stream(line.split(",")).map(String::trim).toArray(String[]::new);
                    for (int i = 0; i < fields.length && i < values.length; i++) {
                        ObjectSetter.set(fields[i].getName(), values[i]);
                    }

                    return ObjectSetter.getObject();
                })
                .collect(Collectors.toList());
    }

    private Stream<String> getFileLines(String first) {
        try {
            return Files.lines(Path.of(first));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static CliCommandInterface commandToProxy(CliCommandInterface cliCommandInstance) {
        ClassLoader classLoader = CliCommandInterface.class.getClassLoader();
        Class[] interfaces = {CliCommandInterface.class};
        CommandInvocationHandler handler = new CommandInvocationHandler(cliCommandInstance);
        return (CliCommandInterface) Proxy.newProxyInstance(classLoader, interfaces, handler);
    }

    private static CliCommandInterface methodToCliCommand(Method method) {
        CliCommand classCommand = method.getClass().getDeclaredAnnotation(CliCommand.class);
        CliCommand methodCommand = method.getDeclaredAnnotation(CliCommand.class);

        Object instance = null;
        try {
            instance = method.getDeclaringClass().getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        final Object finalInstance = instance;

        return new CliCommandInterface() {
            @Override
            public String getCommandID() {
                return methodCommand.ID().isBlank() ? classCommand.ID() : methodCommand.ID();
            }

            @Override
            public String getTitle() {
                return methodCommand.title().isBlank() ? classCommand.title() : methodCommand.title();
            }

            @Override
            public String getDescription() {
                return methodCommand.description().isBlank() ? classCommand.description() : methodCommand.description();
            }

            @Override
            public int order() {
                return methodCommand.order();
            }

            @Override
            public void run() {
                try {
                    method.invoke(finalInstance);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }


    public void printWelcomePage() {
        System.out.println("=================================================================");
        System.out.println("                                                                 ");
        System.out.println("                                                                 ");
        System.out.println("                     Welcome to Bookstore                        ");
        System.out.println("              ---------------------------------                  ");
        System.out.println("             |                                  |                ");
        commands.values().stream()
                .sorted(CliCommandInterface::ordering)
                .forEach(command -> {
                    System.out.printf("%-13s|%6s. %-26s|%13s\n","", command.getCommandID(), command.getTitle(), "");
                    System.out.printf("%-13s|%6s  %-26s|%13s\n","","", "", "");
                });

        System.out.println("             |                                  |                ");
        System.out.println("              ---------------------------------                  ");
        System.out.println("                                                                 ");
        System.out.println("                                                                 ");
        System.out.println("                                                                 ");
        System.out.println("=================================================================");
    }

    public void runCommand(Scanner scanner) {
        String cmdNum = scanner.nextLine().trim();
        Optional.ofNullable(commands.get(cmdNum)).ifPresentOrElse(
                CliCommandInterface::run,
                () -> System.out.println("Error: Unknown command : " + cmdNum)
        );
//        String command = scanner.nextLine().trim();
//        switch (command) {
//            case "1":
//                printAllBook(this.bookList);
//                break;
//            case "2":
//                addBook();
//                break;
//            case "3":
//                System.out.println("Please select category number to search");
//                Stream.of(TITTLE, WRITER, PUBLISHER, PRICE, RELEASEDATE, LOCATION, STOCK)
//                        .map(Properties::toCategoryString)
//                        .forEach(System.out::println);
//                int categoryNum = Integer.parseInt(scanner.nextLine().trim());
//
//                System.out.println("keyword :");
//                String keyword = scanner.nextLine().trim();
//
//                List<Book> tempList = searchBook(categoryNum , keyword);
//                printAllBook(tempList);
//                break;
//            case "4":
//                System.out.println("Type TITLE :");
//                String title = scanner.nextLine().trim();
//                removeBook(title, scanner);
//                break;
//            case "5":
//                System.out.println("Type TITLE :");
//                String titleToBuy = scanner.nextLine().trim();
//                System.out.println("Type customer :");
//                String customer = scanner.nextLine().trim();
//                buyBook(titleToBuy, customer);
//                break;
//            case "6":
//                printPurchaseList();
//                break;
//            case "7":
//                System.out.println("Type title : ");
//                String titleToAddStock = scanner.nextLine().trim();
//                System.out.println("Type stock : ");
//                int stock = Integer.parseInt(scanner.nextLine().trim());
//                addStock(titleToAddStock, stock);
//                break;
//            case "8":
//                printMemberList();
//                break;
//            case "9":
//                System.out.println("Type username : ");
//                String username = scanner.nextLine().trim();
//                System.out.println("Type email : ");
//                String email = scanner.nextLine().trim();
//                System.out.println("Type address : ");
//                String address = scanner.nextLine().trim();
//                addMember(username, email, address);
//                break;
//            case "10":
//                System.out.println("Type username : ");
//                String usernameToWithdraw = scanner.nextLine().trim();
//                withdrawMember(usernameToWithdraw);
//                break;
//            case "11":
//                // TODO : modify member
//                System.out.println( "Type usernmae : ");
//                String usernameToModify = scanner.nextLine().trim();
//                List<Member> memberListToModify = getMemberListToModify(usernameToModify);
//                if (memberListToModify.size()!=1){
//                    System.out.println("Type email");
//                    String emailToModify = scanner.nextLine().trim();
//                    memberListToModify = getMemberListToModify(usernameToModify, emailToModify);
//                }
//
//                System.out.printf("Type new user name [default:%s] :", memberListToModify.get(0).getUserName());
//                String newUserName = scanner.nextLine().trim();
//                if(newUserName =="") newUserName = memberListToModify.get(0).getUserName();
//
//                System.out.printf("Type email [default:%s] :", memberListToModify.get(0).getEmail());
//                String newEmail = scanner.nextLine().trim();
//                if(newEmail =="") newEmail = memberListToModify.get(0).getEmail();
//
//                System.out.printf("Type address [default:%s] :", memberListToModify.get(0).getAddress());
//                String newAddress = scanner.nextLine().trim();
//                if(newAddress =="") newAddress = memberListToModify.get(0).getAddress();
//
//                new Member(newUserName, newEmail, newAddress);
//                modifyMember(memberListToModify, new Member(newUserName, newEmail, newAddress));
//
//
//                break;
//            case "12":
//                System.out.println("Type username : ");
//                String usernameToPrintPurchases = scanner.nextLine().trim();
//                printPurchaseListByUser(usernameToPrintPurchases);
//                break;
//            case "s":
//                saveAsFile();
//                break;
//            case "0":
//                System.exit(0);
//                break;
//            default:
//                System.out.println("Error : " + command);
//        }

    }


    public void saveAsFile() {
        try {
            File tmpFile = new File("memberlist.csv.tmp");
            tmpFile.createNewFile();

            if (Main.HAS_HEADER) {
                Files.writeString(Path.of("memberlist.csv.tmp"), Model.toCsvHeader(Member.class) + "\n", StandardOpenOption.APPEND);
            }
            memberList.forEach(member -> {
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
            if (Main.HAS_HEADER) {
                Files.writeString(Path.of("booklist.csv.tmp"), Model.toCsvHeader(Book.class) + "\n", StandardOpenOption.APPEND);
            }
            bookList.forEach(book -> {
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
            Files.writeString(Path.of("purchaselist.csv.tmp"), Model.toCsvHeader(Purchase.class) + "\n" + StandardOpenOption.APPEND);
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

    public void printPurchaseListByUser(String usernameToPrintPurchases) {
        purchaseList.stream().filter(purchase -> purchase.getCustomer().equals(usernameToPrintPurchases))
                .forEach(System.out::println);
    }

    public void modifyMember(List<Member> memberListToModify, Member member) {
        memberList.stream().filter(m -> m.equals(memberListToModify.get(0)))
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
    public List<Member> getMemberListToModify(String usernameToModify, String emailToModify) {
        List<Member> memberListToModify = this.memberList.stream().filter(member -> member.getUserName().equals(usernameToModify))
                .filter(member -> member.getEmail().equals(emailToModify))
                .collect(Collectors.toUnmodifiableList());

        return memberListToModify;
    }

    public List<Member> getMemberListToModify(String usernameToModify) {
        List<Member> memberListToModify = this.memberList.stream().filter(member -> member.getUserName().equals(usernameToModify))
                .collect(Collectors.toUnmodifiableList());
        memberListToModify.forEach(System.out::println);

        return memberListToModify;
    }
    public Optional<Member> getMemberByNameAndEmail(String username, String email){
        Optional<Member> member = this.memberList.stream().filter(m -> m.getUserName().equals(username))
                .filter(m -> m.getEmail().equals(email))
                .findFirst();

        return member;
    }

    public void withdrawMember(String userToWithdraw) {
        memberList.stream().filter(member -> member.getUserName().equals(userToWithdraw))
                .forEach(member -> member.setActive(false));
    }

    public void addMember(String username, String email, String address) {
        memberList.add(
                new Member(
                        username,
                        email,
                        address,
                        0,
                        Grade.BRONZE
                        )
        );
    }

    public void printMemberList() {
        memberList.stream().filter(member -> member.isActive())
                .forEach(System.out::println);
    }

    public void addStock(String titleToAddStock, int stock) {
        this.bookList.stream().filter(book -> book.getTitle().equals(titleToAddStock))
                .forEach(book -> book.setStock(book.getStock()+stock));
    }

    public void buyBook(String titleToBuy, String customer, String email) {

        this.bookList.stream()
                .filter(book -> book.getTitle().equals(titleToBuy))
                .filter(book -> book.getStock() > 0)
                .forEach(book -> {
                    book.setStock(book.getStock()-1);
                    this.purchaseList.add(
                        new Purchase(
                                titleToBuy,
                                customer,
                                email,
                                1,
                                book.getPrice(),
                                getPoint(book, customer, email)
                        )
                    );
                    getMemberByNameAndEmail(customer, email).ifPresent(member -> member.addPoint(getPoint(book, customer, email)));
                });
    }
    private int getPoint(Book book, String customer, String email) {
        return getMemberByNameAndEmail(customer, email)
                .map(m -> getPointByMember(book, m))
                .orElseThrow();
    }

    private int getPointByMember(Book book, Member member) {
        return member.getGrade().calculatePoint(book.getPrice());
    }

    public void printPurchaseList() {
        this.purchaseList.stream()
                .forEach(System.out::println);
    }


    public void printAllBook(List<Book> bookList){
        System.out.printf("| %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t |%n", "TITLE", "WRITER", "PUBLISHER", "PRICE", "RELEASEDATE", "LOCATION", "STOCK");

        bookList.forEach(i -> System.out.println(i));

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
        System.out.println("stock > ");
        int stock = Integer.parseInt(scanner.nextLine().trim());


        Book book = new Book(title, writer, publisher, price, releaseDate, location, stock);
        this.bookList.add(book);

    }

    public List<Book> searchBook(int category,String keyword){

        Predicate<Book> bookPredicate = valuesToList().stream()
                .filter(p -> p.getCategoryNumber() == category)
                .map(p -> p.same(keyword))
                .findFirst().get();
//                .orElseGet(() -> book -> false);

        return bookList.stream()
                .filter(bookPredicate)
                .collect(Collectors.toUnmodifiableList());

    }

    public void removeBook(String title,Scanner scanner){

        System.out.println("List of deleted Books");
        System.out.printf("| %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t |%n", "TITLE", "WRITER", "PUBLISHER", "PRICE", "RELEASEDATE", "LOCATION", "STOCK");
        bookList.stream()
                .filter(book -> book.getTitle().equals(title))
                .collect(Collectors.toUnmodifiableList())
                .forEach(System.out::println);

        System.out.println("Do you really want to delete?   Y\\N");

        String answer = scanner.nextLine().trim();
        if (answer.equalsIgnoreCase("Y")){
            while(this.bookList.stream().filter((book)->book.getTitle().equals(title)).findFirst().isPresent()){
                this.bookList.stream()
                        .filter((book)->book.getTitle()
                        .equals(title)).findFirst()
                        .ifPresent(book -> this.bookList.remove(book));
            }
            printAllBook(this.bookList);
        }else if (answer.equalsIgnoreCase("N")){
            System.out.println("canceled deletion process");
        }else {
            System.out.println("remove Book Error : " + answer);
            System.out.println("canceled deletion process");
        }

    }

    public List<Book> getBookList(){
        return this.bookList;
    }
    public List<Member> getMemberList(){
        return this.memberList;
    }
    public List<Purchase> getPurchaseList(){
        return this.purchaseList;
    }

    public void updateMemberGrades() {

        // 이름이 같으면 문제 발생
        this.purchaseList.stream()
                .collect(groupingBy(Purchase::getCustomer,Collectors.groupingBy(Purchase::getEmail, summarizingInt(Purchase::getTotalPrice))))
                .forEach((username, map) -> {
                    map.forEach((email, stat)->{
                        Grade newGrade = Grade.getGradeByTotalPrice(stat.getSum());
                        getMemberByNameAndEmail(username, email).ifPresent(member -> member.setGrade(newGrade));
                    });


                });

    }
}
