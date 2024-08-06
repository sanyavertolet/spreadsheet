package com.sanyavertolet.interview.exceptions;

public class FunctionArgumentException extends Exception {
    @SuppressWarnings("unused")
    public FunctionArgumentException(String message) {
        super(message);
    }

    @SuppressWarnings("unused")
    public FunctionArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
