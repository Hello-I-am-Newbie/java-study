package com.onlinejava.project.bookstore;

import java.util.List;

public class Book {
    private String title;
    private String writer;
    private String publisher;
    private int price;
    private String releaseDate;
    private String location;

    public Book(String title, String writer, String publisher, int price, String releaseDate, String location) {
        this.title = title;
        this.writer = writer;
        this.publisher = publisher;
        this.price = price;
        this.releaseDate = releaseDate;
        this.location = location;
    }

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
    @Override
    public String toString() {
        return String.format("| %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t |", title, writer, publisher, price, releaseDate, location);
    }


}
