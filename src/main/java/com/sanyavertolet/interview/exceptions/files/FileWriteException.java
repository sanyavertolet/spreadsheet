package com.sanyavertolet.interview.exceptions.files;

import java.io.Serial;

/**
 * Exception thrown when there is an error writing data to a file.
 */
public class FileWriteException extends FileException {
    @Serial
    private static final long serialVersionUID = 42L;

    /**
     * Constructs a new {@code FileWriteException} with the specified detail message.
     *
     * @param message the detail message.
     */
    @SuppressWarnings("unused")
    public FileWriteException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code FileWriteException} with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public FileWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
