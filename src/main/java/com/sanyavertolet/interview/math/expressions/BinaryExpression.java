package com.sanyavertolet.interview.math.expressions;

import com.sanyavertolet.interview.exceptions.ExpressionEvaluationException;
import com.sanyavertolet.interview.math.operators.NonFunctionOperator;

public class BinaryExpression extends Expression {
    private final Expression left;
    private final Expression right;
    private final NonFunctionOperator operator;

    public BinaryExpression(Expression left, Expression right, NonFunctionOperator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public void recalculate() throws ExpressionEvaluationException {
        Double rightValue = right.evaluate();
        try {
            value = operator.evaluate(left.evaluate(), rightValue);
        } catch (ArithmeticException exception) {
            throw new ExpressionEvaluationException(exception.getMessage());
        }
    }

    @Override
    public String prettyPrint(int shift) {
        return ".".repeat(shift) + operator.symbol() + "\n" + left.prettyPrint(shift + 2) + right.prettyPrint(shift + 2);
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }
}
