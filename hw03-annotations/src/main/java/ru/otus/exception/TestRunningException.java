package ru.otus.exception;

public class TestRunningException extends RuntimeException {
    public TestRunningException(String message, Throwable cause) {
        super(message, cause);
    }
}
