package com.sanyavertolet.interview.exceptions;

public class CellAccessException extends Exception {
    @SuppressWarnings("unused")
    public CellAccessException(String message) {
        super(message);
    }

    @SuppressWarnings("unused")
    public CellAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
