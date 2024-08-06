package com.sanyavertolet.interview.exceptions;

public class ValueTypeException extends RuntimeException {
    @SuppressWarnings("unused")
    public ValueTypeException(String message) {
        super(message);
    }

    @SuppressWarnings("unused")
    public ValueTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
