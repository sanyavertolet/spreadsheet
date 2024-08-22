package com.sanyavertolet.interview.math.expressions;

import com.sanyavertolet.interview.math.CellReference;
import com.sanyavertolet.interview.math.operators.NonFunctionOperator;

import java.util.List;

/**
 * Represents a binary expression in a formula or computation.
 * A {@code BinaryExpression} consists of two sub-expressions (left and right)
 * and an operator that defines the operation to be performed between them.
 */
public class BinaryExpression extends Expression {

    private final Expression left;
    private final Expression right;
    private final NonFunctionOperator operator;

    /**
     * Constructs a {@code BinaryExpression} with the specified left and right expressions
     * and an operator.
     *
     * @param left the left-hand side expression.
     * @param right the right-hand side expression.
     * @param operator the operator used in the binary expression.
     */
    public BinaryExpression(Expression left, Expression right, NonFunctionOperator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    /**
     * Returns a pretty-printed string representation of the binary expression.
     * The output is indented according to the specified shift value.
     * The operator appears at the current level, with left and right operands indented further.
     *
     * @param shift the number of dots to prepend for indentation.
     * @return a formatted string representing the binary expression.
     */
    @Override
    public String prettyPrint(int shift) {
        return ".".repeat(shift) + operator.getSymbol() + "\n" +
                left.prettyPrint(shift + 2) +
                right.prettyPrint(shift + 2);
    }

    /**
     * Returns a list of cell references that are found within the left and right sub-expressions.
     * This method aggregates cell references from both operands.
     *
     * @return a list containing all cell references from the left and right expressions.
     */
    @Override
    public List<CellReference> getCellReferences() {
        List<CellReference> result = left.getCellReferences();
        result.addAll(right.getCellReferences());
        return result;
    }

    /**
     * Returns the left-hand side expression of this binary expression.
     *
     * @return the left expression.
     */
    public Expression getLeft() {
        return left;
    }

    /**
     * Returns the right-hand side expression of this binary expression.
     *
     * @return the right expression.
     */
    public Expression getRight() {
        return right;
    }

    /**
     * Returns the operator used in this binary expression.
     *
     * @return the operator.
     */
    public NonFunctionOperator getOperator() {
        return operator;
    }

    /**
     * Returns a string representation of the binary expression.
     * The format is: "leftExpression operator rightExpression".
     *
     * @return the string representation of the binary expression.
     */
    @Override
    public String toString() {
        return left.toString() + " " + operator.getSymbol() + " " + right.toString();
    }
}
