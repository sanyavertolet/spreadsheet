package com.sanyavertolet.interview.expressions;

public class CellReferenceExpression extends Expression {
    private final String cellReference;

    public CellReferenceExpression(String cellReference) {
        this.cellReference = cellReference;
    }

    @Override
    public Double evaluate() {
        return 0.0;
    }
}
