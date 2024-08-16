package com.sanyavertolet.interview.data.value;

import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;

public sealed abstract class Value permits BooleanValue, DoubleValue, StringValue, IntegerValue, IterableValue {
    public abstract Value plus(Value other) throws ExpressionEvaluationException;
    public abstract Value minus(Value other) throws ExpressionEvaluationException;
    public abstract Value multiply(Value other) throws ExpressionEvaluationException;
    public abstract Value divide(Value other) throws ExpressionEvaluationException;
    public abstract Value pow(Value other) throws ExpressionEvaluationException;

    public abstract Double asDouble() throws ExpressionEvaluationException;
    public abstract Integer asInteger() throws ExpressionEvaluationException;
    public abstract String asString();
    public abstract Boolean asBoolean() throws ExpressionEvaluationException;

    public static Value of(Double value) {
        return new DoubleValue(value);
    }

    public static Value of(String value) {
        return new StringValue(value);
    }

    public static Value of(Boolean value) {
        return new BooleanValue(value);
    }

    public static Value of(Integer value) {
        return new IntegerValue(value);
    }

    public static Value parse(String valueText) {
        if (valueText.trim().equalsIgnoreCase("true")) {
            return Value.of(true);
        } else if (valueText.trim().equalsIgnoreCase("false")) {
            return Value.of(false);
        }

        try {
            return Value.of(Integer.valueOf(valueText));
        } catch (NumberFormatException ignored) { }

        try {
            return Value.of(Double.valueOf(valueText));
        } catch (NumberFormatException ignored) { }
        return Value.of(valueText);
    }

    @Override
    public String toString() {
        return asString();
    }
}
