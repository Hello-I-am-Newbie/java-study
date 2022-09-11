package com.onlinejava.project.bookstore.cli.commands;

import com.onlinejava.project.bookstore.core.cli.CliCommandInterface;

public class SaveCommand implements CliCommandInterface {

    @Override
    public String getCommandID() {
        return "s";
    }

    @Override
    public String getTitle() {
        return "Save";
    }

    @Override
    public String getDescription() {
        return "Save The Changes";
    }

    @Override
    public void run() {
        bookstore.saveAsFile();
    }
}
