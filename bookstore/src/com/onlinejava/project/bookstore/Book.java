package com.onlinejava.project.bookstore;

import java.util.List;
import java.util.function.Predicate;

public class Book {
    private String title;
    private String writer;
    private String publisher;
    private int price;
    private String releaseDate;
    private String location;
    private int stock;

    public Book(String title, String writer, String publisher, int price, String releaseDate, String location, int stock) {
        this.title = title;
        this.writer = writer;
        this.publisher = publisher;
        this.price = price;
        this.releaseDate = releaseDate;
        this.location = location;
        this.stock = stock;
    } // constructor

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public String getPublisher() {
        return publisher;
    }

    public Integer getPrice() {
        return price;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getLocation() {
        return location;
    }

    public Integer getStock() {return stock;}


    public void setTitle(String title) {
        this.title = title;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public void setStock(int stock){this.stock = stock;}
    @Override
    public String toString() {
        return String.format("| %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t |", title, writer, publisher, price, releaseDate, location, stock);
    }

    public enum Properties{
        TITTLE(1) {
            @Override
            public Predicate<Book> same(String keyword) {
                return book -> book.getTitle().equals(keyword);
            }
        },
        WRITER(2) {
            @Override
            public Predicate<Book> same(String keyword) {
                return book -> book.getWriter().equals(keyword);
            }
        },
        PUBLISHER(3) {
            @Override
            public Predicate<Book> same(String keyword) {
                return book -> book.getPublisher().equals(keyword);
            }
        },
        PRICE(4) {
            @Override
            public Predicate<Book> same(String keyword) {
                return book -> book.getPrice().toString().equals(keyword);
            }
        },
        RELEASEDATE(5) {
            @Override
            public Predicate<Book> same(String keyword) {
                return book -> book.getReleaseDate().equals(keyword);
            }
        },
        LOCATION(6) {
            @Override
            public Predicate<Book> same(String keyword) {
                return book -> book.getLocation().equals(keyword);
            }
        },
        STOCK(7) {
            @Override
            public Predicate<Book> same(String keyword) {
                return book -> book.getStock().toString().equals(keyword);
            }
        };

        private final int categoryNumber;

        Properties(int categoryNumber){
            this.categoryNumber = categoryNumber;
        }

        public abstract  Predicate<Book> same(String keyword);

        public String toCategoryString(){
            return this.categoryNumber + ":" + this.toString();
        }

        public static List<Properties> valuesToList(){
            return List.of(values());
        }

        public int getCategoryNumber(){
            return this.categoryNumber;
        }
    }

} // end class
