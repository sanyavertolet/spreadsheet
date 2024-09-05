package com.sanyavertolet.interview.exceptions.expressions;

import com.sanyavertolet.interview.data.value.Value;

import java.io.Serial;

/**
 * Exception thrown when an invalid type cast operation is attempted on {@link Value} objects.
 */
public class ValueCastException extends ExpressionEvaluationException {
    @Serial
    private static final long serialVersionUID = 42L;

    /**
     * Constructs a new {@code ValueCastException} with a detailed message about the unsupported operation.
     *
     * @param operationName the name of the operation that caused the exception.
     * @param left          the left operand involved in the operation.
     * @param right         the right operand involved in the operation.
     */
    public ValueCastException(String operationName, Value left, Value right) {
        super("Operation " + operationName + " is not supported with " + left.getClass().getSimpleName() + " and " + right.getClass().getSimpleName() + " types");
    }

    /**
     * Constructs a new {@code ValueCastException} with a detailed message about the casting failure.
     *
     * @param value        the value that could not be cast.
     * @param desiredClass the class to which the value was attempted to be cast.
     */
    public ValueCastException(Value value, Class<?> desiredClass) {
        super("Cannot cast " + value.getClass().getSimpleName() + " to " + desiredClass.getSimpleName());
    }

    /**
     * Constructs a new {@code ValueCastException} with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    @SuppressWarnings("unused")
    public ValueCastException(String message, Throwable cause) {
        super(message, cause);
    }
}
