package com.sanyavertolet.interview.exceptions;

public class DataSelfReferenceException extends DataDependencyException {
    @SuppressWarnings("unused")
    public DataSelfReferenceException(String message) {
        super(message);
    }

    @SuppressWarnings("unused")
    public DataSelfReferenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
