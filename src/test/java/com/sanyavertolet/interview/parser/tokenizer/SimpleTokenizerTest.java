package com.sanyavertolet.interview.parser.tokenizer;

import com.sanyavertolet.interview.exceptions.ParsingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SimpleTokenizerTest {
    private final SimpleTokenizer simpleTokenizer = new SimpleTokenizer();

    @Test
    void dummyTokenizerTest() throws ParsingException {
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
    void parenthesisTokenizerTest() throws ParsingException {
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
    void referenceTokenizerTest() throws ParsingException {
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
    void multipleOperatorsTokenizerTest() throws ParsingException {
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
    void negativeNumberTokenizerTest() throws ParsingException {
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
    void complexExpressionTokenizerTest() throws ParsingException {
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
    void unknownOperationTokenizerTest() {
        String expression = "=2 & 3";
        Assertions.assertThrows(ParsingException.class, () -> simpleTokenizer.tokenize(expression));
    }

    @Test
    void invalidExpressionTokenizerTest() {
        String expression = "=PI() .3";
        Assertions.assertThrows(ParsingException.class, () -> simpleTokenizer.tokenize(expression));
    }

    @Test
    void emptyExpressionTokenizerTest() throws ParsingException {
        String expression = "=";
        Assertions.assertIterableEquals(List.of(), simpleTokenizer.tokenize(expression));
    }
}
