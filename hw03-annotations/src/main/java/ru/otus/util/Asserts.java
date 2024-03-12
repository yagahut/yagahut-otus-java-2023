package ru.otus.util;

import ru.otus.exception.AssertionException;

public class Asserts {
    public static void assertEquals(Object expected, Object actual) {
        if (!expected.equals(actual)) {
           throw new AssertionException(expected, actual);
        }
    }
}
