package com.sanyavertolet.interview.data.value;

import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.exceptions.expressions.ValueCastException;

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
        return values.stream().map(Value::asString).collect(Collectors.joining(", ", "[", "]"));
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
