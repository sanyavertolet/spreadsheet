package com.sanyavertolet.interview.math.expressions;

import com.sanyavertolet.interview.exceptions.CellReferenceException;
import com.sanyavertolet.interview.exceptions.expressions.RangeParsingException;
import com.sanyavertolet.interview.math.CellReference;

import java.util.ArrayList;
import java.util.List;

public class RangeExpression extends Expression {
    private final CellReference startCell;
    private final CellReference endCell;

    public RangeExpression(CellReference startCell, CellReference endCell) throws RangeParsingException {
        if (startCell == null || endCell == null || startCell.equals(endCell)) {
            throw new RangeParsingException(startCell, endCell);
        }
        if (startCell.row() > endCell.row() || startCell.column() > endCell.column()) {
            throw new RangeParsingException(startCell, endCell);
        }
        this.startCell = startCell;
        this.endCell = endCell;
    }

    public CellReference getFrom() {
        return this.startCell;
    }

    public CellReference getTo() {
        return this.endCell;
    }

    public Boolean isRow() {
        return this.startCell.row() == this.endCell.row();
    }

    public Boolean isColumn() {
        return this.startCell.column() == this.endCell.column();
    }

    public Integer size() {
        return (endCell.row() - startCell.row() + 1) * (endCell.column() - startCell.column() + 1);
    }

    @Override
    public String prettyPrint(int shift) {
        return ".".repeat(shift) + startCell + ":" + endCell;
    }

    @Override
    public List<CellReference> getCellReferences() {
        ArrayList<CellReference> cellReferences = new ArrayList<>();
        try {
            for (int row = startCell.row(); row <= endCell.row(); row++) {
                for (int column = startCell.column(); column <= endCell.column(); column++) {
                    cellReferences.add(CellReference.of(row, column));
                }
            }
        } catch (CellReferenceException e) {
            return null;
        }
        return cellReferences;
    }
}
