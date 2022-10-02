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

    private ConsolePrinter<Member> printer = new ConsolePrinter<>(Member.class);
    public MemberCommands() {
        this.service = BeanFactory.getInstance().get(MemberUseCase.class);
    }
    @CliCommand(ID = "8", title = "Print member list")
    public void printMemberList() {
         printer.printList(service.getMemberList());
    }

    @CliCommand(ID = "9", title = "Add new member")
    public void addMember() {
        String username = ConsoleUtils.prompt("username");
        String email = ConsoleUtils.prompt("email");
        String address = ConsoleUtils.prompt("address");

        service.addMember(username, email, address);
    }

    @CliCommand(ID = "10", title = "Withdraw a member")
    public void withdrawMember() {
        String username = ConsoleUtils.prompt("username");

        service.withdrawMember(username);
    }

    @CliCommand(ID = "11", title = "Modify a member")
    public void ModifyMember() {
        String username = ConsoleUtils.prompt("username");
        List<Member> memberListToModify = service.getMemberListToModify(username);
        if (memberListToModify.size()!=1){
            String email = ConsoleUtils.prompt("email");
            memberListToModify = service.getMemberListToModify(username, email);
        }

        String newUserName = ConsoleUtils.promptDefaultValue("user name", memberListToModify.get(0).getUserName());
        String newEmail = ConsoleUtils.promptDefaultValue("email", memberListToModify.get(0).getEmail());
        String newAddress = ConsoleUtils.promptDefaultValue("address", memberListToModify.get(0).getAddress());

        Member newMember = new Member(newUserName, newEmail, newAddress, memberListToModify.get(0).getTotalPoint(), memberListToModify.get(0).getGrade());
        service.modifyMember(memberListToModify, newMember);

    }
}