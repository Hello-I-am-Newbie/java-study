package com.onlinejava.project.bookstore.core.util;

public class StringUtils {
    public static String toCapitalize(String name) {
        String first = name.substring(0, 1).toUpperCase();
        String tail = name.substring(1);
        return first + tail;
    }
}
