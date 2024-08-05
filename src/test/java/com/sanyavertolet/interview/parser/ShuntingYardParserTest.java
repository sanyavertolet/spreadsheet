package com.sanyavertolet.interview.parser;

import com.sanyavertolet.interview.exceptions.EvaluationException;
import com.sanyavertolet.interview.exceptions.ParsingException;
import com.sanyavertolet.interview.expressions.Expression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShuntingYardParserTest {
    private final ShuntingYardParser parser = new ShuntingYardParser();

    @Test
    void dummyExpressionTest() throws ParsingException, EvaluationException {
        String expressionText = "=1 + 2";
        Expression expression = parser.parse(expressionText);

        Double expectedValue = 3.0;
        Double actualValue = expression.evaluate();
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void parameterlessFunctionExpressionTest() throws ParsingException, EvaluationException {
        String expressionText = "=PI() + E()";
        Expression expression = parser.parse(expressionText);

        Double expectedValue = Math.PI + Math.E;
        Double actualValue = expression.evaluate();
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void parameterizedFunctionExpressionTest() throws ParsingException, EvaluationException {
        String expressionText = "=POW(2.0, 2.0)";
        Expression expression = parser.parse(expressionText);

        Double expectedValue = Math.pow(2.0, 2.0);
        Double actualValue = expression.evaluate();
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void complexExpressionFunctionExpressionTest() throws ParsingException, EvaluationException {
        String expressionText = "=2 ^ 5 + (3 * E()) / (4 - 7)";
        Expression expression = parser.parse(expressionText);

        Double expectedValue = Math.pow(2.0, 5.0) + (3 * Math.E) / (4 - 7);
        Double actualValue = expression.evaluate();
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void nestedFunctionExpressionTest() throws ParsingException, EvaluationException {
        String expressionText = "=POW(PI(), 2)";
        Expression expression = parser.parse(expressionText);

        Double expectedValue = Math.pow(Math.PI, 2);
        Double actualValue = expression.evaluate();
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void negativeNumberExpressionTest() throws ParsingException, EvaluationException {
        String expressionText = "=-5 + 10";
        Expression expression = parser.parse(expressionText);

        Double expectedValue = 5.0;
        Double actualValue = expression.evaluate();
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void multipleOperationsExpressionTest() throws ParsingException, EvaluationException {
        String expressionText = "=2 + 3 * 4 - 5 / 2";
        Expression expression = parser.parse(expressionText);

        Double expectedValue = 2 + 3 * 4 - 5 / 2.0;
        Double actualValue = expression.evaluate();
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void nestedParenthesesExpressionTest() throws ParsingException, EvaluationException {
        String expressionText = "=((2 + 3) * (4 - 1)) / 5";
        Expression expression = parser.parse(expressionText);

        Double expectedValue = ((2 + 3) * (4 - 1)) / 5.0;
        Double actualValue = expression.evaluate();
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void mixedFunctionAndOperationExpressionTest() throws ParsingException, EvaluationException {
        String expressionText = "=(POW(2, 3) + 4) * PI()";
        Expression expression = parser.parse(expressionText);

        Double expectedValue = (Math.pow(2, 3) + 4) * Math.PI;
        Double actualValue = expression.evaluate();
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void tooManyCommasExpressionTest() {
        String expressionText = "=POW(2,, 3)";
        Assertions.assertThrows(ParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void tooManyOpenBracketsExpressionTest() {
        String expressionText = "=POW((2, 3)";
        Assertions.assertThrows(ParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void tooManyCloseBracketsExpressionTest() {
        String expressionText = "=POW(2, 3))";
        Assertions.assertThrows(ParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void brokenBracketsExpressionTest() {
        String expressionText = "=2 * )(2)";
        Assertions.assertThrows(ParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void unmatchedParenthesesTest() {
        String expressionText = "=2 + (3 * 4";
        Assertions.assertThrows(ParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void extraClosingParenthesisTest() {
        String expressionText = "=2 + 3) * 4";
        Assertions.assertThrows(ParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void invalidTokenTest() {
        String expressionText = "=2 + 3 & 4";
        Assertions.assertThrows(ParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void invalidFunctionNameTest() {
        String expressionText = "=UNKNOWN(2, 3)";
        Assertions.assertThrows(ParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void missingFunctionArgumentTest() {
        String expressionText = "=POW(2)";
        Assertions.assertThrows(ParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void extraFunctionArgumentTest() {
        String expressionText = "=POW(2, 3, 4)";
        Assertions.assertThrows(ParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void invalidExpressionSequenceTest() {
        String expressionText = "=2 2 + 3";
        Assertions.assertThrows(ParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void missingOperatorBetweenNumbersTest() {
        String expressionText = "=2 3 + 4";
        Assertions.assertThrows(ParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void emptyParenthesesTest() {
        String expressionText = "=POW(,)";
        Assertions.assertThrows(ParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void incompleteFunctionTest() {
        String expressionText = "=POW(2, ";
        Assertions.assertThrows(ParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void multipleOperatorsWithoutOperandTest() {
        String expressionText = "=2 + / 3";
        Assertions.assertThrows(ParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void startingWithOperatorTest() {
        String expressionText = "=* 2 * 3";
        Assertions.assertThrows(ParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void endingWithOperatorTest() {
        String expressionText = "=2 * 3 +";
        Assertions.assertThrows(ParsingException.class, () -> parser.parse(expressionText));
    }

    @Test
    void missingFunctionParenthesesTest() {
        String expressionText = "=POW 2, 3)";
        Assertions.assertThrows(ParsingException.class, () -> parser.parse(expressionText));
    }
}
