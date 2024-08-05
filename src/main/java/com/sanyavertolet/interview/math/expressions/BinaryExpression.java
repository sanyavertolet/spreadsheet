package com.sanyavertolet.interview.math.expressions;

import com.sanyavertolet.interview.math.Operator;
import com.sanyavertolet.interview.exceptions.EvaluationException;

public class BinaryExpression extends Expression {
    private final Expression left;
    private final Expression right;
    private final Operator operator;

    public BinaryExpression(Expression left, Expression right, Operator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public void recalculate() throws EvaluationException {
        Double rightValue = right.evaluate();
        try {
            value = operator.evaluate(left.evaluate(), rightValue);
        } catch (ArithmeticException exception) {
            throw new EvaluationException(exception.getMessage());
        }
    }
}
