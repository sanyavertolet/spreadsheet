package com.sanyavertolet.interview.data.value;

import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.exceptions.expressions.ValueCastException;

import java.util.Objects;

public final class IntegerValue extends Value {
    private final Integer value;

    public IntegerValue(Integer value) {
        this.value = value;
    }

    @Override
    public Value plus(Value other) throws ValueCastException {
        if (other instanceof DoubleValue) {
            return Value.of(value + other.asDouble());
        }
        return Value.of(value + other.asInteger());
    }

    @Override
    public Value minus(Value other) throws ValueCastException {
        if (other instanceof DoubleValue) {
            return Value.of(value - other.asDouble());
        }
        return Value.of(value - other.asInteger());
    }

    @Override
    public Value multiply(Value other) throws ValueCastException {
        if (other instanceof DoubleValue) {
            return Value.of(value * other.asDouble());
        }
        return Value.of(value * other.asInteger());
    }

    @Override
    public Value divide(Value other) throws ExpressionEvaluationException {
        if (other instanceof DoubleValue) {
            if (other.asDouble() == 0.0) {
                throw new ExpressionEvaluationException("Division by zero");
            }
            return Value.of(value / other.asDouble());
        }
        if (other.asInteger() == 0.0) {
            throw new ExpressionEvaluationException("Division by zero");
        }
        return Value.of(value / other.asInteger());
    }

    @Override
    public Value pow(Value other) throws ValueCastException {
        return Value.of(Math.pow(value, other.asDouble()));
    }

    @Override
    public Value lt(Value other) throws ValueCastException {
        return Value.of(asDouble() < other.asDouble());
    }

    @Override
    public Value gt(Value other) throws ValueCastException {
        return Value.of(asDouble() > other.asDouble());
    }

    @Override
    public Value leq(Value other) throws ValueCastException {
        return Value.of(asDouble() <= other.asDouble());
    }

    @Override
    public Value geq(Value other) throws ValueCastException {
        return Value.of(asDouble() >= other.asDouble());
    }

    @Override
    public Double asDouble() {
        return value.doubleValue();
    }

    @Override
    public Integer asInteger() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof IntegerValue integerValue) {
            return asInteger().equals(integerValue.asInteger());
        } else if (obj instanceof DoubleValue doubleValue) {
            return asDouble().equals(doubleValue.asDouble());
        } else if (obj instanceof Double doubleClass) {
            return asDouble().equals(doubleClass);
        } else if (obj instanceof Integer integer) {
            return asInteger().equals(integer);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
