package com.sanyavertolet.interview.data.value;

import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;

import java.util.Objects;

public final class DoubleValue extends Value {
    private Double value;

    public DoubleValue(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public Value plus(Value other) throws ExpressionEvaluationException {
        return Value.of(value + other.asDouble());
    }

    @Override
    public Value minus(Value other) throws ExpressionEvaluationException {
        return Value.of(value - other.asDouble());
    }

    @Override
    public Value multiply(Value other) throws ExpressionEvaluationException {
        return Value.of(value * other.asDouble());
    }

    @Override
    public Value divide(Value other) throws ExpressionEvaluationException {
        if (other.asDouble() == 0.0) {
            throw new ExpressionEvaluationException("Division by zero");
        }
        return Value.of(value / other.asDouble());
    }

    @Override
    public Value pow(Value other) throws ExpressionEvaluationException {
        return Value.of(Math.pow(value, other.asDouble()));
    }

    @Override
    public Double asDouble() {
        return value;
    }

    @Override
    public Integer asInteger() {
        return value.intValue();
    }

    @Override
    public String asString() {
        return value.toString();
    }

    @Override
    public Boolean asBoolean() {
        return value != 0.0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof IntegerValue integerValue) {
            return asDouble().equals(integerValue.asDouble());
        } else if (obj instanceof DoubleValue doubleValue) {
            return asDouble().equals(doubleValue.asDouble());
        } else if (obj instanceof Double doubleClass) {
            return asDouble().equals(doubleClass);
        } else if (obj instanceof Integer integer) {
            return asDouble().equals(integer.doubleValue());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
