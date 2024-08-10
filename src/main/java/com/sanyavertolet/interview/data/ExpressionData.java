package com.sanyavertolet.interview.data;

import com.sanyavertolet.interview.exceptions.ExpressionEvaluationException;
import com.sanyavertolet.interview.math.CellReference;
import com.sanyavertolet.interview.math.expressions.BinaryExpression;
import com.sanyavertolet.interview.math.expressions.CellReferenceExpression;
import com.sanyavertolet.interview.math.expressions.Expression;
import com.sanyavertolet.interview.math.expressions.FunctionExpression;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class ExpressionData extends Data {
    private final Expression expression;

    public ExpressionData(String text, Expression expression) {
        super(text);
        this.expression = expression;
        try {
            expression.evaluate();
        } catch (ExpressionEvaluationException ignored) { }
    }

    public void recalculate() {
        try {
            expression.recalculate();
        } catch (ExpressionEvaluationException ignored) { }
    }

    public Double getValue() {
        try {
            return expression.evaluate();
        } catch (ExpressionEvaluationException exception) {
            return null;
        }
    }

    @Override
    public String getValueAsString() {
        Double value = getValue();
        return value == null ? "ERR" : value.toString();
    }

    public String getPrettyPrintedExpressionTree() {
        return expression.prettyPrint(0);
    }

    public Set<CellReference> getCellReferences() {
        Set<CellReference> cellReferences = new HashSet<>();
        Stack<Expression> stack = new Stack<>();
        stack.push(expression);

        while (!stack.isEmpty()) {
            Expression expression = stack.pop();
            if (expression instanceof CellReferenceExpression cellReferenceExpression) {
                cellReferences.add(cellReferenceExpression.getCellReference());
            } else if (expression instanceof BinaryExpression binaryExpression) {
                stack.push(binaryExpression.getLeft());
                stack.push(binaryExpression.getRight());
            } else if (expression instanceof FunctionExpression functionExpression) {
                for (Expression argument : functionExpression.getArguments()) {
                    stack.push(argument);
                }
            }
        }
        return cellReferences;
    }
}
