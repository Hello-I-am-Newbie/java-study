package com.onlinejava.project.bookstore.adapters.cli;

import com.onlinejava.project.bookstore.application.domain.entity.Member;
import com.onlinejava.project.bookstore.application.ports.input.MemberUseCase;
import com.onlinejava.project.bookstore.core.cli.CliCommand;
import com.onlinejava.project.bookstore.core.factory.BeanFactory;

import java.util.List;

import static com.onlinejava.project.bookstore.application.domain.BookStoreApplication.scanner;


@CliCommand
public class MemberCommands {
    private MemberUseCase service;

    public MemberCommands() {
        this.service = BeanFactory.getInstance().get(MemberUseCase.class);
    }
    @CliCommand(ID = "8", title = "Print member list")
    public void printMemberList() {
        service.getMemberList().forEach(System.out::println);
    }

    @CliCommand(ID = "9", title = "Add new member")
    public void addMember() {
        System.out.println("Type username : ");
        String username = scanner.nextLine().trim();
        System.out.println("Type email : ");
        String email = scanner.nextLine().trim();
        System.out.println("Type address : ");
        String address = scanner.nextLine().trim();
        service.addMember(username, email, address);
    }

    @CliCommand(ID = "10", title = "Withdraw a member")
    public void withdrawMember() {
        System.out.println("Type username : ");
        String usernameToWithdraw = scanner.nextLine().trim();
        service.withdrawMember(usernameToWithdraw);
    }

    @CliCommand(ID = "11", title = "Modify a member")
    public void ModifyMember() {
        System.out.println( "Type username : ");
        String usernameToModify = scanner.nextLine().trim();
        List<Member> memberListToModify = service.getMemberListToModify(usernameToModify);
        if (memberListToModify.size()!=1){
            System.out.println("Type email");
            String emailToModify = scanner.nextLine().trim();
            memberListToModify = service.getMemberListToModify(usernameToModify, emailToModify);
        }

        System.out.printf("Type new user name [default:%s] :", memberListToModify.get(0).getUserName());
        String newUserName = scanner.nextLine().trim();
        if(newUserName =="") newUserName = memberListToModify.get(0).getUserName();

        System.out.printf("Type email [default:%s] :", memberListToModify.get(0).getEmail());
        String newEmail = scanner.nextLine().trim();
        if(newEmail =="") newEmail = memberListToModify.get(0).getEmail();

        System.out.printf("Type address [default:%s] :", memberListToModify.get(0).getAddress());
        String newAddress = scanner.nextLine().trim();
        if(newAddress =="") newAddress = memberListToModify.get(0).getAddress();

        Member newMember = new Member(newUserName, newEmail, newAddress, memberListToModify.get(0).getTotalPoint(), memberListToModify.get(0).getGrade());
        service.modifyMember(memberListToModify, newMember);

    }
}