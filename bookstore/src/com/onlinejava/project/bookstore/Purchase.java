package com.onlinejava.project.bookstore;

public class Purchase {
    private final String title;
    private final String customer;
    private final int numberOfPurchase;

    public Purchase(String title, String customer, int numberOfPurchase) {
        this.title = title;
        this.customer = customer;
        this.numberOfPurchase = numberOfPurchase;
    }

    public String getTitle() {
        return title;
    }

    public String getCustomer() {
        return customer;
    }

    public int getNumberOfPurchase() {
        return numberOfPurchase;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "title='" + title + '\'' +
                ", customer='" + customer + '\'' +
                ", numberOfPurchase=" + numberOfPurchase +
                '}';
    }

    public String toCsvString() {
        return String.join(",", title, customer, String.valueOf(numberOfPurchase));
    }
}
