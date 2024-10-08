package com.sanyavertolet.interview.parser.tokenizer;

import com.sanyavertolet.interview.exceptions.expressions.ExpressionParsingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SimpleTokenizerTest {
    private final SimpleTokenizer simpleTokenizer = new SimpleTokenizer();

    @Test
    void dummyTokenizerTest() throws ExpressionParsingException {
        String expression = "=2 + 2";
        List<Token> expectedTokens = List.of(
                new Token(Token.Type.NUMBER, "2"),
                new Token(Token.Type.OPERATOR, "+"),
                new Token(Token.Type.NUMBER, "2")
        );
        List<Token> actualTokens = simpleTokenizer.tokenize(expression);
        Assertions.assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void parenthesisTokenizerTest() throws ExpressionParsingException {
        String expression = "=3.0 * (4 - 3.0) / (2 ^ 2)";
        List<Token> expectedTokens = List.of(
                new Token(Token.Type.NUMBER, "3.0"),
                new Token(Token.Type.OPERATOR, "*"),
                new Token(Token.Type.OPEN_PARENTHESIS, "("),
                new Token(Token.Type.NUMBER, "4"),
                new Token(Token.Type.OPERATOR, "-"),
                new Token(Token.Type.NUMBER, "3.0"),
                new Token(Token.Type.CLOSE_PARENTHESIS, ")"),
                new Token(Token.Type.OPERATOR, "/"),
                new Token(Token.Type.OPEN_PARENTHESIS, "("),
                new Token(Token.Type.NUMBER, "2"),
                new Token(Token.Type.OPERATOR, "^"),
                new Token(Token.Type.NUMBER, "2"),
                new Token(Token.Type.CLOSE_PARENTHESIS, ")")
        );
        List<Token> actualTokens = simpleTokenizer.tokenize(expression);
        Assertions.assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void referenceTokenizerTest() throws ExpressionParsingException {
        String expression = "=pow(2, 5) + C2";
        List<Token> expectedTokens = List.of(
                new Token(Token.Type.REFERENCE, "pow"),
                new Token(Token.Type.OPEN_PARENTHESIS, "("),
                new Token(Token.Type.NUMBER, "2"),
                new Token(Token.Type.COMMA, ","),
                new Token(Token.Type.NUMBER, "5"),
                new Token(Token.Type.CLOSE_PARENTHESIS, ")"),
                new Token(Token.Type.OPERATOR, "+"),
                new Token(Token.Type.REFERENCE, "C2")
        );
        List<Token> actualTokens = simpleTokenizer.tokenize(expression);
        Assertions.assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void multipleOperatorsTokenizerTest() throws ExpressionParsingException {
        String expression = "=1 + 2 - 3 * 4 / 5";
        List<Token> expectedTokens = List.of(
                new Token(Token.Type.NUMBER, "1"),
                new Token(Token.Type.OPERATOR, "+"),
                new Token(Token.Type.NUMBER, "2"),
                new Token(Token.Type.OPERATOR, "-"),
                new Token(Token.Type.NUMBER, "3"),
                new Token(Token.Type.OPERATOR, "*"),
                new Token(Token.Type.NUMBER, "4"),
                new Token(Token.Type.OPERATOR, "/"),
                new Token(Token.Type.NUMBER, "5")
        );
        List<Token> actualTokens = simpleTokenizer.tokenize(expression);
        Assertions.assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void negativeNumberTokenizerTest() throws ExpressionParsingException {
        String expression = "=-2 + 3";
        List<Token> expectedTokens = List.of(
                new Token(Token.Type.OPERATOR, "-"),
                new Token(Token.Type.NUMBER, "2"),
                new Token(Token.Type.OPERATOR, "+"),
                new Token(Token.Type.NUMBER, "3")
        );
        List<Token> actualTokens = simpleTokenizer.tokenize(expression);
        Assertions.assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void complexExpressionTokenizerTest() throws ExpressionParsingException {
        String expression = "=pow(-2, 5) + (3 * E()) / (A1 - 7)";
        List<Token> expectedTokens = List.of(
                new Token(Token.Type.REFERENCE, "pow"),
                new Token(Token.Type.OPEN_PARENTHESIS, "("),
                new Token(Token.Type.OPERATOR, "-"),
                new Token(Token.Type.NUMBER, "2"),
                new Token(Token.Type.COMMA, ","),
                new Token(Token.Type.NUMBER, "5"),
                new Token(Token.Type.CLOSE_PARENTHESIS, ")"),
                new Token(Token.Type.OPERATOR, "+"),
                new Token(Token.Type.OPEN_PARENTHESIS, "("),
                new Token(Token.Type.NUMBER, "3"),
                new Token(Token.Type.OPERATOR, "*"),
                new Token(Token.Type.REFERENCE, "E"),
                new Token(Token.Type.OPEN_PARENTHESIS, "("),
                new Token(Token.Type.CLOSE_PARENTHESIS, ")"),
                new Token(Token.Type.CLOSE_PARENTHESIS, ")"),
                new Token(Token.Type.OPERATOR, "/"),
                new Token(Token.Type.OPEN_PARENTHESIS, "("),
                new Token(Token.Type.REFERENCE, "A1"),
                new Token(Token.Type.OPERATOR, "-"),
                new Token(Token.Type.NUMBER, "7"),
                new Token(Token.Type.CLOSE_PARENTHESIS, ")")
        );
        List<Token> actualTokens = simpleTokenizer.tokenize(expression);
        Assertions.assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void exampleExpressionTokenizerTest() throws ExpressionParsingException {
        String expression = "=pow(-2, A1 - 3) * (42 + B2)";
        List<Token> expectedTokens = List.of(
                new Token(Token.Type.REFERENCE, "pow"),
                new Token(Token.Type.OPEN_PARENTHESIS, "("),
                new Token(Token.Type.OPERATOR, "-"),
                new Token(Token.Type.NUMBER, "2"),
                new Token(Token.Type.COMMA, ","),
                new Token(Token.Type.REFERENCE, "A1"),
                new Token(Token.Type.OPERATOR, "-"),
                new Token(Token.Type.NUMBER, "3"),
                new Token(Token.Type.CLOSE_PARENTHESIS, ")"),
                new Token(Token.Type.OPERATOR, "*"),
                new Token(Token.Type.OPEN_PARENTHESIS, "("),
                new Token(Token.Type.NUMBER, "42"),
                new Token(Token.Type.OPERATOR, "+"),
                new Token(Token.Type.REFERENCE, "B2"),
                new Token(Token.Type.CLOSE_PARENTHESIS, ")")
        );
        List<Token> actualTokens = simpleTokenizer.tokenize(expression);
        Assertions.assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void rangeExpressionTokenizerTest() throws ExpressionParsingException {
        String expression = "=sum(A1:F4) * (42 + B2)";
        List<Token> expectedTokens = List.of(
                new Token(Token.Type.REFERENCE, "sum"),
                new Token(Token.Type.OPEN_PARENTHESIS, "("),
                new Token(Token.Type.REFERENCE, "A1"),
                new Token(Token.Type.COLON, ":"),
                new Token(Token.Type.REFERENCE, "F4"),
                new Token(Token.Type.CLOSE_PARENTHESIS, ")"),
                new Token(Token.Type.OPERATOR, "*"),
                new Token(Token.Type.OPEN_PARENTHESIS, "("),
                new Token(Token.Type.NUMBER, "42"),
                new Token(Token.Type.OPERATOR, "+"),
                new Token(Token.Type.REFERENCE, "B2"),
                new Token(Token.Type.CLOSE_PARENTHESIS, ")")
        );
        List<Token> actualTokens = simpleTokenizer.tokenize(expression);
        Assertions.assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void stringExpressionTokenizerTest() throws ExpressionParsingException {
        String expression = "=A1 + \"A1\"";
        List<Token> expectedTokens = List.of(
                new Token(Token.Type.REFERENCE, "A1"),
                new Token(Token.Type.OPERATOR, "+"),
                new Token(Token.Type.STRING, "A1")
        );
        List<Token> actualTokens = simpleTokenizer.tokenize(expression);
        Assertions.assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void invalidStringExpressionTokenizerTest() {
        Assertions.assertThrows(ExpressionParsingException.class, () -> simpleTokenizer.tokenize("=A1 + \"A1"));
    }

    @Test
    void unknownOperationTokenizerTest() {
        Assertions.assertThrows(ExpressionParsingException.class, () -> simpleTokenizer.tokenize("=2 & 3"));
    }

    @Test
    void invalidExpressionTokenizerTest() {
        Assertions.assertThrows(ExpressionParsingException.class, () -> simpleTokenizer.tokenize("=PI() .3"));
    }

    @Test
    void emptyExpressionTokenizerTest() throws ExpressionParsingException {
        Assertions.assertIterableEquals(List.of(), simpleTokenizer.tokenize("="));
    }
}
