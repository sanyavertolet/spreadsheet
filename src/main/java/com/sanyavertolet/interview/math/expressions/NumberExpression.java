package com.sanyavertolet.interview.math.expressions;

public class NumberExpression extends Expression {
    private final Double number;

    public NumberExpression(String value) {
        number = Double.parseDouble(value);
    }

    public Double getNumber() {
        return number;
    }

    @Override
    public String prettyPrint(int shift) {
        return ".".repeat(shift) + number + "\n";
    }
}
