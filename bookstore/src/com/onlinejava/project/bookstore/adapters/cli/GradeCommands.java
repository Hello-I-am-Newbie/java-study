package com.onlinejava.project.bookstore.adapters.cli;

import com.onlinejava.project.bookstore.application.domain.BookStoreFactory;
import com.onlinejava.project.bookstore.application.ports.input.MemberUseCase;
import com.onlinejava.project.bookstore.core.cli.CliCommand;
import static com.onlinejava.project.bookstore.application.domain.BookStoreApplication.scanner;


@CliCommand
public class GradeCommands {
    private MemberUseCase memberService;
    public GradeCommands() {
        this.memberService = BookStoreFactory.lookup(MemberUseCase.class);
    }
    @CliCommand(ID = "13", title = "Update member's grades")
    public void printUsersPurchaseList() {
        System.out.print("Do you want to update the grades of all members? [y(default) / n]:");
        String yn = scanner.nextLine().trim();
        if (yn.equalsIgnoreCase("Y")) {
            memberService.updateMemberGrades();
        } else {
            System.out.println("Canceled");
        }
    }
}
