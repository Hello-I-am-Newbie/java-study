package com.onlinejava.project.bookstore.adapters.cli;

import com.onlinejava.project.bookstore.core.cli.CliCommandInterface;

public class QuitCommand implements CliCommandInterface {
    @Override
    public String getCommandID() {
        return "q";
    }

    @Override
    public String getTitle() {
        return "Quit";
    }

    @Override
    public String getDescription() {
        return "Exit The Program";
    }

    @Override
    public void run() {
        System.exit(0);
    }
}
