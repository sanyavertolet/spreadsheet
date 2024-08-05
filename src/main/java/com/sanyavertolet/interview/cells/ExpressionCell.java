package com.sanyavertolet.interview.cells;

import com.sanyavertolet.interview.exceptions.EvaluationException;
import com.sanyavertolet.interview.math.expressions.Expression;

public class ExpressionCell extends Cell {
    private final Expression expression;

    public ExpressionCell(String text, Expression expression) {
        super(text);
        this.expression = expression;
        try {
            expression.evaluate();
        } catch (EvaluationException ignored) { }
    }

    public void recalculate() {
        try {
            expression.recalculate();
        } catch (EvaluationException ignored) { }
    }

    public Double getValue() {
        try {
            return expression.evaluate();
        } catch (EvaluationException exception) {
            return null;
        }
    }

    @Override
    public String getValueAsString() {
        Double value = getValue();
        return value == null ? "ERR" : value.toString();
    }

    public String getPrettyPrintedExpressionTree() {
        return "TBD";
    }
}
