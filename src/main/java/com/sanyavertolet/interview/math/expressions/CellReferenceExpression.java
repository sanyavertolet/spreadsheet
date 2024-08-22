package com.sanyavertolet.interview.math.expressions;

import com.sanyavertolet.interview.math.CellReference;

import java.util.ArrayList;
import java.util.List;

public class CellReferenceExpression extends Expression {
    private final CellReference cellReference;

    public CellReferenceExpression(CellReference cellReference) {
        this.cellReference = cellReference;
    }

    @Override
    public String prettyPrint(int shift) {
        return ".".repeat(shift) + this + "\n";
    }

    @Override
    public List<CellReference> getCellReferences() {
        return new ArrayList<>(List.of(cellReference));
    }

    public CellReference getCellReference() {
        return cellReference;
    }

    @Override
    public String toString() {
        return cellReference.identifier();
    }
}
