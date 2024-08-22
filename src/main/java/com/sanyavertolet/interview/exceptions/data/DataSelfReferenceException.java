package com.sanyavertolet.interview.exceptions.data;

import com.sanyavertolet.interview.math.CellReference;

/**
 * Exception thrown when a data item references itself, which is not allowed.
 */
public class DataSelfReferenceException extends DataDependencyException {

    /**
     * Constructs a new {@code DataSelfReferenceException} with the specified detail message.
     *
     * @param message the detail message.
     */
    @SuppressWarnings("unused")
    public DataSelfReferenceException(String message) {
        super(message);
    }

    public DataSelfReferenceException(CellReference reference) {
        super("Cell cannot depend on itself: " + reference);
    }
}
