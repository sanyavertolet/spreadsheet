package com.sanyavertolet.interview.data.value;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class IterableValue extends Value {
    private final List<Value> values;

    public IterableValue(List<Value> values) {
        this.values = values;
    }

    public List<Value> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return values.stream().map(Value::toString).collect(Collectors.joining(", ", "[", "]"));
    }

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

    @Override
    public int hashCode() {
        int hash = Objects.hash(values.size());
        for (Value value : values) {
            hash = 31 * hash + value.hashCode();
        }
        return hash;
    }
}
