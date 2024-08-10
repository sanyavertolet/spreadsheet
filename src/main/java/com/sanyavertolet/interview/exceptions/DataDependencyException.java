package com.sanyavertolet.interview.exceptions;

public class DataDependencyException extends Exception {
    @SuppressWarnings("unused")
    public DataDependencyException(String message) {
        super(message);
    }

    @SuppressWarnings("unused")
    public DataDependencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
