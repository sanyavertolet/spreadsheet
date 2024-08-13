package com.sanyavertolet.interview.math.expressions;

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
    public String prettyPrint(int shift) {
        return ".".repeat(shift) + operator.getSymbol() + "\n" + left.prettyPrint(shift + 2) + right.prettyPrint(shift + 2);
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public NonFunctionOperator getOperator() {
        return operator;
    }
}
