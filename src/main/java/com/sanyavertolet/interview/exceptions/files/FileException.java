package com.sanyavertolet.interview.exceptions.files;

import java.io.Serial;

/**
 * Base class for exceptions related to file operations.
 */
public class FileException extends Exception {
    @Serial
    private static final long serialVersionUID = 42L;

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
