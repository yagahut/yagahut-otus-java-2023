package ru.otus.exception;

public class AssertionException extends RuntimeException {
    public AssertionException(Object expected, Object actual) {
        super("""
                Assertion failed!
                expected=%s,
                actual=%s""".formatted(expected, actual));
    }
}
