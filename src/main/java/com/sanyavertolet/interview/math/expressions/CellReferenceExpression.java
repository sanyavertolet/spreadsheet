package com.sanyavertolet.interview.math.expressions;

import com.sanyavertolet.interview.math.CellReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an expression that refers to a specific cell in a spreadsheet or table.
 * A {@code CellReferenceExpression} encapsulates a single {@link CellReference} object.
 */
public class CellReferenceExpression extends Expression {

    private final CellReference cellReference;

    /**
     * Constructs a {@code CellReferenceExpression} with the specified cell reference.
     *
     * @param cellReference the cell reference associated with this expression.
     */
    public CellReferenceExpression(CellReference cellReference) {
        this.cellReference = cellReference;
    }

    /**
     * Returns a pretty-printed string representation of the cell reference expression.
     * The output is indented according to the specified shift value.
     *
     * @param shift the number of dots to prepend for indentation.
     * @return a formatted string representing the cell reference expression.
     */
    @Override
    public String prettyPrint(int shift) {
        return ".".repeat(shift) + this + "\n";
    }

    /**
     * Returns a list containing the cell reference used in this expression.
     * This method returns a single-element list with the cell reference.
     *
     * @return a list containing the cell reference for this expression.
     */
    @Override
    public List<CellReference> getCellReferences() {
        return new ArrayList<>(List.of(cellReference));
    }

    /**
     * Returns the cell reference associated with this expression.
     *
     * @return the cell reference.
     */
    public CellReference getCellReference() {
        return cellReference;
    }

    /**
     * Returns a string representation of the cell reference expression.
     * The string representation is the identifier of the cell reference.
     *
     * @return the cell reference identifier.
     */
    @Override
    public String toString() {
        return cellReference.identifier();
    }
}
