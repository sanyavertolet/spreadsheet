package com.sanyavertolet.interview.cells;

import com.sanyavertolet.interview.exceptions.EvaluationException;
import com.sanyavertolet.interview.expressions.Expression;
import com.sanyavertolet.interview.parser.ExpressionParser;
import com.sanyavertolet.interview.parser.TreeNode;

public class ExpressionCell extends Cell {
    private Double value;
    private final Expression expression;

    public ExpressionCell(String text, Expression expression) {
        super(text);
        this.expression = expression;
        try {
            value = expression.evaluate();
        } catch (EvaluationException ignored) { }
    }

    public Double recalculate() {
        try {
            value = expression.evaluate();
        } catch (EvaluationException exception) {
            value = null;
        }
        return value;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public String getValueAsString() {
        return value == null ? "ERR" : value.toString();
    }

    public String getPrettyPrintedExpressionTree() {
        return "TBD";
    }
}
