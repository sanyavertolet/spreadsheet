package com.sanyavertolet.interview.exceptions.expressions;

import java.io.Serial;

/**
 * Exception thrown when there is an error in evaluating an expression.
 */
public class ExpressionEvaluationException extends Exception {
    @Serial
    private static final long serialVersionUID = 42L;

    /**
     * Constructs a new {@code ExpressionEvaluationException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public ExpressionEvaluationException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code ExpressionEvaluationException} with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public ExpressionEvaluationException(String message, Throwable cause) {
        super(message, cause);
    }
}
