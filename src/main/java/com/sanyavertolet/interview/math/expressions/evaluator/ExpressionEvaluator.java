package com.sanyavertolet.interview.math.expressions.evaluator;

import com.sanyavertolet.interview.data.value.Value;
import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.math.expressions.Expression;

/**
 * An interface for evaluating expressions.
 * Implementations of this interface provide the logic to compute the result of an {@link Expression}.
 */
public interface ExpressionEvaluator {
    /**
     * Evaluates the given expression and returns the resulting {@link Value}.
     *
     * @param expression the expression to be evaluated.
     * @return the computed result of the expression as a {@link Value}.
     * @throws ExpressionEvaluationException if there is an error during evaluation.
     */
    Value evaluate(Expression expression) throws ExpressionEvaluationException;

    /**
     * Evaluates the given expression and returns the resulting {@link Value}.
     *
     * @param expression the expression to be evaluated.
     * @return the computed result of the expression as a {@link Value} or null if {@link ExpressionEvaluationException} was thrown.
     */
    Value evaluateOrNull(Expression expression);
}
