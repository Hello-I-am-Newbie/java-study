package com.onlinejava.project.bookstore.adapters.cli;

import com.onlinejava.project.bookstore.application.ports.input.MemberUseCase;
import com.onlinejava.project.bookstore.core.cli.CliCommand;
import com.onlinejava.project.bookstore.core.factory.BeanFactory;

import static com.onlinejava.project.bookstore.application.domain.BookStoreApplication.scanner;


@CliCommand
public class GradeCommands {
    private MemberUseCase memberService;
    public GradeCommands() {
        this.memberService = BeanFactory.getInstance().get(MemberUseCase.class);
    }
    @CliCommand(ID = "13", title = "Update member's grades")
    public void printUsersPurchaseList() {
        boolean yn = ConsoleUtils.promptYN("Do you want to update the grades of all members? [y(default) / n]:");
        if (yn) {
            memberService.updateMemberGrades();
        } else {
            System.out.println("Canceled");
        }
    }
}
