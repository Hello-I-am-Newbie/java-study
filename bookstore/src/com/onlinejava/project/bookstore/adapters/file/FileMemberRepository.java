package com.onlinejava.project.bookstore.adapters.file;

import com.onlinejava.project.bookstore.Main;
import com.onlinejava.project.bookstore.application.domain.entity.Member;
import com.onlinejava.project.bookstore.application.ports.output.MemberRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileMemberRepository extends FileRepository<Member> implements MemberRepository {
    @Override
    public List<Member> findAll() {
        return this.list;
    }

    @Override
    public Optional<Member> findByUserName(String userName) {
        return this.list.stream()
                .filter(m -> m.getUserName().equals(userName))
                .findFirst();
    }

    @Override
    public List<Member> findAllByName(String userName) {
        return this.list.stream().filter(member -> member.getUserName().equals(userName))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Member> findAllByNameAndEmail(String username, String email) {
        return this.list.stream().filter(member -> member.getUserName().equals(username))
                .filter(member -> member.getEmail().equals(email))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<Member> findByNameAndEmail(String username, String email) {
        return this.list.stream().filter(m -> m.getUserName().equals(username))
                .filter(m -> m.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public List<Member> findActiveMembers() {
        return this.list.stream()
                .filter(Member::isActive)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void add(Member member) {
        this.list.add(member);
    }

    @Override
    public void save() {
        saveEntityToCSVFile("memberlist.csv", Member.class, Main.HAS_HEADER);
    }

    @Override
    public void initData() {
        this.list = getEntityListFromLines("memberlist.csv", Member.class);
    }
}