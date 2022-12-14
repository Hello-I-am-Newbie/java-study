package com.onlinejava.project.bookstore.application.domain.entity;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Book extends Entity {
    private String title;
    private String writer;
    private String publisher;
    private int price;
    private String releaseDate;
    private String location;
    private int stock;

    public Book() {
    }

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
                return book -> book.getTitle().contains(keyword);
            }
        },
        WRITER(2) {
            @Override
            public Predicate<Book> same(String keyword) {
                return book -> book.getWriter().contains(keyword);
            }
        },
        PUBLISHER(3) {
            @Override
            public Predicate<Book> same(String keyword) {
                return book -> book.getPublisher().contains(keyword);
            }
        },
        PRICE(4) {
            @Override
            public Predicate<Book> same(String keyword) {
                return book -> book.getPrice().toString().contains(keyword);
            }
        },
        RELEASEDATE(5) {
            @Override
            public Predicate<Book> same(String keyword) {
                return book -> book.getReleaseDate().contains(keyword);
            }
        },
        LOCATION(6) {
            @Override
            public Predicate<Book> same(String keyword) {
                return book -> book.getLocation().contains(keyword);
            }
        },
        STOCK(7) {
            @Override
            public Predicate<Book> same(String keyword) {
                return book -> book.getStock().toString().contains(keyword);
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return price == book.price && stock == book.stock && Objects.equals(title, book.title) && Objects.equals(writer, book.writer) && Objects.equals(publisher, book.publisher) && Objects.equals(releaseDate, book.releaseDate) && Objects.equals(location, book.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, writer, publisher, price, releaseDate, location, stock);
    }
} // end class
