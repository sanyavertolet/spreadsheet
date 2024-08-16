package com.sanyavertolet.interview.math.expressions;

import com.sanyavertolet.interview.data.value.Value;
import com.sanyavertolet.interview.math.CellReference;

import java.util.ArrayList;
import java.util.List;

public class ValueExpression extends Expression {
    private final Value value;

    public ValueExpression(String value) {
        this.value = Value.parse(value);
    }

    public Value getValue() {
        return value;
    }

    @Override
    public String prettyPrint(int shift) {
        return ".".repeat(shift) + value + "\n";
    }

    @Override
    public List<CellReference> getCellReferences() {
        return new ArrayList<>();
    }
}
