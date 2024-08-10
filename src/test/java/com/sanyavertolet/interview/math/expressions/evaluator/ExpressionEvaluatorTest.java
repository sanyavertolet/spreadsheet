package com.sanyavertolet.interview.math.expressions.evaluator;

import com.sanyavertolet.interview.data.manager.DataAccessor;
import com.sanyavertolet.interview.exceptions.*;
import com.sanyavertolet.interview.math.CellReference;
import com.sanyavertolet.interview.math.expressions.Expression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.sanyavertolet.interview.Expressions.BinaryExpressions.*;
import static com.sanyavertolet.interview.Expressions.Cells.a1;
import static com.sanyavertolet.interview.Expressions.Cells.b1;
import static com.sanyavertolet.interview.Expressions.Cells.b2;
import static com.sanyavertolet.interview.Expressions.Functions.*;
import static com.sanyavertolet.interview.Expressions.Numbers.*;
import static com.sanyavertolet.interview.Expressions.Numbers.fortyTwo;

public class ExpressionEvaluatorTest {
    private final CellReference a1Ref = CellReference.of("A1");
    private final CellReference b2Ref = CellReference.of("B2");

    private final Double a1Val = 2.0;
    private final Double b2Val = 7.0;

    private final DataAccessor dataAccessor = new DataAccessor() {
        @Override
        public Double getDoubleCellValue(CellReference cellReference) throws DataAccessException {
            return switch (cellReference.identifier()) {
                case "A1" -> a1Val;
                case "B2" -> b2Val;
                default -> throw new DataAccessException("Cell not found");
            };
        }

        @Override
        public boolean hasCell(CellReference cellReference) {
            return cellReference.equals(a1Ref) || cellReference.equals(b2Ref);
        }
    };

    private final ExpressionEvaluator expressionEvaluator = new SimpleExpressionEvaluator(dataAccessor);

    public ExpressionEvaluatorTest() throws CellReferenceException { }

    @Test
    void dummyExpressionTest() throws ExpressionEvaluationException {
        Expression expression = plus(one, two);

        Double expectedValue = 3.0;
        Double actualValue = expressionEvaluator.evaluate(expression);

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void parameterlessFunctionExpressionTest() throws FunctionArgumentException, ExpressionEvaluationException {
        Expression expression = plus(pi(), e());

        Double expectedValue = Math.PI + Math.E;
        Double actualValue = expressionEvaluator.evaluate(expression);

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void parameterizedFunctionExpressionTest() throws FunctionArgumentException, ExpressionEvaluationException {
        Expression expression = powF(two, two);

        Double expectedValue = Math.pow(2.0, 2.0);
        Double actualValue = expressionEvaluator.evaluate(expression);

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void complexExpressionFunctionExpressionTest() throws FunctionArgumentException, ExpressionEvaluationException {
        Expression expression = plus(
                powOp(two, five),
                div(
                        mul(three, e()),
                        minus(four, seven)
                )
        );

        Double expectedValue = Math.pow(2.0, 5.0) + (3.0 * Math.E) / (4.0 - 7.0);
        Double actualValue = expressionEvaluator.evaluate(expression);

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void nestedFunctionExpressionTest() throws FunctionArgumentException, ExpressionEvaluationException {
        Expression expression = powF(pi(), two);

        Double expectedValue = Math.pow(Math.PI, 2.0);
        Double actualValue = expressionEvaluator.evaluate(expression);

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void negativeNumberExpressionTest() throws ExpressionEvaluationException {
        Expression expression = plus(minus(five), ten);

        Double expectedValue = -5.0 + 10.0;
        Double actualValue = expressionEvaluator.evaluate(expression);

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void multipleOperationsExpressionTest() throws ExpressionEvaluationException {
        Expression expression = minus(
                plus(
                        two,
                        mul(three, four)
                ),
                div(five, two)
        );

        Double expectedValue = 2.0 + 3.0 * 4.0 - 5.0 / 2.0;
        Double actualValue = expressionEvaluator.evaluate(expression);

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void nestedParenthesesExpressionTest() throws ExpressionEvaluationException {
        Expression expression = div(
                mul(
                        plus(two, three),
                        minus(four, one)
                ),
                five
        );

        Double expectedValue = ((2.0 + 3.0) * (4.0 - 1.0)) / 5.0;
        Double actualValue = expressionEvaluator.evaluate(expression);

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void mixedFunctionAndOperationExpressionTest() throws FunctionArgumentException, ExpressionEvaluationException {
        Expression expression = mul(
                plus(
                        powF(two, three),
                        four
                ),
                pi()
        );

        Double expectedValue = (Math.pow(2, 3) + 4) * Math.PI;
        Double actualValue = expressionEvaluator.evaluate(expression);

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void cellReferenceExpressionTest() throws CellReferenceException, ExpressionEvaluationException {
        Expression expression = mul(a1(), two);

        Double expectedValue = a1Val * 2;
        Double actualValue = expressionEvaluator.evaluate(expression);

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void exampleExpressionTest() throws FunctionArgumentException, CellReferenceException, ExpressionEvaluationException {
        Expression expression = mul(
                powF(
                        minus(two),
                        minus(a1(), three)
                ),
                plus(fortyTwo, b2())
        );

        Double expectedValue = Math.pow(-2, a1Val - 3) * (42 + b2Val);
        Double actualValue = expressionEvaluator.evaluate(expression);

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void missingCellExpressionTest() throws CellReferenceException {
        Expression expression = b1();

        Assertions.assertThrows(ExpressionEvaluationException.class, () -> expressionEvaluator.evaluate(expression));
    }

    @Test
    void unknownExpressionTest() {
        Expression expression = new Expression() {
            @Override
            public String prettyPrint(int shift) {
                return "";
            }
        };

        Assertions.assertThrows(ExpressionEvaluationException.class, () -> expressionEvaluator.evaluate(expression));
    }
}
