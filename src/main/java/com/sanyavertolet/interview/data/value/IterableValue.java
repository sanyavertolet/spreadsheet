package com.sanyavertolet.interview.data.value;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class IterableValue extends Value {
    private final List<Value> values;

    public IterableValue(List<Value> values) {
        this.values = values;
    }

    public List<Value> getValue() {
        return values;
    }

    @Override
    public String asString() {
        return values.stream().map(Value::asString).collect(Collectors.joining(", ", "[", "]"));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IterableValue iterableValue)) {
            return false;
        }
        if (values.size() != iterableValue.getValue().size()) {
            return false;
        }
        for (int i = 0; i < values.size(); i++) {
            if (!values.get(i).equals(iterableValue.getValue().get(i))) {
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
