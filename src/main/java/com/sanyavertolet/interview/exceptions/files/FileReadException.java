package com.sanyavertolet.interview.exceptions.files;

/**
 * Exception thrown when there is an error reading data from a file.
 */
public class FileReadException extends FileException {

    /**
     * Constructs a new {@code FileReadException} with the specified detail message.
     *
     * @param message the detail message.
     */
    @SuppressWarnings("unused")
    public FileReadException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code FileReadException} with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public FileReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
