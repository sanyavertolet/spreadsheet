package com.sanyavertolet.interview.math.expressions;

import com.sanyavertolet.interview.exceptions.ExpressionEvaluationException;

public abstract class Expression {
    protected Double value;

    public Double evaluate() throws ExpressionEvaluationException {
        if (value == null) {
            recalculate();
        }
        return value;
    }

    public abstract void recalculate() throws ExpressionEvaluationException;

    public abstract String prettyPrint(int shift);
}
