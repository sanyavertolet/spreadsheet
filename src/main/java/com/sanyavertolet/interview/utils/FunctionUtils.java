package com.sanyavertolet.interview.utils;

import com.sanyavertolet.interview.data.value.IntegerValue;
import com.sanyavertolet.interview.data.value.IterableValue;
import com.sanyavertolet.interview.data.value.Value;
import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;

import java.util.List;

public class FunctionUtils {
    private FunctionUtils() {}

    public static Value sum(List<Value> values) throws ExpressionEvaluationException {
        if (!(values.get(0) instanceof IterableValue iterableValue)) {
            throw new ExpressionEvaluationException("SUM function should have only one valid range argument");
        }
        Value accumulator = new IntegerValue(0);
        for (Value value : iterableValue.getValue()) {
            accumulator = accumulator.plus(value);
        }
        return accumulator;
    }
}
