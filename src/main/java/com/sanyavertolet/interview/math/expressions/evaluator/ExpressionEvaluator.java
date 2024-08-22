package com.sanyavertolet.interview.math.expressions.evaluator;

import com.sanyavertolet.interview.data.value.Value;
import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.math.expressions.Expression;

/**
 * An interface for evaluating expressions.
 * Implementations of this interface provide the logic to compute the result of an {@code Expression}.
 */
public interface ExpressionEvaluator {

    /**
     * Evaluates the given expression and returns the resulting {@code Value}.
     *
     * @param expression the expression to be evaluated.
     * @return the computed result of the expression as a {@code Value}.
     * @throws ExpressionEvaluationException if there is an error during evaluation.
     */
    Value evaluate(Expression expression) throws ExpressionEvaluationException;
}
