package com.onlinejava.project.bookstore.adapters.cli;

import com.onlinejava.project.bookstore.application.ports.output.BookRepository;
import com.onlinejava.project.bookstore.application.ports.output.MemberRepository;
import com.onlinejava.project.bookstore.application.ports.output.PurchaseRepository;
import com.onlinejava.project.bookstore.core.cli.CliCommandInterface;
import com.onlinejava.project.bookstore.core.factory.BeanFactory;

public class SaveCommand implements CliCommandInterface {
    private BookRepository bookRepository;
    private MemberRepository memberRepository;
    private PurchaseRepository purchaseRepository;

    public SaveCommand() {
        this.bookRepository = BeanFactory.getInstance().get(BookRepository.class);
        this.memberRepository = BeanFactory.getInstance().get(MemberRepository.class);
        this.purchaseRepository = BeanFactory.getInstance().get(PurchaseRepository.class);
    }

    public SaveCommand(BookRepository bookRepository, MemberRepository memberRepository, PurchaseRepository purchaseRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void setPurchaseRepository(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }
    @Override
    public String getCommandID() {
        return "s";
    }

    @Override
    public String getTitle() {
        return "Save";
    }

    @Override
    public String getDescription() {
        return "Save The Changes";
    }

    @Override
    public void run() {
        bookRepository.save();
        memberRepository.save();
        purchaseRepository.save();
    }
}
