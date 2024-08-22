package com.sanyavertolet.interview.math.expressions;

import com.sanyavertolet.interview.exceptions.CellReferenceException;
import com.sanyavertolet.interview.exceptions.expressions.RangeParsingException;
import com.sanyavertolet.interview.math.CellReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a range of cells in a spreadsheet, defined by a start and end {@link CellReference}.
 * The {@code RangeExpression} class is a specific type of {@link Expression} that encapsulates
 * a rectangular area of cells.
 */
public class RangeExpression extends Expression {
    private final CellReference startCell;
    private final CellReference endCell;

    /**
     * Constructs a {@code RangeExpression} with the specified start and end cell references.
     * The start cell must be the top-left cell, and the end cell must be the bottom-right cell of the range.
     *
     * @param startCell the start cell reference (top-left) of the range.
     * @param endCell   the end cell reference (bottom-right) of the range.
     * @throws RangeParsingException if the start or end cell is null, if they are equal, or if the start cell is not
     *                               before the end cell in both row and column.
     */
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

    /**
     * Returns the start cell reference of the range.
     *
     * @return the start {@link CellReference}.
     */
    public CellReference getFrom() {
        return this.startCell;
    }

    /**
     * Returns the end cell reference of the range.
     *
     * @return the end {@link CellReference}.
     */
    public CellReference getTo() {
        return this.endCell;
    }

    /**
     * Determines if the range spans a single row.
     *
     * @return {@code true} if the range is a single row; {@code false} otherwise.
     */
    public Boolean isRow() {
        return this.startCell.row() == this.endCell.row();
    }

    /**
     * Determines if the range spans a single column.
     *
     * @return {@code true} if the range is a single column; {@code false} otherwise.
     */
    public Boolean isColumn() {
        return this.startCell.column() == this.endCell.column();
    }

    /**
     * Returns the total number of cells in the range.
     *
     * @return the number of cells in the range.
     */
    public Integer size() {
        return (endCell.row() - startCell.row() + 1) * (endCell.column() - startCell.column() + 1);
    }

    /**
     * Returns a pretty-printed string representation of this range expression, with the specified indentation.
     *
     * @param shift the number of dots to prepend for indentation.
     * @return a formatted string representing the range expression.
     */
    @Override
    public String prettyPrint(int shift) {
        return ".".repeat(shift) + startCell + ":" + endCell;
    }

    /**
     * Returns a list of all {@link CellReference} objects within the range.
     *
     * @return a list of cell references within the range.
     */
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

    /**
     * Returns a string representation of the range, in the format "startCell:endCell".
     *
     * @return the range as a string.
     */
    @Override
    public String toString() {
        return startCell + ":" + endCell;
    }
}
