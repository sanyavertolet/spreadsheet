package com.sanyavertolet.interview.math.expressions.evaluator;

import com.sanyavertolet.interview.data.value.Value;
import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.math.expressions.Expression;

public interface ExpressionEvaluator {
    Value evaluate(Expression expression) throws ExpressionEvaluationException;
}
