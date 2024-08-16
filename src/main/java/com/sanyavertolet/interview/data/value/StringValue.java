package com.sanyavertolet.interview.data.value;

import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.exceptions.expressions.ValueCastException;

import java.util.Objects;

public final class StringValue extends Value {
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Value plus(Value other) throws ExpressionEvaluationException {
        throw new ValueCastException("+ (plus)", this, other);
    }

    @Override
    public Value minus(Value other) throws ExpressionEvaluationException {
        throw new ValueCastException("- (minus)", this, other);
    }

    @Override
    public Value multiply(Value other) throws ExpressionEvaluationException {
        throw new ValueCastException("* (multiply)", this, other);
    }

    @Override
    public Value divide(Value other) throws ExpressionEvaluationException {
        throw new ValueCastException("/ (divide)", this, other);
    }

    @Override
    public Value pow(Value other) throws ExpressionEvaluationException {
        throw new ValueCastException("^ (power)", this, other);
    }

    @Override
    public Double asDouble() throws ExpressionEvaluationException {
        throw new ValueCastException(this, Double.class);
    }

    @Override
    public Integer asInteger() throws ExpressionEvaluationException {
        throw new ValueCastException(this, Integer.class);
    }

    @Override
    public String asString() {
        return value;
    }

    @Override
    public Boolean asBoolean() throws ExpressionEvaluationException {
        throw new ValueCastException(this, Boolean.class);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof StringValue stringValue) {
            return asString().equals(stringValue.asString());
        } else if (obj instanceof String string) {
            return asString().equals(string);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
