package com.sanyavertolet.interview.math.expressions.evaluator;

import com.sanyavertolet.interview.data.manager.DataAccessor;
import com.sanyavertolet.interview.exceptions.DataAccessException;
import com.sanyavertolet.interview.exceptions.ExpressionEvaluationException;
import com.sanyavertolet.interview.math.CellReference;
import com.sanyavertolet.interview.math.Function;
import com.sanyavertolet.interview.math.expressions.*;
import com.sanyavertolet.interview.math.operators.NonFunctionOperator;

import java.util.ArrayList;
import java.util.List;

public class SimpleExpressionEvaluator implements ExpressionEvaluator {
    private final DataAccessor dataAccessor;

    public SimpleExpressionEvaluator(DataAccessor dataAccessor) {
        this.dataAccessor = dataAccessor;
    }

    @Override
    public Double evaluate(Expression expression) throws ExpressionEvaluationException {
        if (expression instanceof CellReferenceExpression cellReferenceExpression) {
            CellReference cellReference = cellReferenceExpression.getCellReference();
            try {
                return dataAccessor.getDoubleCellValue(cellReference);
            } catch (DataAccessException exception) {
                throw new ExpressionEvaluationException("Could not access cell " + cellReference.identifier(), exception);
            }
        } else if (expression instanceof NumberExpression numberExpression) {
            return numberExpression.getNumber();
        } else if (expression instanceof BinaryExpression binaryExpression) {
            Double leftExpressionValue = evaluate(binaryExpression.getLeft());
            Double rightExpressionValue = evaluate(binaryExpression.getRight());
            NonFunctionOperator operator = binaryExpression.getOperator();
            return operator.calculate(leftExpressionValue, rightExpressionValue);
        }  else if (expression instanceof FunctionExpression functionExpression) {
            List<Double> argumentValues = new ArrayList<>();
            for (Expression argumentExpression : functionExpression.getArguments()) {
                argumentValues.add(evaluate(argumentExpression));
            }
            Function function = functionExpression.getFunction();
            return function.evaluate(argumentValues);
        }
        throw new ExpressionEvaluationException("Unknown expression: " + expression.toString());
    }
}
