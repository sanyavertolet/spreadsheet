package com.sanyavertolet.interview.exceptions.data;

import com.sanyavertolet.interview.math.CellReference;

import java.io.Serial;

/**
 * Exception thrown when a data item references itself, which is not allowed.
 */
public class DataSelfReferenceException extends DataDependencyException {
    @Serial
    private static final long serialVersionUID = 42L;

    /**
     * Constructs a new {@code DataSelfReferenceException} with the specified detail message.
     *
     * @param message the detail message.
     */
    @SuppressWarnings("unused")
    public DataSelfReferenceException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code DataSelfReferenceException} with the specified detail message.
     *
     * @param reference the self-referenced {@code CellReference}.
     */
    public DataSelfReferenceException(CellReference reference) {
        super("Cell cannot depend on itself: " + reference);
    }
}
