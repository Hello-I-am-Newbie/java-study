package com.onlinejava.project.bookstore.core.function;

public class Consumers {

    @FunctionalInterface
    public interface ThrowableConsumer<T> {
        void accept(T t) throws Throwable;
    }

    public static <T> java.util.function.Consumer<T> unchecked(ThrowableConsumer<T> consumer) {
        return (T t) -> {
            try {
                consumer.accept(t);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }
}
