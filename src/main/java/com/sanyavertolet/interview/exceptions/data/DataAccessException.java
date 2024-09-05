package com.sanyavertolet.interview.exceptions.data;

import java.io.Serial;

/**
 * Custom exception to handle data access related errors.
 */
public class DataAccessException extends Exception {
    @Serial
    private static final long serialVersionUID = 42L;

    /**
     * Constructs a new {@code DataAccessException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public DataAccessException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code DataAccessException} with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    @SuppressWarnings("unused")
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
