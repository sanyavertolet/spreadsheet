package com.sanyavertolet.interview.exceptions;

public class EvaluationException extends Exception {
    @SuppressWarnings("unused")
    public EvaluationException(String message) {
        super(message);
    }

    @SuppressWarnings("unused")
    public EvaluationException(String message, Throwable cause) {
        super(message, cause);
    }
}
