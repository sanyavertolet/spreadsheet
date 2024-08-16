package com.sanyavertolet.interview.data;

import com.sanyavertolet.interview.data.value.Value;
import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.math.expressions.Expression;
import com.sanyavertolet.interview.math.expressions.evaluator.ExpressionEvaluator;

public class Data {
    private String text;
    private Value value;
    private final Expression expressionTree;

    public Data(String text, Value value, Expression expressionTree) {
        this.text = text;
        this.value = value;
        this.expressionTree = expressionTree;
    }

    public Data(String text, Double value) {
        this.text = text;
        this.value = Value.of(value);
        this.expressionTree = null;
    }

    public Data(String text, Integer value) {
        this.text = text;
        this.value = Value.of(value);
        this.expressionTree = null;
    }

    public Data(String text) {
        this.text = text;
        this.value = Value.of(text);
        this.expressionTree = null;
    }

    public Data(String text, Boolean value) {
        this.text = text;
        this.value = Value.of(value);
        this.expressionTree = null;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Value getValue() {
        return value != null ? value.copy() : null;
    }

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

    public Expression getExpressionTree() {
        return expressionTree;
    }

    public void markAsError() {
        if (expressionTree != null) {
            value = null;
        }
    }

    public String prettyPrintExpression() {
        if (this.expressionTree != null) {
            return expressionTree.prettyPrint(0);
        } else {
            return "Expression is null";
        }
    }
}
