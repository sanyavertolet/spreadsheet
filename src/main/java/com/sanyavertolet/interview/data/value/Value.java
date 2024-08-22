package com.sanyavertolet.interview.data.value;

import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.exceptions.expressions.ValueCastException;

public sealed abstract class Value permits BooleanValue, DoubleValue, StringValue, IntegerValue, IterableValue {
    public Value plus(Value other) throws ValueCastException {
        throw new ValueCastException("+ (plus)", this, other);
    }
    public Value minus(Value other) throws ValueCastException {
        throw new ValueCastException("- (minus)", this, other);
    }
    public Value multiply(Value other) throws ValueCastException {
        throw new ValueCastException("* (multiply)", this, other);
    }
    public Value divide(Value other) throws ExpressionEvaluationException {
        throw new ValueCastException("/ (divide)", this, other);
    }
    public Value pow(Value other) throws ValueCastException {
        throw new ValueCastException("^ (power)", this, other);
    }

    public Value lt(Value other) throws ValueCastException {
        throw new ValueCastException("< (less than)", this, other);
    }
    public Value gt(Value other) throws ValueCastException {
        throw new ValueCastException("> (greater than)", this, other);
    }
    public Value leq(Value other) throws ValueCastException {
        throw new ValueCastException("<= (less than or equal)", this, other);
    }
    public Value geq(Value other) throws ValueCastException {
        throw new ValueCastException(">= (greater than or equal)", this, other);
    }

    public Value eq(Value other) {
        return Value.of(equals(other));
    }
    public Value neq(Value other) {
        return Value.of(!equals(other));
    }

    public String asString() throws ValueCastException {
        throw new ValueCastException(this, String.class);
    }
    public Double asDouble() throws ValueCastException {
        throw new ValueCastException(this, Double.class);
    }
    public Integer asInteger() throws ValueCastException {
        throw new ValueCastException(this, Integer.class);
    }
    public  Boolean asBoolean() throws ValueCastException {
        throw new ValueCastException(this, Boolean.class);
    }

    public static Value of(Double value) {
        return value.intValue() == value ? new IntegerValue(value.intValue()) : new DoubleValue(value);
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
}
