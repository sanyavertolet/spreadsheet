package com.sanyavertolet.interview.parser.tokenizer;

import com.sanyavertolet.interview.exceptions.expressions.ExpressionParsingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple implementation of the {@link Tokenizer} interface, responsible for breaking down
 * an input string into a list of {@link Token} objects. The {@code SimpleTokenizer} supports
 * basic expression parsing, including operators, numbers, references, and strings.
 */
public class SimpleTokenizer implements Tokenizer {
    private final static Logger logger = LoggerFactory.getLogger(SimpleTokenizer.class);
    private Integer position;
    private String expression;
    private Character currentSym;

    /**
     * Constructs a {@code SimpleTokenizer} with an initial position and empty expression.
     */
    public SimpleTokenizer() {
        position = 0;
        expression = "";
    }

    /**
     * Advances the tokenizer to the next character in the expression, updating the current symbol.
     */
    private void advance() {
        position++;
        if (position < expression.length()) {
            currentSym = expression.charAt(position);
        }
    }

    /**
     * Checks if there are more tokens to process in the expression.
     *
     * @return {@code true} if there are more tokens, {@code false} otherwise.
     */
    private boolean hasMoreTokens() {
        return position < expression.length();
    }

    /**
     * Retrieves the next {@link Token} from the expression based on the current symbol.
     *
     * @return the next token in the expression.
     * @throws ExpressionParsingException if an invalid token or syntax error is encountered.
     */
    private Token getNextToken() throws ExpressionParsingException {
        while (hasMoreTokens() && expression.charAt(position) == ' ') {
            advance();
        }
        switch (currentSym) {
            case '(':
                advance();
                return new Token(Token.Type.OPEN_PARENTHESIS, "(");
            case ')':
                advance();
                return new Token(Token.Type.CLOSE_PARENTHESIS, ")");
            case ',':
                advance();
                return new Token(Token.Type.COMMA, ",");
            case ':':
                advance();
                return new Token(Token.Type.COLON, ":");
            case '=':
                advance();
                if (currentSym.equals('=')) {
                    advance();
                    return new Token(Token.Type.OPERATOR, "==");
                } else {
                    throw new ExpressionParsingException("Unexpected symbol '" + currentSym + "'");
                }
            case '!':
                advance();
                if (currentSym.equals('=')) {
                    advance();
                    return new Token(Token.Type.OPERATOR, "!=");
                } else {
                    throw new ExpressionParsingException("Unexpected symbol '" + currentSym + "'");
                }
            case '<':
                advance();
                if (currentSym.equals('=')) {
                    advance();
                    return new Token(Token.Type.OPERATOR, "<=");
                } else {
                    return new Token(Token.Type.OPERATOR, "<");
                }
            case '>':
                advance();
                if (currentSym.equals('=')) {
                    advance();
                    return new Token(Token.Type.OPERATOR, ">=");
                } else {
                    return new Token(Token.Type.OPERATOR, ">");
                }
            case '+':
            case '-':
            case '*':
            case '/':
            case '^':
            case '%':
                String operator = currentSym.toString();
                advance();
                return new Token(Token.Type.OPERATOR, operator);
            default:
                if (Character.isDigit(currentSym)) {
                    return getNumberToken();
                } else if (Character.isLetter(currentSym)) {
                    return getReferenceToken();
                } else if (currentSym.equals('"')) {
                    advance();
                    return getStringToken();
                } else {
                    throw new ExpressionParsingException("Syntax error at position: " + position);
                }
        }
    }

    /**
     * Tokenizes the given input string into a list of {@link Token} objects.
     *
     * @param input the input string to be tokenized.
     * @return a list of tokens representing the parsed elements of the input string.
     * @throws ExpressionParsingException if an error occurs during tokenization, such as invalid syntax.
     */
    @Override
    public List<Token> tokenize(String input) throws ExpressionParsingException {
        logger.debug("Tokenizing {}", input);
        expression = input;
        position = 0;
        advance();

        List<Token> tokens = new ArrayList<>();

        while (hasMoreTokens()) {
            tokens.add(getNextToken());
        }
        logger.trace("Tokenized {} into tokens {}", input, tokens.stream().map(Record::toString).collect(Collectors.joining(", ", "[", "]")));
        return tokens;
    }

    /**
     * Retrieves a number token from the current position in the expression.
     * This method collects digits and decimal points to form a valid number token.
     *
     * @return a {@link Token} representing the number.
     */
    private Token getNumberToken() {
        if (!hasMoreTokens()) {
            return new Token(Token.Type.NUMBER, currentSym.toString());
        }
        StringBuilder token = new StringBuilder();
        while (hasMoreTokens() && (Character.isDigit(currentSym) || currentSym.equals('.'))) {
            token.append(currentSym);
            advance();
        }
        return new Token(Token.Type.NUMBER, token.toString());
    }

    /**
     * Retrieves a reference token (e.g., cell reference) from the current position in the expression.
     * This method collects letters and digits to form a valid reference token.
     *
     * @return a {@link Token} representing the reference.
     */
    private Token getReferenceToken() {
        if (!hasMoreTokens()) {
            return new Token(Token.Type.REFERENCE, currentSym.toString());
        }
        StringBuilder token = new StringBuilder();
        while (hasMoreTokens() && (Character.isLetter(currentSym) || Character.isDigit(currentSym))) {
            token.append(currentSym);
            advance();
        }
        return new Token(Token.Type.REFERENCE, token.toString());
    }

    /**
     * Retrieves a string token from the current position in the expression.
     * This method collects characters within quotes to form a valid string token.
     *
     * @return a {@link Token} representing the string.
     * @throws ExpressionParsingException if the string is not properly closed with a quote.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    private Token getStringToken() throws ExpressionParsingException {
        if (!hasMoreTokens() && currentSym != '"') {
            throw new ExpressionParsingException("Could not parse string properly: closing \" is missing");
        } else if (!hasMoreTokens()) {
            return new Token(Token.Type.STRING, currentSym.toString());
        }

        StringBuilder token = new StringBuilder();
        while (hasMoreTokens() && currentSym != '"') {
            token.append(currentSym);
            advance();
        }
        if (!currentSym.equals('"')) {
            throw new ExpressionParsingException("Could not parse string properly: closing \" is missing");
        }
        advance();
        return new Token(Token.Type.STRING, token.toString());
    }
}
