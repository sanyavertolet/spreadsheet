package com.sanyavertolet.interview.exceptions.expressions;

import java.io.Serial;

/**
 * Exception thrown when an error occurs while parsing an expression.
 */
public class ExpressionParsingException extends Exception {
    @Serial
    private static final long serialVersionUID = 42L;

    /**
     * Constructs a new {@code ExpressionParsingException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public ExpressionParsingException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code ExpressionParsingException} with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public ExpressionParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
