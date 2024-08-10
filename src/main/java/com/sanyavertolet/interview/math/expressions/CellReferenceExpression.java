package com.sanyavertolet.interview.math.expressions;

import com.sanyavertolet.interview.data.manager.DataAccessor;
import com.sanyavertolet.interview.exceptions.DataAccessException;
import com.sanyavertolet.interview.exceptions.ExpressionEvaluationException;
import com.sanyavertolet.interview.math.CellReference;

public class CellReferenceExpression extends Expression {
    private final CellReference cellReference;
    private final DataAccessor dataAccessor;

    public CellReferenceExpression(CellReference cellReference, DataAccessor dataAccessor) {
        this.cellReference = cellReference;
        this.dataAccessor = dataAccessor;
    }

    @Override
    public void recalculate() throws ExpressionEvaluationException {
        try {
            value = dataAccessor.getDoubleCellValue(cellReference);
        } catch (DataAccessException exception) {
            throw new ExpressionEvaluationException(exception.getMessage());
        }
    }

    @Override
    public String prettyPrint(int shift) {
        return ".".repeat(shift) + cellReference.identifier() + "<" + value + ">" + "\n";
    }

    public CellReference getCellReference() {
        return cellReference;
    }
}
