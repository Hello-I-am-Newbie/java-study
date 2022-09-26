package com.onlinejava.project.bookstore.application.domain.service;

import com.onlinejava.project.bookstore.application.domain.BookStoreFactory;
import com.onlinejava.project.bookstore.application.domain.entity.Book;
import com.onlinejava.project.bookstore.application.domain.entity.Grade;
import com.onlinejava.project.bookstore.application.domain.entity.Member;
import com.onlinejava.project.bookstore.application.domain.entity.Purchase;
import com.onlinejava.project.bookstore.application.ports.input.MemberUseCase;
import com.onlinejava.project.bookstore.application.ports.output.MemberRepository;
import com.onlinejava.project.bookstore.application.ports.output.PurchaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.onlinejava.project.bookstore.application.domain.entity.Book.Properties.valuesToList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summarizingInt;

public class MemberService implements MemberUseCase {
    private MemberRepository repository;
    private PurchaseService purchaseService;

    public MemberService() {

    }
    public void setDependency() {
        this.repository = BookStoreFactory.lookup(MemberRepository.class);
    }
    public MemberService(MemberRepository repository, PurchaseService purchaseService) {
        this.repository = repository;
        this.purchaseService = purchaseService;
    }

    @Override
    public void modifyMember(List<Member> memberListToModify, Member member) {
        getMemberList().stream().filter(m -> m.equals(memberListToModify.get(0)))
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
        List<Member> memberListToModify = repository.findAllByNameAndEmail(usernameToModify, emailToModify);

        return memberListToModify;
    }

    public List<Member> getMemberListToModify(String usernameToModify) {
        List<Member> memberListToModify = repository.findAllByName(usernameToModify);
        memberListToModify.forEach(System.out::println);

        return memberListToModify;
    }
    public Optional<Member> getMemberByNameAndEmail(String username, String email){
        return repository.findByNameAndEmail(username, email);
    }

    public void withdrawMember(String userToWithdraw) {
        repository.findByUserName(userToWithdraw)
                .ifPresent(member -> member.setActive(false));
    }

    public void addMember(String username, String email, String address) {
        repository.add(
                new Member(
                        username,
                        email,
                        address,
                        0,
                        Grade.BRONZE
                )
        );
    }

    public List<Member> getActiveMemberList() {
        return repository.findActiveMembers();
    }


    public List<Member> getMemberList(){
        return repository.findAll();
    }

    public void updateMemberGrades() {

        // 이름이 같으면 문제 발생
        purchaseService.getPurchaseList().stream()
                .collect(groupingBy(Purchase::getCustomer,Collectors.groupingBy(Purchase::getEmail, summarizingInt(Purchase::getTotalPrice))))
                .forEach((username, map) -> {
                    map.forEach((email, stat)->{
                        Grade newGrade = Grade.getGradeByTotalPrice(stat.getSum());
                        getMemberByNameAndEmail(username, email).ifPresent(member -> member.setGrade(newGrade));
                    });

                });

    }

}
