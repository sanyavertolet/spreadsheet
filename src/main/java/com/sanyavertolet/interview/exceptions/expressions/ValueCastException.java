package com.sanyavertolet.interview.exceptions.expressions;

import com.sanyavertolet.interview.data.value.Value;

public class ValueCastException extends ExpressionEvaluationException {
    @SuppressWarnings("unused")
    public ValueCastException(String operationName, Value left, Value right) {
        super("Operation " + operationName + " is not supported with " + left.getClass().getSimpleName() + " and " + right.getClass().getSimpleName() + " types");
    }

    public ValueCastException(Value value, Class<?> desiredClass) {
        super("Cannot cast " + value.getClass().getSimpleName() + " to " + desiredClass.getSimpleName());
    }

    @SuppressWarnings("unused")
    public ValueCastException(String message, Throwable cause) {
        super(message, cause);
    }
}
