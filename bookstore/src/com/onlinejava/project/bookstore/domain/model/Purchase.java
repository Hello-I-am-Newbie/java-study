package com.onlinejava.project.bookstore.domain.model;

import java.util.Objects;

public class Purchase extends Model{
    private String title;
    private String customer;
    private String email;
    private int numberOfPurchase;
    private int totalPrice;
    private int point;

    public Purchase() {
    }

    public Purchase(String title, String customer, String email, int numberOfPurchase, int totalPrice, int point) {
        this.title = title;
        this.customer = customer;
        this.email = email;
        this.numberOfPurchase = numberOfPurchase;
        this.totalPrice = totalPrice;
        this.point = point;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumberOfPurchase() {
        return numberOfPurchase;
    }

    public void setNumberOfPurchase(int numberOfPurchase) {
        this.numberOfPurchase = numberOfPurchase;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "title='" + title + '\'' +
                ", customer='" + customer + '\'' +
                ", email='" + email + '\'' +
                ", numberOfPurchase=" + numberOfPurchase +
                ", totalPrice=" + totalPrice +
                ", point=" + point +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return numberOfPurchase == purchase.numberOfPurchase
                && totalPrice == purchase.totalPrice
                && point == purchase.point
                && Objects.equals(title, purchase.title)
                && Objects.equals(customer, purchase.customer)
                && Objects.equals(email, purchase.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, customer, email, numberOfPurchase, totalPrice, point);
    }
}
