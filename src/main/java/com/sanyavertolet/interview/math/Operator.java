package com.sanyavertolet.interview.math;

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

    public Double evaluate(Double left, Double right) {
        return switch (this) {
            case PLUS -> left + right;
            case MINUS -> left - right;
            case MULTIPLY -> left * right;
            case DIVIDE -> {
                if (left.equals(0.0)) {
                    throw new ArithmeticException("Division by zero");
                }
                yield left / right;
            }
            case POWER -> Math.pow(left, right);
            default -> throw new IllegalStateException("Operator cannot be evaluated: " + this);
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
