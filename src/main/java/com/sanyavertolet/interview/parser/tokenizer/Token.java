package com.sanyavertolet.interview.parser.tokenizer;

public record Token(Token.Type type, String value) {
    public enum Type {
        UNKNOWN,
        OPERATOR,
        NUMBER,
        REFERENCE,
        OPEN_PARENTHESIS,
        CLOSE_PARENTHESIS,
        COMMA,
        COLON,
        STRING,
    }
}
