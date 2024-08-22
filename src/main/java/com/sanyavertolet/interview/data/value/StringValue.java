package com.sanyavertolet.interview.data.value;

import java.util.Objects;

/**
 * A final class representing a string value in an expression. The {@code StringValue} class extends
 * the {@link Value} class and provides specific implementations for handling string data.
 */
public final class StringValue extends Value {
    private final String value;

    /**
     * Constructs a {@code StringValue} with the specified string.
     *
     * @param value the string value to be represented by this {@code StringValue}.
     */
    public StringValue(String value) {
        this.value = value;
    }

    /**
     * Returns the string representation of this value.
     *
     * @return the string value.
     */
    @Override
    public String asString() {
        return value;
    }

    /**
     * Returns a string representation of this {@code StringValue} object.
     *
     * @return the string value.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Compares this {@code StringValue} to another object for equality.
     * Returns {@code true} if the other object is also a {@code StringValue} or a {@code String}
     * and their string representations are equal.
     *
     * @param obj the object to compare with this {@code StringValue}.
     * @return {@code true} if the specified object is equal to this {@code StringValue}; {@code false} otherwise.
     */
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

    /**
     * Returns the hash code for this {@code StringValue}.
     *
     * @return the hash code of the string value.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
