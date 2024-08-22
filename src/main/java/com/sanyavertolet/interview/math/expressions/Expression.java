package com.sanyavertolet.interview.math.expressions;

import com.sanyavertolet.interview.math.CellReference;

import java.util.List;

/**
 * Abstract base class for all expression types within the expression parsing and evaluation framework.
 * An {@code Expression} represents a component of an expression, which can include values, operators, functions,
 * and cell references.
 */
public abstract class Expression {

    /**
     * Returns a pretty-printed string representation of the expression.
     * The output should be formatted with indentation to reflect the structure of the expression.
     *
     * @param shift the number of dots to prepend for indentation.
     * @return a formatted string representing the expression.
     */
    public abstract String prettyPrint(int shift);

    /**
     * Returns a list of {@link CellReference} objects contained within this expression.
     * This method is used to collect all cell references that are part of the expression.
     *
     * @return a list of cell references used in this expression.
     */
    public abstract List<CellReference> getCellReferences();
}
