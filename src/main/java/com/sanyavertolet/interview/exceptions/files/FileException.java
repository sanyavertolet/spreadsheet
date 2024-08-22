package com.sanyavertolet.interview.exceptions.files;

/**
 * Base class for exceptions related to file operations.
 */
public class FileException extends Exception {

    /**
     * Constructs a new {@code FileException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public FileException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code FileException} with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
}
