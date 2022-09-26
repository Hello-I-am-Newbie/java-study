package com.onlinejava.project.bookstore.application.ports.input;

import com.onlinejava.project.bookstore.application.domain.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberUseCase {
    public abstract  void modifyMember(List<Member> memberListToModify, Member member) ;

    public abstract List<Member> getMemberListToModify(String usernameToModify, String emailToModify) ;

    public abstract List<Member> getMemberListToModify(String usernameToModify) ;

    public abstract Optional<Member> getMemberByNameAndEmail(String username, String email);

    public abstract void withdrawMember(String userToWithdraw) ;

    public abstract void addMember(String username, String email, String address);

    public abstract List<Member> getActiveMemberList();

    public abstract List<Member> getMemberList();

    public abstract void updateMemberGrades();
}
