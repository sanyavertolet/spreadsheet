package com.sanyavertolet.interview.parser.tokenizer;

import com.sanyavertolet.interview.exceptions.expressions.ExpressionParsingException;

import java.util.ArrayList;
import java.util.List;

public class SimpleTokenizer implements Tokenizer {
    private Integer position;
    private String expression;
    private Character currentSym;

    public SimpleTokenizer() {
        position = 0;
        expression = "";
    }

    private void advance() {
        position++;
        if (position < expression.length()) {
            currentSym = expression.charAt(position);
        }
    }

    private boolean hasMoreTokens() {
        return position < expression.length();
    }

    private Token getNextToken() throws ExpressionParsingException {
        while(hasMoreTokens() && expression.charAt(position) == ' ') {
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
                if (currentSym == '=') {
                    advance();
                    return new Token(Token.Type.OPERATOR, "==");
                } else {
                    throw new ExpressionParsingException("Unexpected symbol '" + currentSym + "'");
                }
            case '!':
                advance();
                if (currentSym == '=') {
                    advance();
                    return new Token(Token.Type.OPERATOR, "!=");
                } else {
                    throw new ExpressionParsingException("Unexpected symbol '" + currentSym + "'");
                }
            case '<':
                advance();
                if (currentSym == '=') {
                    advance();
                    return new Token(Token.Type.OPERATOR, "<=");
                } else {
                    return new Token(Token.Type.OPERATOR, "<");
                }
            case '>':
                advance();
                if (currentSym == '=') {
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
                if(Character.isDigit(currentSym)) {
                    return getNumberToken();
                } else if (Character.isLetter(currentSym)) {
                    return getReferenceToken();
                } else if (currentSym == '"') {
                    advance();
                    return getStringToken();
                } else {
                    throw new ExpressionParsingException("Syntax error at position: " + position);
                }
        }
    }

    @Override
    public List<Token> tokenize(String input) throws ExpressionParsingException {
        expression = input;
        position = 0;
        advance();

        List<Token> tokens = new ArrayList<>();

        while (hasMoreTokens()) {
            tokens.add(getNextToken());
        }

        return tokens;
    }

    private Token getNumberToken() {
        StringBuilder token = new StringBuilder();
        if (!hasMoreTokens()) {
            return new Token(Token.Type.NUMBER, currentSym.toString());
        }
        while(hasMoreTokens() && (Character.isDigit(currentSym) || currentSym.equals('.'))) {
            token.append(currentSym);
            advance();
        }
        return new Token(Token.Type.NUMBER, token.toString());
    }

    private Token getReferenceToken() {
        StringBuilder token = new StringBuilder();
        if (!hasMoreTokens()) {
            return new Token(Token.Type.REFERENCE, currentSym.toString());
        }
        while(hasMoreTokens() && (Character.isLetter(currentSym) || Character.isDigit(currentSym))) {
            token.append(currentSym);
            advance();
        }
        return new Token(Token.Type.REFERENCE, token.toString());
    }

    private Token getStringToken() throws ExpressionParsingException {
        StringBuilder token = new StringBuilder();
        if (!hasMoreTokens() && currentSym != '"') {
            throw new ExpressionParsingException("Could not parse string properly: closing \" is missing");
        } else if (!hasMoreTokens()) {
            return new Token(Token.Type.STRING, currentSym.toString());
        }
        while(hasMoreTokens() && currentSym != '"') {
            token.append(currentSym);
            advance();
        }
        if (currentSym != '"') {
            throw new ExpressionParsingException("Could not parse string properly: closing \" is missing");
        }
        advance();
        return new Token(Token.Type.STRING, token.toString());
    }
}
