package com.onlinejava.project.bookstore.application.ports.output;

import com.onlinejava.project.bookstore.application.domain.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    List<Member> findAll();

    Optional<Member> findByUserName(String userName);

    List<Member> findAllByName(String userName);
    List<Member> findAllByNameAndEmail(String username, String email);
    Optional<Member> findByNameAndEmail(String username, String email);

    List<Member> findActiveMembers();

    void add(Member member);

    void save();
}
