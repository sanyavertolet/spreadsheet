package com.sanyavertolet.interview.math.expressions;

public class NumberExpression extends Expression {
    public NumberExpression(String value) {
        this.value = Double.parseDouble(value);
    }

    @Override
    public void recalculate() { }

    @Override
    public String prettyPrint(int shift) {
        return ".".repeat(shift) + value + "\n";
    }
}
