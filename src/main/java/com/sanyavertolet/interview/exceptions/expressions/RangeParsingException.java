package com.sanyavertolet.interview.exceptions.expressions;

import com.sanyavertolet.interview.math.CellReference;

import java.io.Serial;

/**
 * Exception thrown when an error occurs while parsing a range expression.
 */
public class RangeParsingException extends ExpressionParsingException {
    @Serial
    private static final long serialVersionUID = 42L;

    /**
     * Constructs a new {@code RangeParsingException} with a detailed message about the invalid range.
     *
     * @param from the starting cell reference of the range.
     * @param to   the ending cell reference of the range.
     */
    public RangeParsingException(CellReference from, CellReference to) {
        super("Could not construct RangeExpression " + from + ":" + to);
    }

    /**
     * Constructs a new {@code RangeParsingException} with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    @SuppressWarnings("unused")
    public RangeParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
