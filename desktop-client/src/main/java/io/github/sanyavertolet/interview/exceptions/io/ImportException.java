package io.github.sanyavertolet.interview.exceptions.io;

import java.io.Serial;

/**
 * Exception thrown when an error occurs during a data read operation.
 *
 * <p>This exception is not limited to file-based reads and can apply
 * to any data source. Possible scenarios include issues reading from
 * streams, buffers, network sources, or other input channels. Underlying
 * causes might range from I/O errors to malformed data, insufficient
 * permissions, or other conditions preventing successful data retrieval.</p>
 */
public class ImportException extends Exception {
    @Serial
    private static final long serialVersionUID = 42L;

    /**
     * Constructs a new {@code ReadException} with the specified detail message.
     *
     * @param message the detail message explaining the cause of this exception.
     */
    @SuppressWarnings("unused")
    public ImportException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code ReadException} with the specified detail message and cause.
     *
     * @param message the detail message providing additional context.
     * @param cause   the underlying exception that triggered this read error.
     */
    public ImportException(String message, Throwable cause) {
        super(message, cause);
    }
}
