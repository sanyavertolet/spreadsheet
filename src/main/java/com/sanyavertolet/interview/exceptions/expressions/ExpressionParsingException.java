package com.sanyavertolet.interview.exceptions.expressions;

public class ExpressionParsingException extends Exception {
    @SuppressWarnings("unused")
    public ExpressionParsingException(String message) {
        super(message);
    }

    @SuppressWarnings("unused")
    public ExpressionParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
