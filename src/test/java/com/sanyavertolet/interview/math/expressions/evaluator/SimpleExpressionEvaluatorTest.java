package com.sanyavertolet.interview.math.expressions.evaluator;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.data.accessor.DataAccessor;
import com.sanyavertolet.interview.data.value.StringValue;
import com.sanyavertolet.interview.data.value.Value;
import com.sanyavertolet.interview.exceptions.*;
import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.exceptions.expressions.RangeParsingException;
import com.sanyavertolet.interview.math.CellReference;
import com.sanyavertolet.interview.math.expressions.Expression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.sanyavertolet.interview.Expressions.BinaryExpressions.*;
import static com.sanyavertolet.interview.Expressions.Cells.*;
import static com.sanyavertolet.interview.Expressions.Functions.*;
import static com.sanyavertolet.interview.Expressions.Ranges.range;
import static com.sanyavertolet.interview.Expressions.Values.*;
import static com.sanyavertolet.interview.Expressions.Values.fortyTwo;

public class SimpleExpressionEvaluatorTest {
    private final Double a1Val = 1.0;
    private final Double a2Val = 2.0;
    private final Integer b1Val = 3;
    private final Double b2Val = 4.0;

    private final DataAccessor dataAccessor = reference -> switch (reference.identifier()) {
        case "A1" -> new Data("1.0", a1Val);
        case "A2" -> new Data("2.0", a2Val);
        case "B1" -> new Data("3.0", b1Val);
        case "B2" -> new Data("4.0", b2Val);
        default -> new Data("");
    };

    private final ExpressionEvaluator expressionEvaluator = new SimpleExpressionEvaluator(dataAccessor);

    public SimpleExpressionEvaluatorTest() { }

    @Test
    void dummyExpressionTest() throws ExpressionEvaluationException {
        Expression expression = plus(one, two);

        Value expectedValue = Value.of(3);
        Value actualValue = expressionEvaluator.evaluate(expression);

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void parameterlessFunctionExpressionTest() throws FunctionArgumentException, ExpressionEvaluationException {
        Expression expression = plus(pi(), e());

        Value expectedValue = Value.of(Math.PI + Math.E);
        Value actualValue = expressionEvaluator.evaluate(expression);

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void parameterizedFunctionExpressionTest() throws FunctionArgumentException, ExpressionEvaluationException {
        Expression expression = powF(two, two);

        Value expectedValue = Value.of(Math.pow(2, 2));
        Value actualValue = expressionEvaluator.evaluate(expression);

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

        Value expectedValue = Value.of(Math.pow(2, 5) + (3.0 * Math.E) / (4 - 7));
        Value actualValue = expressionEvaluator.evaluate(expression);

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void nestedFunctionExpressionTest() throws FunctionArgumentException, ExpressionEvaluationException {
        Expression expression = powF(pi(), two);

        Value expectedValue = Value.of(Math.pow(Math.PI, 2));
        Value actualValue = expressionEvaluator.evaluate(expression);

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void negativeNumberExpressionTest() throws ExpressionEvaluationException {
        Expression expression = plus(minus(five), ten);

        Value expectedValue = Value.of(-5 + 10);
        Value actualValue = expressionEvaluator.evaluate(expression);

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

        Value expectedValue = Value.of(2 + 3 * 4 - 5 / 2);
        Value actualValue = expressionEvaluator.evaluate(expression);

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

        Value expectedValue = Value.of(((2 + 3) * (4 - 1)) / 5);
        Value actualValue = expressionEvaluator.evaluate(expression);

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

        Value expectedValue = Value.of((Math.pow(2, 3) + 4) * Math.PI);
        Value actualValue = expressionEvaluator.evaluate(expression);

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void cellReferenceExpressionTest() throws CellReferenceException, ExpressionEvaluationException {
        Expression expression = mul(a1(), two);

        Value expectedValue = Value.of(a1Val * 2);
        Value actualValue = expressionEvaluator.evaluate(expression);

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

        Value expectedValue = Value.of(Math.pow(-2, a1Val - 3) * (42 + b2Val));
        Value actualValue = expressionEvaluator.evaluate(expression);

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void doubleCastingExpressionTest() throws ExpressionEvaluationException {
        Expression expression = div(
                mul(
                        plus(two, three),
                        minus(four, one)
                ),
                value("5.0")
        );

        Value expectedValue = Value.of(((2 + 3) * (4 - 1)) / 5.0);
        Value actualValue = expressionEvaluator.evaluate(expression);

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void containsExpressionTest() throws ExpressionEvaluationException, FunctionArgumentException {
        String string = "FooBar";
        String needle = "oB";
        Expression expression = contains(value(string), value(needle));

        @SuppressWarnings("ConstantValue") Value expectedValue = Value.of(string.contains(needle));
        Value actualValue = expressionEvaluator.evaluate(expression);

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void stringCastExpressionTest() throws ExpressionEvaluationException, FunctionArgumentException {
        Assertions.assertEquals(new StringValue("5"), expressionEvaluator.evaluate(string(value("5"))));
        Assertions.assertEquals(new StringValue("true"), expressionEvaluator.evaluate(string(value("true"))));
        Assertions.assertEquals(new StringValue("5.2"), expressionEvaluator.evaluate(string(value("5.2"))));
    }

    @SuppressWarnings("ConstantValue")
    @Test
    void booleanArithmeticExpressionTest() throws ExpressionEvaluationException, FunctionArgumentException {
        Assertions.assertEquals(Value.of(4 + 1 == 5), expressionEvaluator.evaluate(eq(plus(four, one), five)));
        Assertions.assertEquals(Value.of(4 != 5), expressionEvaluator.evaluate(neq(four, five)));
        Assertions.assertEquals(Value.of(Math.PI < 5), expressionEvaluator.evaluate(lt(pi(), five)));
        Assertions.assertEquals(Value.of(2 * Math.PI > 5), expressionEvaluator.evaluate(gt(mul(two, pi()), five)));

        Assertions.assertEquals(Value.of(false), expressionEvaluator.evaluate(eq(eq(one, one), one)));
        Assertions.assertEquals(Value.of(true), expressionEvaluator.evaluate(eq(eq(one, one), value("true"))));

        Assertions.assertEquals(Value.of(true), expressionEvaluator.evaluate(gt(three, gt(one, two))));
        Assertions.assertEquals(Value.of(false), expressionEvaluator.evaluate(gt(minus(one), gt(one, two))));
    }

    @Test
    void stringFunctionsExpressionTest() throws ExpressionEvaluationException, FunctionArgumentException {
        Assertions.assertEquals(Value.of("concat"), expressionEvaluator.evaluate(concat(rawValue("con"), rawValue("cat"))));

        Assertions.assertEquals(Value.of("true"), expressionEvaluator.evaluate(iff(trueExpr, rawValue("true"), rawValue("false"))));
        Assertions.assertEquals(Value.of("false"), expressionEvaluator.evaluate(iff(falseExpr, rawValue("true"), rawValue("false"))));

        Assertions.assertEquals(
                Value.of("error"),
                expressionEvaluator.evaluate(ifErr(plus(one, rawValue("cannot use plus with strings")), rawValue("error")))
        );
    }

    @Test
    void rangeSumExpressionTest() throws ExpressionEvaluationException, FunctionArgumentException, CellReferenceException, RangeParsingException {
        Expression expression = mul(
                sum(range("A1", "B2")),
                three
        );

        Value expectedValue = Value.of((a1Val + a2Val + b1Val + b2Val) * 3);
        Value actualValue = expressionEvaluator.evaluate(expression);

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void missingCellExpressionTest() throws CellReferenceException {
        Expression expression = mul(two, c2());

        Assertions.assertThrows(ExpressionEvaluationException.class, () -> expressionEvaluator.evaluate(expression));
    }

    @Test
    void stringAdditionExpressionTest() {
        Expression expression = plus(value("a123"), value("b456"));

        Assertions.assertThrows(ExpressionEvaluationException.class, () -> expressionEvaluator.evaluate(expression));
    }

    @Test
    void unknownExpressionTest() {
        Expression expression = new Expression() {
            @Override
            public String prettyPrint(int shift) {
                return "";
            }

            @Override
            public List<CellReference> getCellReferences() {
                return new ArrayList<>();
            }
        };

        Assertions.assertThrows(ExpressionEvaluationException.class, () -> expressionEvaluator.evaluate(expression));
    }
}
