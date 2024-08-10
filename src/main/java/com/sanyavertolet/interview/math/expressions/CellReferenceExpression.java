package com.sanyavertolet.interview.math.expressions;

import com.sanyavertolet.interview.math.CellReference;

public class CellReferenceExpression extends Expression {
    private final CellReference cellReference;

    public CellReferenceExpression(CellReference cellReference) {
        this.cellReference = cellReference;
    }

    @Override
    public String prettyPrint(int shift) {
        return ".".repeat(shift) + cellReference.identifier() + "\n";
    }

    public CellReference getCellReference() {
        return cellReference;
    }
}
