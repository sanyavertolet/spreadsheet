package com.sanyavertolet.interview.expressions;

public class NumberExpression extends Expression {
    private final Double value;

    public NumberExpression(Double value) {
        this.value = value;
    }

    public NumberExpression(String value) {
        this.value = Double.parseDouble(value);
    }

    @Override
    public Double evaluate() {
        return value;
    }
}
