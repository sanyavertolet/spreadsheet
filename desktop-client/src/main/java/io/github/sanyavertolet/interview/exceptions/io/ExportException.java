package io.github.sanyavertolet.interview.exceptions.io;

import java.io.Serial;

/**
 * Exception thrown when an error occurs during a data write operation.
 *
 * <p>This exception is not limited to file operations and can be used
 * in any context where data writing is involved. Common scenarios include
 * failures in writing to streams, buffers, network connections, or
 * other output destinations. The cause of the exception might be
 * I/O-related, permission issues, or any other condition that prevents
 * successful completion of the write operation.</p>
 */
public class ExportException extends Exception {
    @Serial
    private static final long serialVersionUID = 42L;

    /**
     * Constructs a new {@code WriteException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the write failure.
     */
    @SuppressWarnings("unused")
    public ExportException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code WriteException} with the specified detail message and cause.
     *
     * @param message the detail message explaining the reason for the write failure.
     * @param cause   the underlying cause of this exception.
     */
    public ExportException(String message, Throwable cause) {
        super(message, cause);
    }
}
