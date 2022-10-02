package com.onlinejava.project.bookstore.application.domain.exception;

import static com.onlinejava.project.bookstore.application.domain.exception.TooManyItemsException.*;

public class NotEnoughBooksInStockException extends ExpectedException {
    public NotEnoughBooksInStockException(Term term, String value) {
        super(String.format("The %s [%s] are not enough in stock", term.forMessage, value));
    }
    public NotEnoughBooksInStockException(String message) {
        super(message);
    }
}
