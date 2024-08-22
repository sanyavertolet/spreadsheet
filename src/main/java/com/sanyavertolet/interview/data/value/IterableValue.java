package com.sanyavertolet.interview.data.value;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A final class representing a value that holds a collection of {@link Value} instances.
 * The {@code IterableValue} class is used to manage and represent a list of values in an expression.
 */
public final class IterableValue extends Value {
    private final List<Value> values;

    /**
     * Constructs an {@code IterableValue} with the specified list of {@link Value} instances.
     *
     * @param values the list of values to be contained within this {@code IterableValue}.
     */
    public IterableValue(List<Value> values) {
        this.values = values;
    }

    /**
     * Returns the list of {@link Value} instances contained within this {@code IterableValue}.
     *
     * @return the list of values.
     */
    public List<Value> getValues() {
        return values;
    }

    /**
     * Returns a string representation of this {@code IterableValue}.
     * The string representation is a comma-separated list of the values, enclosed in square brackets.
     *
     * @return the string representation of this {@code IterableValue}.
     */
    @Override
    public String toString() {
        return values.stream()
                .map(Value::toString)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    /**
     * Compares this {@code IterableValue} to another object for equality.
     * Returns {@code true} if the other object is also an {@code IterableValue} and contains the same
     * sequence of {@link Value} instances.
     *
     * @param obj the object to compare with this {@code IterableValue}.
     * @return {@code true} if the specified object is equal to this {@code IterableValue}; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IterableValue iterableValue)) {
            return false;
        }
        if (values.size() != iterableValue.getValues().size()) {
            return false;
        }
        for (int i = 0; i < values.size(); i++) {
            if (!values.get(i).equals(iterableValue.getValues().get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the hash code for this {@code IterableValue}.
     * The hash code is computed based on the size of the list and the hash codes of its elements.
     *
     * @return the hash code of this {@code IterableValue}.
     */
    @Override
    public int hashCode() {
        int hash = Objects.hash(values.size());
        for (Value value : values) {
            hash = 31 * hash + value.hashCode();
        }
        return hash;
    }
}
