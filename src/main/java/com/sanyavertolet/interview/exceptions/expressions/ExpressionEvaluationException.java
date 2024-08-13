package com.sanyavertolet.interview.exceptions.expressions;

public class ExpressionEvaluationException extends Exception {
    @SuppressWarnings("unused")
    public ExpressionEvaluationException(String message) {
        super(message);
    }

    @SuppressWarnings("unused")
    public ExpressionEvaluationException(String message, Throwable cause) {
        super(message, cause);
    }
}
