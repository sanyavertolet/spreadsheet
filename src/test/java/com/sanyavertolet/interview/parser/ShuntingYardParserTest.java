package com.sanyavertolet.interview.parser;

import com.sanyavertolet.interview.AssertionUtils;
import com.sanyavertolet.interview.exceptions.*;
import com.sanyavertolet.interview.exceptions.expressions.ExpressionParsingException;
import com.sanyavertolet.interview.math.expressions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.sanyavertolet.interview.Expressions.BinaryExpressions.*;
import static com.sanyavertolet.interview.Expressions.Cells.a1;
import static com.sanyavertolet.interview.Expressions.Cells.b2;
import static com.sanyavertolet.interview.Expressions.Functions.*;
import static com.sanyavertolet.interview.Expressions.Ranges.range;
import static com.sanyavertolet.interview.Expressions.Values.*;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class ShuntingYardParserTest {
    private final ShuntingYardParser parser = new ShuntingYardParser();

    @Test
    void dummyExpressionTest() throws ExpressionParsingException {
        String expressionText = "=1 + 2";

        Expression expectedExpression = plus(one, two);
        Expression actualExpression = parser.parse(expressionText);

        AssertionUtils.assertExpressionsEqual(expectedExpression, actualExpression);
    }

    @Test
    void parameterlessFunctionExpressionTest() throws ExpressionParsingException, FunctionArgumentException {
        String expressionText = "=PI() + E()";

        Expression expectedExpression = plus(pi(), e());
        Expression actualExpression = parser.parse(expressionText);

        AssertionUtils.assertExpressionsEqual(expectedExpression, actualExpression);
    }

    @Test
    void parameterizedFunctionExpressionTest() throws ExpressionParsingException, FunctionArgumentException {
        String expressionText = "=POW(2.0, 2.0)";

        Expression expectedExpression = powF(two, two);
        Expression actualExpression = parser.parse(expressionText);

        AssertionUtils.assertExpressionsEqual(expectedExpression, actualExpression);
    }

    @Test
    void complexExpressionFunctionExpressionTest() throws ExpressionParsingException, FunctionArgumentException {
        String expressionText = "=2 ^ 5 + (3 * E()) / (4 - 7)";

        Expression expectedExpression = plus(
                powOp(two, five),
                div(
                        mul(three, e()),
                        minus(four, seven)
                )
        );
        Expression actualExpression = parser.parse(expressionText);

        AssertionUtils.assertExpressionsEqual(expectedExpression, actualExpression);
    }

    @Test
    void nestedFunctionExpressionTest() throws ExpressionParsingException, FunctionArgumentException {
        String expressionText = "=POW(PI(), 2)";

        Expression expectedExpression = powF(pi(), two);
        Expression actualExpression = parser.parse(expressionText);

        AssertionUtils.assertExpressionsEqual(expectedExpression, actualExpression);
    }

    @Test
    void negativeNumberExpressionTest() throws ExpressionParsingException {
        String expressionText = "=-5 + 10";

        Expression expectedExpression = plus(minus(five), ten);
        Expression actualExpression = parser.parse(expressionText);

        AssertionUtils.assertExpressionsEqual(expectedExpression, actualExpression);
    }

    @Test
    void multipleOperationsExpressionTest() throws ExpressionParsingException {
        String expressionText = "=2 + 3 * 4 - 5 / 2";

        Expression expectedExpression = minus(
                plus(
                        two,
                        mul(three, four)
                ),
                div(five, two)
        );
        Expression actualExpression = parser.parse(expressionText);

        AssertionUtils.assertExpressionsEqual(expectedExpression, actualExpression);
    }

    @Test
    void nestedParenthesesExpressionTest() throws ExpressionParsingException {
        String expressionText = "=((2 + 3) * (4 - 1)) / 5";

        Expression expectedExpression = div(
                mul(
                        plus(two, three),
                        minus(four, one)
                ),
                five
        );
        Expression actualExpression = parser.parse(expressionText);

        AssertionUtils.assertExpressionsEqual(expectedExpression, actualExpression);
    }

    @Test
    void mixedFunctionAndOperationExpressionTest() throws ExpressionParsingException, FunctionArgumentException {
        String expressionText = "=(POW(2, 3) + 4) * PI()";

        Expression expectedExpression = mul(
                plus(
                        powF(two, three),
                        four
                ),
                pi()
        );
        Expression actualExpression = parser.parse(expressionText);

        AssertionUtils.assertExpressionsEqual(expectedExpression, actualExpression);
    }

    @Test
    void cellReferenceExpressionTest() throws ExpressionParsingException, CellReferenceException {
        String expressionText = "=A1 * 2";

        Expression expectedExpression = mul(a1(), two);
        Expression actualExpression = parser.parse(expressionText);

        AssertionUtils.assertExpressionsEqual(expectedExpression, actualExpression);
    }

    @Test
    void exampleExpressionTest() throws ExpressionParsingException, FunctionArgumentException, CellReferenceException {
        String expressionText = "=pow(-2, A1 - 3) * (42 + B2)";

        Expression expectedExpression = mul(
                powF(
                        minus(two),
                        minus(a1(), three)
                ),
                plus(fortyTwo, b2())
        );
        Expression actualExpression = parser.parse(expressionText);

        AssertionUtils.assertExpressionsEqual(expectedExpression, actualExpression);
    }

    @Test
    void validRangeExpressionTest() throws ExpressionParsingException, FunctionArgumentException, CellReferenceException {
        String expressionText = "=sum(A1:F4) * (42 + B2)";

        Expression expectedExpression = mul(
                sum(range("A1", "F4")),
                plus(fortyTwo, b2())
        );
        Expression actualExpression = parser.parse(expressionText);

        AssertionUtils.assertExpressionsEqual(expectedExpression, actualExpression);
    }

    @Test
    void validBooleanExpressionTest() throws ExpressionParsingException, FunctionArgumentException, CellReferenceException {
        String expressionText = "=sum(A1:F4) < 5";

        Expression expectedExpression = lt(
                sum(range("A1", "F4")),
                five
        );
        Expression actualExpression = parser.parse(expressionText);

        AssertionUtils.assertExpressionsEqual(expectedExpression, actualExpression);
    }

    @Test
    void stringExpressionTest() throws ExpressionParsingException, FunctionArgumentException {
        String expressionText = "=CONCAT(\"2\", \"3\")";

        Expression expectedExpression = concat(
                rawValue("2"),
                rawValue("3")
        );
        Expression actualExpression = parser.parse(expressionText);

        AssertionUtils.assertExpressionsEqual(expectedExpression, actualExpression);
    }

    @Test
    void tooManyCommasExpressionTest() {
        String expressionText = "=POW(2,, 3)";
        Assertions.assertThrows(ExpressionParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void tooManyOpenBracketsExpressionTest() {
        String expressionText = "=POW((2, 3)";
        Assertions.assertThrows(ExpressionParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void tooManyCloseBracketsExpressionTest() {
        String expressionText = "=POW(2, 3))";
        Assertions.assertThrows(ExpressionParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void brokenBracketsExpressionTest() {
        String expressionText = "=2 * )(2)";
        Assertions.assertThrows(ExpressionParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void unmatchedParenthesesTest() {
        String expressionText = "=2 + (3 * 4";
        Assertions.assertThrows(ExpressionParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void extraClosingParenthesisTest() {
        String expressionText = "=2 + 3) * 4";
        Assertions.assertThrows(ExpressionParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void invalidTokenTest() {
        String expressionText = "=2 + 3 & 4";
        Assertions.assertThrows(ExpressionParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void invalidFunctionNameTest() {
        String expressionText = "=UNKNOWN(2, 3)";
        Assertions.assertThrows(ExpressionParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void missingFunctionArgumentTest() {
        String expressionText = "=POW(2)";
        Assertions.assertThrows(ExpressionParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void extraFunctionArgumentTest() {
        String expressionText = "=POW(2, 3, 4)";
        Assertions.assertThrows(ExpressionParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void invalidExpressionSequenceTest() {
        String expressionText = "=2 2 + 3";
        Assertions.assertThrows(ExpressionParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void missingOperatorBetweenNumbersTest() {
        String expressionText = "=2 3 + 4";
        Assertions.assertThrows(ExpressionParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void emptyParenthesesTest() {
        String expressionText = "=POW(,)";
        Assertions.assertThrows(ExpressionParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void incompleteFunctionTest() {
        String expressionText = "=POW(2, ";
        Assertions.assertThrows(ExpressionParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void multipleOperatorsWithoutOperandTest() {
        String expressionText = "=2 + / 3";
        Assertions.assertThrows(ExpressionParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void startingWithOperatorTest() {
        String expressionText = "=* 2 * 3";
        Assertions.assertThrows(ExpressionParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void endingWithOperatorTest() {
        String expressionText = "=2 * 3 +";
        Assertions.assertThrows(ExpressionParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void missingFunctionParenthesesTest() {
        String expressionText = "=POW 2, 3)";
        Assertions.assertThrows(ExpressionParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void invalidStringFunctionParenthesesTest() {
        String expressionText = "=CONCATENATE(\"2\", \"3)";
        Assertions.assertThrows(ExpressionParsingException.class, () -> parser.parse(expressionText));
    }
}
