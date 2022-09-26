package com.onlinejava.project.bookstore.application.domain.service;

import com.onlinejava.project.bookstore.application.domain.BookStoreFactory;
import com.onlinejava.project.bookstore.application.domain.entity.Book;
import com.onlinejava.project.bookstore.application.domain.entity.Member;
import com.onlinejava.project.bookstore.application.domain.entity.Purchase;
import com.onlinejava.project.bookstore.application.ports.input.PurchaseUseCase;
import com.onlinejava.project.bookstore.application.ports.output.BookRepository;
import com.onlinejava.project.bookstore.application.ports.output.MemberRepository;
import com.onlinejava.project.bookstore.application.ports.output.PurchaseRepository;

import java.util.List;

public class PurchaseService implements PurchaseUseCase {

    private PurchaseRepository repository;
    private BookRepository bookRepository;
    private MemberRepository memberRepository;

    public PurchaseService() {
    }
    public void setDependency() {
        this.repository = BookStoreFactory.lookup(PurchaseRepository.class);
        this.bookRepository = BookStoreFactory.lookup(BookRepository.class);
        this.memberRepository = BookStoreFactory.lookup(MemberRepository.class);
    }

    public PurchaseService(PurchaseRepository repository, BookRepository bookRepository, MemberRepository memberRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void buyBook(String titleToBuy, String customer, String email) {
        bookRepository.findAll().stream()
                .filter(book -> book.getTitle().equals(titleToBuy))
                .filter(book -> book.getStock() > 0)
                .forEach(book -> {
                    book.setStock(book.getStock()-1);
                    repository.add(
                            new Purchase(
                                    titleToBuy,
                                    customer,
                                    email,
                                    1,
                                    book.getPrice(),
                                    getPoint(book, customer, email)
                            )
                    );
                    memberRepository.findByNameAndEmail(customer, email).ifPresent(member -> member.addPoint(getPoint(book, customer, email)));
                });
    }

    @Override
    public void printPurchaseListByUser(String usernameToPrintPurchases) {
        getPurchaseList().stream().filter(purchase -> purchase.getCustomer().equals(usernameToPrintPurchases))
                .forEach(System.out::println);
    }
    @Override
    public int getPoint(Book book, String customer, String email) {
        return memberRepository.findByNameAndEmail(customer, email)
                .map(m -> getPointByMember(book, m))
                .orElseThrow();
    }
    @Override
    public int getPointByMember(Book book, Member member) {
        return member.getGrade().calculatePoint(book.getPrice());
    }

    @Override
    public List<Purchase> getPurchaseList(){
        return repository.findAll();
    }

}
