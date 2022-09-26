package com.onlinejava.project.bookstore.core.cli;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static com.onlinejava.project.bookstore.application.domain.BookStoreApplication.scanner;


public class CommandInvocationHandler implements InvocationHandler {

    private CliCommandInterface cliCommandInstance;
    public CommandInvocationHandler(CliCommandInterface cliCommandInstance) {
        this.cliCommandInstance = cliCommandInstance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        boolean target = method.getName().equals("run") && method.getParameterCount() == 0;
        if( !target ){
            return method.invoke(cliCommandInstance, args);
        }
        Object result = method.invoke(cliCommandInstance);
        System.out.println("Press enter for the menu");
        scanner.nextLine();

        return result;
    }
}
