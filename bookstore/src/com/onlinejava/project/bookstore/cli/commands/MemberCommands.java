package com.onlinejava.project.bookstore.cli.commands;

import com.onlinejava.project.bookstore.domain.model.Member;
import com.onlinejava.project.bookstore.core.cli.CliCommand;

import java.util.List;

import static com.onlinejava.project.bookstore.Main.*;
import static com.onlinejava.project.bookstore.Main.scanner;


@CliCommand
public class MemberCommands {

    @CliCommand(ID = "8", title = "Print member list")
    public void printMemberList() {
        bookStoreService.printMemberList();
    }

    @CliCommand(ID = "9", title = "Add new member")
    public void addMember() {
        System.out.println("Type username : ");
        String username = scanner.nextLine().trim();
        System.out.println("Type email : ");
        String email = scanner.nextLine().trim();
        System.out.println("Type address : ");
        String address = scanner.nextLine().trim();
        bookStoreService.addMember(username, email, address);
    }

    @CliCommand(ID = "10", title = "Withdraw a member")
    public void withdrawMember() {
        System.out.println("Type username : ");
        String usernameToWithdraw = scanner.nextLine().trim();
        bookStoreService.withdrawMember(usernameToWithdraw);
    }

    @CliCommand(ID = "11", title = "Modify a member")
    public void ModifyMember() {
        System.out.println( "Type username : ");
        String usernameToModify = scanner.nextLine().trim();
        List<Member> memberListToModify = bookStoreService.getMemberListToModify(usernameToModify);
        if (memberListToModify.size()!=1){
            System.out.println("Type email");
            String emailToModify = scanner.nextLine().trim();
            memberListToModify = bookStoreService.getMemberListToModify(usernameToModify, emailToModify);
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
        bookStoreService.modifyMember(memberListToModify, newMember);

    }
}