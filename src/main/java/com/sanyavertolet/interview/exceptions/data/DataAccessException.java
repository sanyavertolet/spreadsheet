package com.sanyavertolet.interview.exceptions.data;

public class DataAccessException extends Exception {
    @SuppressWarnings("unused")
    public DataAccessException(String message) {
        super(message);
    }

    @SuppressWarnings("unused")
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
