package com.sanyavertolet.interview.parser.tokenizer;

/**
 * Represents a token used in parsing expressions. A {@code Token} consists of a {@link Type} indicating
 * the kind of token, and a string value representing the token's content.
 *
 * @param type  the type of the token, indicating its role in the expression (e.g., operator, number, reference).
 * @param value the string value of the token.
 */
public record Token(Token.Type type, String value) {

    /**
     * Enum representing the different types of tokens that can be encountered in an expression.
     */
    public enum Type {
        /**
         * Represents an unknown or unrecognized token type.
         */
        UNKNOWN,

        /**
         * Represents an operator (e.g., +, -, *, /, etc.) in an expression.
         */
        OPERATOR,

        /**
         * Represents a numeric value in an expression.
         */
        NUMBER,

        /**
         * Represents references both for cells (e.g., A1, B2, etc.) and functions(e.g. SUM, PI, etc.) in a spreadsheet expression.
         */
        REFERENCE,

        /**
         * Represents an open parenthesis '(' in an expression, typically used for grouping.
         */
        OPEN_PARENTHESIS,

        /**
         * Represents a close parenthesis ')' in an expression, typically used for grouping.
         */
        CLOSE_PARENTHESIS,

        /**
         * Represents a comma ',' in an expression, typically used for separating function arguments.
         */
        COMMA,

        /**
         * Represents a colon ':' in an expression, typically used in ranges (e.g., A1:B2).
         */
        COLON,

        /**
         * Represents a string value in an expression.
         */
        STRING,
    }
}
