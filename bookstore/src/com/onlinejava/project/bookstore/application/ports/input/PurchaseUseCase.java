package com.onlinejava.project.bookstore.application.ports.input;

import com.onlinejava.project.bookstore.application.domain.entity.Book;
import com.onlinejava.project.bookstore.application.domain.entity.Member;
import com.onlinejava.project.bookstore.application.domain.entity.Purchase;

import java.util.List;

public interface PurchaseUseCase {
    public void buyBook(String titleToBuy, String customer, String email);

    public abstract void printPurchaseListByUser(String usernameToPrintPurchases);

    public abstract int getPoint(Book book, String customer, String email);

    public abstract int getPointByMember(Book book, Member member);

    public abstract List<Purchase> getPurchaseList();
}
