package com.sanyavertolet.interview.data.value;

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
    public Value eq(Value other) {
        return Value.of(asString().equals(other.asString()));
    }

    @Override
    public Value neq(Value other) {
        return Value.of(!asString().equals(other.asString()));
    }

    @Override
    public String asString() {
        return value;
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
