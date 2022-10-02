package com.onlinejava.project.bookstore.application.domain.exception;

import java.util.function.Supplier;

import static com.onlinejava.project.bookstore.application.domain.exception.TooManyItemsException.*;

public class NoSuchItemException extends ExpectedException {
    public NoSuchItemException(Term term, String value) {
        super(String.format("The %s [%s] do not exist", term.forMessage, value));
    }

    public NoSuchItemException(String message) {
        super(message);
    }

    public static Supplier<NoSuchItemException> supplierOf(Term term, String value) {
        return () -> new NoSuchItemException(term, value);

    }
}
