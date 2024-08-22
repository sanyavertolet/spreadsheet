package com.sanyavertolet.interview.data;

import com.sanyavertolet.interview.data.value.Value;
import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.math.expressions.Expression;
import com.sanyavertolet.interview.math.expressions.evaluator.ExpressionEvaluator;

/**
 * Represents the data associated with a cell in a table-like structure, including the cell's text,
 * its evaluated value, and an optional expression tree for cells containing formulas.
 * The {@code Data} class supports recalculating its value based on an expression and marking the cell
 * as erroneous if an evaluation fails.
 */
public class Data {
    private String text;
    private Value value;
    private final Expression expressionTree;

    /**
     * Constructs a {@code Data} object with the specified text, value, and expression tree.
     *
     * @param text           the text representation of the data.
     * @param value          the evaluated value of the data.
     * @param expressionTree the expression tree associated with the data, or {@code null} if not applicable.
     */
    public Data(String text, Value value, Expression expressionTree) {
        this.text = text;
        this.value = value;
        this.expressionTree = expressionTree;
    }

    /**
     * Constructs a {@code Data} object with the specified text and double value.
     * The expression tree is set to {@code null}.
     *
     * @param text  the text representation of the data.
     * @param value the double value of the data.
     */
    public Data(String text, Double value) {
        this.text = text;
        this.value = Value.of(value);
        this.expressionTree = null;
    }

    /**
     * Constructs a {@code Data} object with the specified text and integer value.
     * The expression tree is set to {@code null}.
     *
     * @param text  the text representation of the data.
     * @param value the integer value of the data.
     */
    public Data(String text, Integer value) {
        this.text = text;
        this.value = Value.of(value);
        this.expressionTree = null;
    }

    /**
     * Constructs a {@code Data} object with the specified text.
     * The value is derived from the text, and the expression tree is set to {@code null}.
     *
     * @param text the text representation of the data.
     */
    public Data(String text) {
        this.text = text;
        this.value = Value.of(text);
        this.expressionTree = null;
    }

    /**
     * Constructs a {@code Data} object with the specified text and boolean value.
     * The expression tree is set to {@code null}.
     *
     * @param text  the text representation of the data.
     * @param value the boolean value of the data.
     */
    public Data(String text, Boolean value) {
        this.text = text;
        this.value = Value.of(value);
        this.expressionTree = null;
    }

    /**
     * Returns the text representation of the data.
     *
     * @return the text of the data.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text representation of the data.
     *
     * @param text the new text of the data.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Returns the evaluated value of the data.
     *
     * @return the value of the data.
     */
    public Value getValue() {
        return value;
    }

    /**
     * Recalculates the value of the data based on the associated expression tree, if it exists.
     * If the evaluation fails, the value is set to {@code null}.
     *
     * @param expressionEvaluator the evaluator used to recalculate the value.
     */
    public void recalculateValue(ExpressionEvaluator expressionEvaluator) {
        if (expressionTree == null) {
            return;
        }
        try {
            value = expressionEvaluator.evaluate(expressionTree);
        } catch (ExpressionEvaluationException e) {
            value = null;
        }
    }

    /**
     * Returns the expression tree associated with the data, if any.
     *
     * @return the expression tree, or {@code null} if not applicable.
     */
    public Expression getExpressionTree() {
        return expressionTree;
    }

    /**
     * Marks the data as erroneous by setting the value to {@code null} if an expression is associated with it.
     */
    public void markAsError() {
        if (expressionTree != null) {
            value = null;
        }
    }

    /**
     * Returns a pretty-printed representation of the expression tree.
     * If the expression tree is {@code null}, returns "Expression is null".
     *
     * @return a pretty-printed string of the expression tree or a message indicating it's null.
     */
    public String prettyPrintExpression() {
        if (this.expressionTree != null) {
            return expressionTree.prettyPrint(0);
        } else {
            return "Expression is null";
        }
    }
}
