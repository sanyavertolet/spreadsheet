package com.sanyavertolet.interview.exceptions;

public class ParsingException extends Exception {
    @SuppressWarnings("unused")
    public ParsingException(String message) {
        super(message);
    }

    @SuppressWarnings("unused")
    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
