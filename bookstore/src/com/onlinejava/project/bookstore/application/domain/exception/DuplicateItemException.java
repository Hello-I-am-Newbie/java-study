package com.onlinejava.project.bookstore.application.domain.exception;

import java.util.function.Consumer;

import static com.onlinejava.project.bookstore.application.domain.exception.TooManyItemsException.*;

public class DuplicateItemException extends ExpectedException{
    public DuplicateItemException(Term term, String value) {
        super(String.format("The %s [%s] already exist", term.forMessage, value));
    }
    public DuplicateItemException(String message) {
        super(message);
    }

    public static Consumer consumerOf(Term term, String value) {
        return i -> {
            throw new DuplicateItemException(term, value);
        };
    }
}
