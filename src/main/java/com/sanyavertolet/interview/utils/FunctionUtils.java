package com.sanyavertolet.interview.utils;

import com.sanyavertolet.interview.data.value.*;
import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;

import java.util.List;

public class FunctionUtils {
    private FunctionUtils() {}

    public static Value sum(List<Value> values) throws ExpressionEvaluationException {
        if (!(values.get(0) instanceof IterableValue iterableValue)) {
            throw new ExpressionEvaluationException("SUM function should have only one valid range argument");
        }
        return sum(iterableValue);
    }

    public static Value average(List<Value> values) throws ExpressionEvaluationException {
        if (!(values.get(0) instanceof IterableValue iterableValue)) {
            throw new ExpressionEvaluationException("SUM function should have only one valid range argument");
        }
        return sum(iterableValue).divide(Value.of((double) iterableValue.getValues().size()));
    }

    private static Value sum(IterableValue iterableValue) throws ExpressionEvaluationException {
        Value accumulator = new IntegerValue(0);
        for (Value value : iterableValue.getValues()) {
            accumulator = accumulator.plus(value);
        }
        return accumulator;
    }

    public static Value count(List<Value> values) throws ExpressionEvaluationException {
        if (!(values.get(0) instanceof IterableValue iterableValue)) {
            throw new ExpressionEvaluationException("SUM function should have only one valid range argument");
        }
        int counter = 0;
        for (Value value : iterableValue.getValues()) {
            if (value instanceof IntegerValue || value instanceof DoubleValue || value instanceof BooleanValue) {
                counter += 1;
            }
        }
        return Value.of(counter);
    }
}
