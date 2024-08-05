package com.sanyavertolet.interview.math.expressions;

public class CellReferenceExpression extends Expression {
    private final String cellReference;

    public CellReferenceExpression(String cellReference) {
        this.cellReference = cellReference;
    }

    @Override
    public void recalculate() {
        value = 0.0;
    }
}
