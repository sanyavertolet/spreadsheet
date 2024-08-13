package com.sanyavertolet.interview.math.expressions.evaluator;

import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.math.expressions.Expression;

public interface ExpressionEvaluator {
    Double evaluate(Expression expression) throws ExpressionEvaluationException;
}
