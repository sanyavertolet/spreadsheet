package com.sanyavertolet.interview.parser.tokenizer;

import com.sanyavertolet.interview.exceptions.ParsingException;

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

    void advance() {
        position++;
        if (position < expression.length()) {
            currentSym = expression.charAt(position);
        }
    }

    boolean hasMoreTokens() {
        return position < expression.length();
    }

    Token getNextToken() throws ParsingException {
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
                } else {
                    throw new ParsingException("Syntax error at position: " + position);
                }
        }
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

    @Override
    public List<Token> tokenize(String input) throws ParsingException {
        expression = input;
        position = 0;
        advance();

        List<Token> tokens = new ArrayList<>();

        while (hasMoreTokens()) {
            tokens.add(getNextToken());
        }

        return tokens;
    }
}
