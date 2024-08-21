package com.sanyavertolet.interview.data.value;

import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.exceptions.expressions.ValueCastException;

import java.util.Objects;

public final class BooleanValue extends Value {
    private final Boolean value;

    public BooleanValue(Boolean value) {
        this.value = value;
    }

    @Override
    public Value plus(Value other) throws ValueCastException {
        if (other instanceof BooleanValue || other instanceof IntegerValue) {
            return Value.of(asInteger() + other.asInteger());
        } else if (other instanceof DoubleValue) {
            return Value.of(asDouble() + other.asDouble());
        }
        return super.plus(other);
    }

    public Value minus(Value other) throws ValueCastException {
        if (other instanceof BooleanValue || other instanceof IntegerValue) {
            return Value.of(asInteger() - other.asInteger());
        } else if (other instanceof DoubleValue) {
            return Value.of(asDouble() - other.asDouble());
        }
        return super.minus(other);
    }

    @Override
    public Value multiply(Value other) throws ValueCastException {
        if (other instanceof BooleanValue || other instanceof IntegerValue) {
            return Value.of(asInteger() * other.asInteger());
        } else if (other instanceof DoubleValue) {
            return Value.of(asDouble() * other.asDouble());
        }
        return super.multiply(other);
    }

    public Value divide(Value other) throws ExpressionEvaluationException {
        if (other.asDouble() == 0.0) {
            throw new ExpressionEvaluationException("Division by zero");
        }
        if (other instanceof BooleanValue || other instanceof IntegerValue) {
            return Value.of(asInteger() / other.asInteger());
        } else if (other instanceof DoubleValue) {
            return Value.of(asDouble() / other.asDouble());
        }
        return super.divide(other);
    }

    public Value pow(Value other) throws ValueCastException {
        if (other instanceof IntegerValue || other instanceof DoubleValue || other instanceof BooleanValue) {
            return Value.of(Math.pow(asDouble(), other.asDouble()));
        }
        return super.pow(other);
    }

    public Value lt(Value other) throws ValueCastException {
        if (other instanceof IntegerValue || other instanceof DoubleValue || other instanceof BooleanValue) {
            return Value.of(asDouble() < other.asDouble());
        }
        return super.lt(other);
    }
    public Value gt(Value other) throws ValueCastException {
        if (other instanceof IntegerValue || other instanceof DoubleValue) {
            return Value.of(asDouble() > other.asDouble());
        }
        return super.gt(other);
    }
    public Value leq(Value other) throws ValueCastException {
        if (other instanceof IntegerValue || other instanceof DoubleValue || other instanceof BooleanValue) {
            return Value.of(asDouble() <= other.asDouble());
        }
        return super.leq(other);
    }
    public Value geq(Value other) throws ValueCastException {
        if (other instanceof IntegerValue || other instanceof DoubleValue || other instanceof BooleanValue) {
            return Value.of(asDouble() >= other.asDouble());
        }
        return super.geq(other);
    }

    @Override
    public Boolean asBoolean() {
        return value;
    }

    @Override
    public Double asDouble() {
        return value ? 1.0 : 0.0;
    }

    @Override
    public Integer asInteger() {
        return value ? 1 : 0;
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
        if (obj instanceof BooleanValue booleanValue) {
            return asBoolean() == booleanValue.asBoolean();
        } else if (obj instanceof Boolean booleanClass) {
            return asBoolean() == booleanClass;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
