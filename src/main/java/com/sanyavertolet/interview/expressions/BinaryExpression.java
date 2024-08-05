package com.sanyavertolet.interview.expressions;

import com.sanyavertolet.interview.exceptions.EvaluationException;
import com.sanyavertolet.interview.parser.Operator;

public class BinaryExpression extends Expression {
    private Expression left;
    private Expression right;
    private Operator operator;

    public BinaryExpression(Expression left, Expression right, Operator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Double evaluate() throws EvaluationException {
        Double rightValue = right.evaluate();
        return switch (operator) {
            case PLUS -> left.evaluate() + rightValue;
            case MINUS -> left.evaluate() - rightValue;
            case MULTIPLY -> left.evaluate() * rightValue;
            case DIVIDE -> {
                if (rightValue.equals(0.0)) {
                    throw new ArithmeticException("Division by zero");
                }
                yield left.evaluate() / rightValue;
            }
            case POWER -> Math.pow(left.evaluate(), rightValue);
            default -> null;
        };
    }
}
