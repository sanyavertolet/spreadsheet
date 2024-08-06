package com.sanyavertolet.interview.math.expressions;

import com.sanyavertolet.interview.cells.cellmanager.CellAccessor;
import com.sanyavertolet.interview.exceptions.CellAccessException;
import com.sanyavertolet.interview.exceptions.EvaluationException;
import com.sanyavertolet.interview.math.CellReference;

public class CellReferenceExpression extends Expression {
    private final CellReference cellReference;
    private final CellAccessor cellAccessor;

    public CellReferenceExpression(CellReference cellReference, CellAccessor cellAccessor) {
        this.cellReference = cellReference;
        this.cellAccessor = cellAccessor;
    }

    @Override
    public void recalculate() throws EvaluationException {
        try {
            value = cellAccessor.getDoubleCellValue(cellReference);
        } catch (CellAccessException exception) {
            throw new EvaluationException(exception.getMessage());
        }
    }

    @Override
    public String prettyPrint(int shift) {
        return ".".repeat(shift) + cellReference.identifier() + "<" + value + ">" + "\n";
    }
}
