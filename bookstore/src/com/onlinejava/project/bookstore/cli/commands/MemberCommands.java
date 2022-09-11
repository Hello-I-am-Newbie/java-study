package com.onlinejava.project.bookstore.cli.commands;

import com.onlinejava.project.bookstore.Member;
import com.onlinejava.project.bookstore.core.cli.CliCommand;

import java.util.List;

import static com.onlinejava.project.bookstore.core.cli.CliCommandInterface.bookstore;
import static com.onlinejava.project.bookstore.core.cli.CliCommandInterface.scanner;


@CliCommand
public class MemberCommands {

    @CliCommand(ID = "8", title = "Print member list")
    public void printMemberList() {
        bookstore.printMemberList();
    }

    @CliCommand(ID = "9", title = "Add new member")
    public void addMember() {
        System.out.println("Type username : ");
        String username = scanner.nextLine().trim();
        System.out.println("Type email : ");
        String email = scanner.nextLine().trim();
        System.out.println("Type address : ");
        String address = scanner.nextLine().trim();
        bookstore.addMember(username, email, address);
    }

    @CliCommand(ID = "10", title = "Withdraw a member")
    public void withdrawMember() {
        System.out.println("Type username : ");
        String usernameToWithdraw = scanner.nextLine().trim();
        bookstore.withdrawMember(usernameToWithdraw);
    }

    @CliCommand(ID = "11", title = "Modify a member")
    public void ModifyMember() {
        System.out.println( "Type username : ");
        String usernameToModify = scanner.nextLine().trim();
        List<Member> memberListToModify = bookstore.getMemberListToModify(usernameToModify);
        if (memberListToModify.size()!=1){
            System.out.println("Type email");
            String emailToModify = scanner.nextLine().trim();
            memberListToModify = bookstore.getMemberListToModify(usernameToModify, emailToModify);
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

        new Member(newUserName, newEmail, newAddress);
        bookstore.modifyMember(memberListToModify, new Member(newUserName, newEmail, newAddress));

    }
}