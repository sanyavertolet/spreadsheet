package com.sanyavertolet.interview.math.expressions;

import com.sanyavertolet.interview.exceptions.EvaluationException;

public abstract class Expression {
    protected Double value;

    public Double evaluate() throws EvaluationException {
        if (value == null) {
            recalculate();
        }
        return value;
    }

    public abstract void recalculate() throws EvaluationException;

    public abstract String prettyPrint(int shift);
}
