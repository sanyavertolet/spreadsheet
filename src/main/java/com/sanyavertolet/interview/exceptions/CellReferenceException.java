package com.sanyavertolet.interview.exceptions;

/**
 * Exception thrown when there is an issue with cell references, such as invalid coordinates or identifiers.
 */
public class CellReferenceException extends Exception {

    /**
     * Constructs a new {@code CellReferenceException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public CellReferenceException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CellReferenceException} with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public CellReferenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
