package com.sanyavertolet.interview.math.expressions.evaluator;

import com.sanyavertolet.interview.data.value.IterableValue;
import com.sanyavertolet.interview.data.value.Value;
import com.sanyavertolet.interview.data.accessor.DataAccessor;
import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.math.CellReference;
import com.sanyavertolet.interview.math.expressions.*;
import com.sanyavertolet.interview.math.operators.NonFunctionOperator;

import java.util.ArrayList;
import java.util.List;

public class SimpleExpressionEvaluator implements ExpressionEvaluator {
    private final DataAccessor dataAccessor;

    public SimpleExpressionEvaluator(DataAccessor dataAccessor) {
        this.dataAccessor = dataAccessor;
    }

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

    private Value evaluate(FunctionExpression expression) throws ExpressionEvaluationException {
        List<Value> argumentValues = new ArrayList<>();
        for (Expression argument : expression.getArguments()) {
            argumentValues.add(evaluate(argument));
        }
        return expression.getFunction().evaluate(argumentValues);
    }

    private Value evaluate(ValueExpression expression) {
        return expression.getValue();
    }

    private Value evaluate(CellReferenceExpression expression) {
        CellReference cellReference = expression.getCellReference();
        return dataAccessor.getData(cellReference).getValue();
    }

    private Value evaluate(RangeExpression expression) {
        return new IterableValue(expression.getCellReferences()
                .stream()
                .map(it -> dataAccessor.getData(it).getValue())
                .toList()
        );
    }

    @Override
    public Value evaluate(Expression expression) throws ExpressionEvaluationException {
        if (expression instanceof CellReferenceExpression cellReferenceExpression) {
            return evaluate(cellReferenceExpression);
        } else if (expression instanceof ValueExpression valueExpression) {
            return evaluate(valueExpression);
        } else if (expression instanceof BinaryExpression binaryExpression) {
            return evaluate(binaryExpression);
        }  else if (expression instanceof FunctionExpression functionExpression) {
            return evaluate(functionExpression);
        } else if (expression instanceof RangeExpression rangeExpression) {
            return evaluate(rangeExpression);
        }
        throw new ExpressionEvaluationException("Unknown expression: " + expression.toString());
    }
}
