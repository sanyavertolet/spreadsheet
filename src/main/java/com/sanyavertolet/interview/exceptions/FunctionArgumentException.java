package com.sanyavertolet.interview.exceptions;

/**
 * Exception thrown when a function receives an incorrect number of arguments.
 */
public class FunctionArgumentException extends Exception {

    /**
     * Constructs a new {@code FunctionArgumentException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public FunctionArgumentException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code FunctionArgumentException} with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    @SuppressWarnings("unused")
    public FunctionArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
