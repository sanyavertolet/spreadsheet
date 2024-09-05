package com.sanyavertolet.interview.exceptions.data;

import java.io.Serial;

/**
 * Exception thrown when there is a problem with data dependencies.
 */
public class DataDependencyException extends Exception {
    @Serial
    private static final long serialVersionUID = 42L;

    /**
     * Constructs a new {@code DataDependencyException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public DataDependencyException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code DataDependencyException} with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    @SuppressWarnings("unused")
    public DataDependencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
