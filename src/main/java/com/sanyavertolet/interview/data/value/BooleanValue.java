package com.sanyavertolet.interview.data.value;

import com.sanyavertolet.interview.exceptions.expressions.ValueCastException;

import java.util.Objects;

public final class BooleanValue extends Value {
    private final Boolean value;

    public BooleanValue(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }

    @Override
    public Value plus(Value other) throws ValueCastException {
        if (other instanceof BooleanValue otherValue) {
            return Value.of(value || otherValue.value);
        }
        throw new ValueCastException("+ (plus)", this, other);
    }

    @Override
    public Value multiply(Value other) throws ValueCastException {
        if (other instanceof BooleanValue otherValue) {
            return Value.of(value && otherValue.value);
        }
        throw new ValueCastException("* (multiply)", this, other);
    }

    @Override
    public String asString() {
        return value.toString();
    }

    @Override
    public Boolean asBoolean() {
        return value;
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
