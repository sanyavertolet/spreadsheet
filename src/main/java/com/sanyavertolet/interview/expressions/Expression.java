package com.sanyavertolet.interview.expressions;

import com.sanyavertolet.interview.exceptions.EvaluationException;

public abstract class Expression {
    public abstract Double evaluate() throws EvaluationException;
}
