package com.sanyavertolet.interview.parser;

public enum Operator {
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    POWER,
    OPEN_PARENTHESIS,
    CLOSE_PARENTHESIS,
    COMMA,
    ;

    public enum Associativity {
        LEFT,
        RIGHT,
    }

    public Associativity associativity() {
        return switch (this) {
            case PLUS, MINUS, MULTIPLY, DIVIDE -> Associativity.LEFT;
            case POWER -> Associativity.RIGHT;
            default -> throw new IllegalStateException("Operator has no associativity: " + this);
        };
    }

    public int precedence() {
        return switch (this) {
            case PLUS, MINUS -> 2;
            case MULTIPLY, DIVIDE -> 3;
            case POWER -> 4;
            default -> throw new IllegalStateException("Operator has no priority: " + this);
        };
    }

    public static Operator of(String symbol) {
        return switch (symbol) {
            case "+" -> PLUS;
            case "-" -> MINUS;
            case "*" -> MULTIPLY;
            case "/" -> DIVIDE;
            case "^" -> POWER;
            default -> throw new IllegalArgumentException("Invalid symbol: " + symbol);
        };
    }
}
