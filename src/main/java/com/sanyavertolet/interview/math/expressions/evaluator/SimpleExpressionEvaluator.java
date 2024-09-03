package com.sanyavertolet.interview.math.expressions.evaluator;

import com.sanyavertolet.interview.data.value.IterableValue;
import com.sanyavertolet.interview.data.value.Value;
import com.sanyavertolet.interview.data.accessor.DataAccessor;
import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.math.CellReference;
import com.sanyavertolet.interview.math.expressions.*;
import com.sanyavertolet.interview.math.operators.NonFunctionOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple implementation of the {@code ExpressionEvaluator} interface.
 * This class evaluates expressions using various strategies based on the type of expression.
 */
public class SimpleExpressionEvaluator implements ExpressionEvaluator {
    private final Logger logger = LoggerFactory.getLogger(SimpleExpressionEvaluator.class);
    private final DataAccessor dataAccessor;

    /**
     * Constructs a {@code SimpleExpressionEvaluator} with the given {@code DataAccessor}.
     *
     * @param dataAccessor the data accessor used to retrieve data for cell references.
     */
    public SimpleExpressionEvaluator(DataAccessor dataAccessor) {
        this.dataAccessor = dataAccessor;
    }

    /**
     * Evaluates a {@code BinaryExpression}.
     *
     * @param expression the binary expression to evaluate.
     * @return the result of the binary expression evaluation as a {@code Value}.
     * @throws ExpressionEvaluationException if an error occurs during evaluation.
     */
    private Value evaluate(BinaryExpression expression) throws ExpressionEvaluationException {
        Value left = evaluate(expression.getLeft());
        Value right = evaluate(expression.getRight());
        NonFunctionOperator operator = expression.getOperator();
        return switch (operator.type()) {
            case PLUS -> left.plus(right);
            case MINUS -> left.minus(right);
            case MULTIPLY -> left.multiply(right);
            case DIVIDE -> left.divide(right);
            case POWER -> left.pow(right);
            case EQ -> left.eq(right);
            case NEQ -> left.neq(right);
            case LT -> left.lt(right);
            case GT -> left.gt(right);
            case LEQ -> left.leq(right);
            case GEQ -> left.geq(right);
            default -> throw new ExpressionEvaluationException("Unknown operator type: " + operator.type());
        };
    }

    /**
     * Evaluates a {@code FunctionExpression}.
     *
     * @param expression the function expression to evaluate.
     * @return the result of the function evaluation as a {@code Value}.
     * @throws ExpressionEvaluationException if an error occurs during evaluation.
     */
    private Value evaluate(FunctionExpression expression) throws ExpressionEvaluationException {
        List<Value> argumentValues = new ArrayList<>();
        for (Expression argument : expression.getArguments()) {
            try {
                argumentValues.add(evaluate(argument));
            } catch (ExpressionEvaluationException e) {
                argumentValues.add(null);
            }
        }
        return expression.getFunction().evaluate(argumentValues);
    }

    /**
     * Evaluates a {@code ValueExpression}.
     *
     * @param expression the value expression to evaluate.
     * @return the result of the value expression evaluation as a {@code Value}.
     */
    private Value evaluate(ValueExpression expression) {
        return expression.getValue();
    }

    /**
     * Evaluates a {@code CellReferenceExpression}.
     *
     * @param expression the cell reference expression to evaluate.
     * @return the result of the cell reference expression evaluation as a {@code Value}.
     */
    private Value evaluate(CellReferenceExpression expression) {
        CellReference cellReference = expression.getCellReference();
        return dataAccessor.getData(cellReference).getValue();
    }

    /**
     * Evaluates a {@code RangeExpression}.
     *
     * @param expression the range expression to evaluate.
     * @return the result of the range expression evaluation as an {@code IterableValue}.
     */
    private Value evaluate(RangeExpression expression) {
        return new IterableValue(expression.getCellReferences()
                .stream()
                .map(it -> dataAccessor.getData(it).getValue())
                .toList()
        );
    }

    /**
     * Evaluates the given expression.
     *
     * @param expression the expression to evaluate.
     * @return the result of the expression evaluation as a {@code Value}.
     * @throws ExpressionEvaluationException if an error occurs during evaluation.
     */
    @Override
    public Value evaluate(Expression expression) throws ExpressionEvaluationException {
        logger.trace("Evaluating expression: \n{}", expression.prettyPrint(0));
        try {
            if (expression instanceof CellReferenceExpression cellReferenceExpression) {
                return evaluate(cellReferenceExpression);
            } else if (expression instanceof ValueExpression valueExpression) {
                return evaluate(valueExpression);
            } else if (expression instanceof BinaryExpression binaryExpression) {
                return evaluate(binaryExpression);
            } else if (expression instanceof FunctionExpression functionExpression) {
                return evaluate(functionExpression);
            } else if (expression instanceof RangeExpression rangeExpression) {
                return evaluate(rangeExpression);
            }
        } catch (Exception e) {
            throw new ExpressionEvaluationException("Could not evaluate expression", e);
        }
        throw new ExpressionEvaluationException("Unknown expression: " + expression);
    }

    /**
     * Evaluates the given expression and returns the resulting {@link Value}.
     *
     * @param expression the expression to be evaluated.
     * @return the computed result of the expression as a {@link Value} or null if {@link ExpressionEvaluationException} was thrown.
     */
    @Override
    public Value evaluateOrNull(Expression expression) {
        try {
            return evaluate(expression);
        } catch (ExpressionEvaluationException e) {
            return null;
        }
    }
}
